package com.sunyb.logbin.converter;

import com.sunyb.logbin.constants.CanalBinLogRecordKeyConstants;

import java.nio.charset.StandardCharsets;

/**
 * @author yb.Sun
 * @date  2022/12/16 15:03
 */
public class TextTypeConverter implements TypeConverter {
    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_TEXT;
    }

    @Override
    public Object converter(String src) {
        return src;
    }
}
