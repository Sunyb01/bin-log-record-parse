package com.xfw.logbin.converter;

import com.xfw.logbin.constants.CanalBinLogRecordKeyConstants;

/**
 * @author yb.Sun
 * @date 2022/6/22 18:00
 * Note:
 */
public class BitTypeConverter implements TypeConverter{

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_BIT;
    }

    @Override
    public Object converter(String src) {
        return Integer.parseInt(src);
    }
}
