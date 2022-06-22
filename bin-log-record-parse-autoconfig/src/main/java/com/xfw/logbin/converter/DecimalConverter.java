package com.xfw.logbin.converter;

import com.xfw.logbin.constants.CanalBinLogRecordKeyConstants;

import java.math.BigDecimal;

/**
 * @author yb.Sun
 * @date 2022/6/22 17:52
 * Note:
 */
public class DecimalConverter implements TypeConverter {

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_DECIMAL;
    }

    @Override
    public Object converter(String src) {
        return new BigDecimal(src);
    }
}
