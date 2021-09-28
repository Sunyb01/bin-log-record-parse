package com.xfw.logbin.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 16:33
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BinLogParseStatus {

    /**
     *  是否解析错误
     */
    private Boolean hasError;

    /**
     *  错误信息
     */
    private String errorMsg;

    /**
     *  异常
     */
    private Throwable error;
}
