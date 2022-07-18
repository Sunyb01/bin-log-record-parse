package com.sunyb.logbin.converter;

import com.sunyb.logbin.constants.CanalBinLogRecordKeyConstants;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 18:12
 **/
public class TinyIntTypeConverter implements TypeConverter {

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_TINYINT;
    }

    @Override
    public Integer converter(String src) {
        if (StringUtils.isBlank(src)) {
            return null;
        }
        return Integer.valueOf(src);
    }
}
