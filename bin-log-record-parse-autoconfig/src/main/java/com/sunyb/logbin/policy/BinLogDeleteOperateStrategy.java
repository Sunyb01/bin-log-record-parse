package com.sunyb.logbin.policy;

import com.google.common.collect.Lists;
import com.sunyb.logbin.converter.TypeConverter;
import com.sunyb.logbin.entity.BinRecordDetails;
import com.sunyb.logbin.enums.BinLogOperateTypeEnum;
import com.sunyb.logbin.process.LogRecordContext;

import java.util.List;
import java.util.Map;

/**
 * delete 操作
 * @author yb.Sun
 * @date 2021/09/27 20:57
 **/
public class BinLogDeleteOperateStrategy implements BinLogOperateStrategy {

    @Override
    public BinLogOperateTypeEnum getSupportName() {
        return BinLogOperateTypeEnum.DELETE;
    }

    @Override
    public List<BinRecordDetails<String, Object>> execute(List<Map<String, String>> data, LogRecordContext context, Map<String, TypeConverter> typeConverterMap) {
        List<String> pks = context.getPks();
        verifyPks(pks);

        List<BinRecordDetails<String, Object>> result = Lists.newArrayListWithCapacity(data.size());

        for (Map<String, String> item : data) {
            BinRecordDetails<String, Object> record = buildRecordByContext(context);

            pks.forEach(pkName -> populateField(typeConverterMap, item, record, pkName));

            result.add(record);
        }

        return result;
    }

}
