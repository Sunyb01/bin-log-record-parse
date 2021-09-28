package com.xfw.common.converter;

import com.xfw.common.constants.CanalBinLogRecordKeyConstants;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 17:02
 **/
public class Bigint2LongConverter implements TypeConverter {

    @Override
    public Long converter(String src) {
        if (StringUtils.isBlank(src)) {
            return null;
        }

        return Long.parseLong(src);
    }

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_BIGINT;
    }
}
