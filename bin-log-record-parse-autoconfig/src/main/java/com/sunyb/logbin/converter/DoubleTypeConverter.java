package com.sunyb.logbin.converter;

import com.sunyb.logbin.constants.CanalBinLogRecordKeyConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author yb.Sun
 * @date: 2022/12/16 09:42
 */
public class DoubleTypeConverter implements TypeConverter {
    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_DOUBLE;
    }

    @Override
    public Object converter(String src) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }

        return Double.valueOf(src);
    }
}
