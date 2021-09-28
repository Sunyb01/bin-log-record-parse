package com.xfw.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author yb.Sun
 * @date 2021/09/28 9:37
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "log-record")
public class LogRecordProperties {

    /**
     * 注册类型
     */
    private String recordType;
}
