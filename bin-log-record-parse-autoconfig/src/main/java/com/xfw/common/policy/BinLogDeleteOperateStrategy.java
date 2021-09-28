package com.xfw.common.policy;

import com.xfw.common.enums.BinLogOperateTypeEnum;
import com.xfw.common.process.LogRecordContext;
import com.xfw.common.converter.TypeConverter;
import com.xfw.common.entity.BinRecordDetails;

import java.util.List;
import java.util.Map;

/**
 *
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
        return null;
    }
}
