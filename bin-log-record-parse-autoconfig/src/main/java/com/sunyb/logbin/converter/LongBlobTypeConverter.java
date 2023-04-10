package com.sunyb.logbin.converter;

import com.sunyb.logbin.constants.CanalBinLogRecordKeyConstants;

import java.nio.charset.StandardCharsets;

/**
 * @author yb.Sun
 * @date 2022/12/16 15:03
 */
public class LongBlobTypeConverter implements TypeConverter {
    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_LONG_BLOB;
    }

    @Override
    public Object converter(String src) {
        return src.getBytes(StandardCharsets.UTF_8);
    }
}
