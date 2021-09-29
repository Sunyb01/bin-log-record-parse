package com.xfw.logbin.policy;

import com.google.common.base.CaseFormat;
import com.xfw.logbin.constants.CanalBinLogRecordKeyConstants;
import com.xfw.logbin.enums.BinLogOperateTypeEnum;
import com.xfw.logbin.process.LogRecordContext;
import com.xfw.logbin.converter.TypeConverter;
import com.xfw.logbin.entity.BinRecordDetails;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 18:31
 **/
public interface BinLogOperateStrategy {

    /**
     * 获取支持的操作类型 INSERT/UPDATE/DELETE
     * @author yb.sun
     * @date 2021/09/27 20:32
     * @return com.xfw.logbin.enums.BinLogOperateTypeEnum 操作类型枚举
     */
    BinLogOperateTypeEnum getSupportName();

   /**
    * 执行解析
    * @author yb.sun
    * @date 2021/09/27 18:37
    * @param data 数据源
    * @param context 上下文
    * @param typeConverterMap 类型解析器集合
    * @return java.util.List<T> 数据列表
    */
    List<BinRecordDetails<String, Object>> execute(List<Map<String, String>> data, LogRecordContext context, Map<String, TypeConverter> typeConverterMap);

    /**
     *  将包含下划线的键转换为小驼峰， 如user_name -> userName
     * @author yb.sun
     * @date 2021/09/28 15:19
     * @param src 源
     * @return java.lang.String 结果
     */
    default String converter2LowCamelIfNecessary(String src) {
        if (src.contains(CanalBinLogRecordKeyConstants.CONVERTER_2_LOWER_CAMEL_TOKEN)) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, src);
        }

        return src;
    }

    /**
     * 填充参数字段
     * @author yb.sun
     * @date 2021/09/28 15:15
     * @param typeConverterMap 转换器map
     * @param item 元素自身
     * @param record 记录
     * @param key 键
     */
    default void populateField(Map<String, TypeConverter> typeConverterMap, Map<String, String> item, BinRecordDetails<String, Object> record, String key) {
        String value = item.get(key);
        TypeConverter typeConverter = typeConverterMap.get(key);
        key = converter2LowCamelIfNecessary(key);

        if (StringUtils.isBlank(value)) {
            throw new NullPointerException("Missing required param, it's null or empty!");
        }

        Object realValue = typeConverter.converter(value);
        record.put(key, realValue);
    }

    /**
     * 校验主键是否合法
     * @author yb.sun
     * @date 2021/09/28 15:21
     * @param pks 主键列表
     */
    default void verifyPks(List<String> pks) {
        if (CollectionUtils.isEmpty(pks)) {
            throw new IllegalArgumentException("Invalid param is null or empty!");
        }
    }

    /**
     * 根据上下文构建初始记录
     * @author yb.sun
     * @date 2021/09/28 15:32
     * @param context 上下文
     * @return com.xfw.logbin.entity.BinRecordDetails<java.lang.String, java.lang.Object> 记录
     */
    default BinRecordDetails<String, Object> buildRecordByContext(LogRecordContext context) {
        return BinRecordDetails.<String, Object>builder()
                .databaseName(context.getDatabaseName())
                .operateType(context.getActionType())
                .tableName(context.getTableName())
                .pk(context.getPks())
                .build();
    }
}
