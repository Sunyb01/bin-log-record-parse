package com.xfw.logbin.policy;

import com.xfw.logbin.enums.BinLogOperateTypeEnum;
import com.xfw.logbin.process.LogRecordContext;
import com.xfw.logbin.converter.TypeConverter;
import com.xfw.logbin.entity.BinRecordDetails;

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
     * @return com.sunyb.dataxdemo.common.enums.BinLogOperateTypeEnum 操作类型枚举
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

}
