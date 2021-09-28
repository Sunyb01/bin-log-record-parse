package com.xfw.common.converter;

import com.xfw.common.constants.CanalBinLogRecordKeyConstants;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 17:45
 **/
public class VarcharTypeConverter implements TypeConverter {

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_VARCHAR;
    }

    @Override
    public String converter(String src) {
        return src;
    }
}
