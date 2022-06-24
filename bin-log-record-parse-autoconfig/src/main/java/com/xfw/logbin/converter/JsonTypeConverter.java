package com.xfw.logbin.converter;

import com.xfw.logbin.constants.CanalBinLogRecordKeyConstants;

/**
 * @author yb.Sun
 * @date 2022/6/24 13:46
 * Note:
 */
public class JsonTypeConverter implements TypeConverter {

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_JSON;
    }

    @Override
    public Object converter(String src) {
        return src;
    }
}
