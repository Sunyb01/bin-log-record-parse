package com.xfw.common.converter;

import com.xfw.common.constants.CanalBinLogRecordKeyConstants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author yb.sun
 * @date 2021/09/27 18:02
 * @return
 */
public class DateTimeTypeConverter  implements TypeConverter {
    static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static ZoneId zoneId = ZoneId.systemDefault();

    @Override
    public String getTypeName() {
        return CanalBinLogRecordKeyConstants.MYSQL_TYPE_DATETIME;
    }

    @Override
    public Date converter(String src) {
        LocalDateTime ldt = LocalDateTime.parse("2017-09-28 17:07:05",df);
        ZonedDateTime zdt = ldt.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
}