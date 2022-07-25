package com.sunyb.logbin.converter;

import com.sunyb.logbin.constants.CanalBinLogRecordKeyConstants;

import java.nio.charset.StandardCharsets;

/**
 * @author yb.Sun
 * @date 2022/7/25 16:27
 * Note:
 */
public class BlobTypeConverter implements TypeConverter{

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_BLOB;
    }

    @Override
    public Object converter(String src) {
        return src.getBytes(StandardCharsets.UTF_8);
    }
}
