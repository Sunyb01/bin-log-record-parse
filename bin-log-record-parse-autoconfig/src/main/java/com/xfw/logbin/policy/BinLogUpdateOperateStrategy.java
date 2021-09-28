package com.xfw.logbin.policy;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
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
 * @date 2021/09/27 18:35
 **/
public class BinLogUpdateOperateStrategy implements BinLogOperateStrategy {

    @Override
    public BinLogOperateTypeEnum getSupportName() {
        return BinLogOperateTypeEnum.UPDATE;
    }

    @Override
    public List<BinRecordDetails<String, Object>> execute(List<Map<String, String>> data, LogRecordContext context, Map<String, TypeConverter> typeConverterMap) {
        List<BinRecordDetails<String, Object>> result = Lists.newArrayListWithCapacity(data.size());
        List<Map<String, Object>> olds = context.getOlds();
        List<String> pks = context.getPks();

        for (Map<String, String> item : data) {
            BinRecordDetails<String, Object> record = BinRecordDetails.<String, Object>builder()
                    .typeEnum(BinLogOperateTypeEnum.valueOf(context.getActionType()))
                    .databaseName(context.getDatabaseName())
                    .tableName(context.getTableName())
                    .pk(pks)
                    .build();
            // 1. 遍历更新字段
            for (Map<String, Object> updateField : olds) {
                for (Map.Entry<String, Object> entry : updateField.entrySet()){
                    String key = entry.getKey();
                    String value = item.get(key);
                    TypeConverter typeConverter = typeConverterMap.get(key);

                    if (key.contains(CanalBinLogRecordKeyConstants.CONVERTER_2_LOWER_CAMEL_TOKEN)) {
                        key = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
                    }

                    if (StringUtils.isBlank(value)) {
                        throw new NullPointerException("Missing required param, it's null or empty!");
                    }

                    Object realValue = typeConverter.converter(value);
                    record.put(key, realValue);
                }
            }
            // 2. 主键填充
            if (CollectionUtils.isNotEmpty(pks)) {
                for (String pkName : pks) {
                    String pkValueStr = item.get(pkName);
                    if (StringUtils.isBlank(pkValueStr)) {
                        throw new NullPointerException("Missing required param, it's null or empty!");
                    }

                    TypeConverter typeConverter = typeConverterMap.get(pkName);
                    Object realValue = typeConverter.converter(pkValueStr);
                    if (pkName.contains(CanalBinLogRecordKeyConstants.CONVERTER_2_LOWER_CAMEL_TOKEN)) {
                        pkName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, pkName);
                    }

                    record.put(pkName, realValue);
                }
            }

            result.add(record);
        }

        return result;
    }
}
