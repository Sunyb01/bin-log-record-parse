package com.xfw.logbin.converter;

import com.xfw.logbin.constants.CanalBinLogRecordKeyConstants;

/**
 * @author yb.Sun
 * @date 2022/6/24 13:51
 * Note:
 */
public class LongtextTypeConverter implements TypeConverter {

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_LONG_TEXT;
    }

    @Override
    public Object converter(String src) {
        return src;
    }
}
