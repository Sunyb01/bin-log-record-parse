package com.sunyb.logbin.policy;

import com.google.common.collect.Lists;
import com.sunyb.logbin.converter.TypeConverter;
import com.sunyb.logbin.entity.BinRecordDetails;
import com.sunyb.logbin.enums.BinLogOperateTypeEnum;
import com.sunyb.logbin.process.LogRecordContext;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * insert 操作
 * @author yb.Sun
 * @date 2021/09/27 18:34
 **/
public class BinLogInsertOperateStrategy implements BinLogOperateStrategy {

    @Override
    public BinLogOperateTypeEnum getSupportName() {
        return BinLogOperateTypeEnum.INSERT;
    }

    @Override
    public List<BinRecordDetails<String, Object>> execute(List<Map<String, String>> data, LogRecordContext context, Map<String, TypeConverter> typeConverterMap) {
        List<BinRecordDetails<String, Object>> result = Lists.newArrayListWithCapacity(data.size());

        for (Map<String, String> item : data) {
            BinRecordDetails<String, Object> record = buildRecordByContext(context);
            // 1. 解析参数
            for (Map.Entry<String, String> entry : item.entrySet()){
                String key = entry.getKey();
                TypeConverter typeConverter = typeConverterMap.get(key);
                key = converter2LowCamelIfNecessary(key);

                String value = entry.getValue();
                if (StringUtils.isBlank(value)) {
                    continue;
                }

                Object realValue = typeConverter.converter(value);
                if (Objects.nonNull(realValue)) {
                    record.put(key, realValue);
                }
            }

            result.add(record);
        }

        return result;
    }
}
