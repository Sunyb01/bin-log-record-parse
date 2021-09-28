package com.xfw.logbin.converter;

import com.xfw.logbin.constants.CanalBinLogRecordKeyConstants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author yb.sun
 * @date 2021/09/27 17:58
 * @return
 */
public class TimeStampTypeConverter implements TypeConverter {
    static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static ZoneId zoneId = ZoneId.systemDefault();

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_TIMESTAMP;
    }

    @Override
    public Date converter(String src) {
        LocalDateTime ldt = LocalDateTime.parse(src,df);
        ZonedDateTime zdt = ldt.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
}
