package com.sunyb.logbin.converter;

import com.sunyb.logbin.constants.CanalBinLogRecordKeyConstants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author yb.sun
 * @date 2021/09/27 17:58
 */
public class TimeStampTypeConverter implements TypeConverter {

    static final String ERROR_TIME = "0000-00-00 00:00:00";

    static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static ZoneId zoneId = ZoneId.systemDefault();

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_TIMESTAMP;
    }

    @Override
    public Date converter(String src) {
        if (Objects.equals(src, ERROR_TIME)) {
            return null;
        }

        LocalDateTime ldt = LocalDateTime.parse(src, df);
        ZonedDateTime zdt = ldt.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
}
