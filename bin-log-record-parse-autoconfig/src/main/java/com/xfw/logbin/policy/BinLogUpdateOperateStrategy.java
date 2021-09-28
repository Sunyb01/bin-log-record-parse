package com.xfw.logbin.policy;

import com.google.common.collect.Lists;
import com.xfw.logbin.converter.TypeConverter;
import com.xfw.logbin.entity.BinRecordDetails;
import com.xfw.logbin.enums.BinLogOperateTypeEnum;
import com.xfw.logbin.process.LogRecordContext;

import java.util.List;
import java.util.Map;

/**
 * update 操作
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
        List<String> pks = context.getPks();
        verifyPks(pks);

        List<BinRecordDetails<String, Object>> result = Lists.newArrayListWithCapacity(data.size());
        List<Map<String, Object>> olds = context.getOlds();

        for (Map<String, String> item : data) {
            BinRecordDetails<String, Object> record = buildRecordByContext(context);
            // 1. 遍历更新字段
            for (Map<String, Object> updateField : olds) {
                for (Map.Entry<String, Object> entry : updateField.entrySet()){
                    String key = entry.getKey();
                    populateField(typeConverterMap, item, record, key);
                }
            }
            // 2. 主键填充
            pks.forEach(pkName -> populateField(typeConverterMap, item, record, pkName));

            result.add(record);
        }

        return result;
    }
}
