package com.xfw.logbin.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 18:58
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogRecordContext {

    /**
     *  databaseName
     */
    private String databaseName;

    /**
     *  tableName
     */
    private String tableName;

    /**
     *  pks
     */
    private List<String> pks;

    /**
     *  actionType
     */
    private String actionType;

    /**
     *  olds
     */
    private List<Map<String, Object>> olds;
}
