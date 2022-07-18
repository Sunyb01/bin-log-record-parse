package com.sunyb.logbin.converter;

import com.sunyb.logbin.constants.CanalBinLogRecordKeyConstants;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 17:46
 **/
public class CharTypeConverter implements TypeConverter {

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_CHAR;
    }

    @Override
    public String converter(String src) {
        return src;
    }
}