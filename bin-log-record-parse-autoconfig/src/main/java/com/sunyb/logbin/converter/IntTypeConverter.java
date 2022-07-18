package com.sunyb.logbin.converter;

import com.sunyb.logbin.constants.CanalBinLogRecordKeyConstants;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author yb.Sun
 * @date 2021/09/28 16:26
 **/
public class IntTypeConverter implements TypeConverter{
    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_INT;
    }

    @Override
    public Object converter(String src) {
        if (StringUtils.isBlank(src)) {
            return null;
        }

        return Integer.valueOf(src);
    }
}
