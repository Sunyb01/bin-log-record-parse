package com.sunyb.logbin.constants;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 16:51
 **/
public class CanalBinLogRecordKeyConstants {

    private CanalBinLogRecordKeyConstants() { }

    /**
     *  canal 类型解析器
     */
    public static final String PARSE_TYPE_CANAL = "CANAL";

    // ######################################################## logRecord字段名称 ########################################################

    /**
     *  type字段键
     */
    public static final String TYPE_KEY = "type";

    /**
     *  table字段键
     */
    public static final String TABLE_KEY = "table";

    /**
     *  database字段键
     */
    public static final String DATABASE_KEY = "database";

    /**
     *  pkNames字段键
     */
    public static final String PK_NAME_KEY = "pkNames";

    /**
     *  old字段键
     */
    public static final String OLD_KEY = "old";

    /**
     *  isDdl字段键
     */
    public static final String IS_DDL_KEY = "isDdl";

    /**
     *  data字段键
     */
    public static final String DATA_KEY = "data";

    /**
     *  mysqlType字段键
     */
    public static final String MYSQL_TYPE_KEY = "mysqlType";

    // ######################################################## mysql数据类型 ########################################################

    /**
     *  mysql字段类型 - bigint
     */
    public static final String MYSQL_TYPE_BIGINT = "bigint";

    /**
     *  mysql字段类型 - int
     */
    public static final String MYSQL_TYPE_INT = "int";

    /**
     *  mysql字段类型 - varchar
     */
    public static final String MYSQL_TYPE_VARCHAR = "varchar";

    /**
     *  mysql字段类型 - char
     */
    public static final String MYSQL_TYPE_CHAR = "char";

    /**
     *  mysql字段类型 - tinyint
     */
    public static final String MYSQL_TYPE_TINYINT = "tinyint";

    /**
     *  mysql字段类型 - datetime
     */

    public static final String MYSQL_TYPE_DATETIME = "datetime";

    /**
     *  mysql字段类型 - datetime
     */
    public static final String MYSQL_TYPE_TIMESTAMP = "timestamp";

    /**
     *  mysql字段类型 - decimal
     */
    public static final String MYSQL_DECIMAL = "decimal";

    /**
     *  mysql字段类型 - bit
     */
    public static final String MYSQL_BIT = "bit";

    /**
     *  mysql字段类型 - json
     */
    public static final String MYSQL_TYPE_JSON = "json";

    /**
     *  mysql字段类型 - longtext
     */
    public static final String MYSQL_TYPE_LONG_TEXT = "longtext";

    /**
     *  mysql字段类型 - text
     */
    public static final String MYSQL_TYPE_TEXT = "text";

    /**
     *  mysql字段类型 - MYSQL_TYPE_BLOB
     */
    public static final String MYSQL_TYPE_BLOB = "blob";

    /**
     *  mysql字段类型 - MYSQL_TYPE_LONG_BLOB
     */
    public static final String MYSQL_TYPE_LONG_BLOB = "longblob";

    /**
     *  mysql字段类型 - datetime
     */

    public static final String MYSQL_TYPE_DOUBLE= "double";

    // ######################################################## 定位标识 ########################################################

    /**
     *  canal字段mysqlType中值截取标识
     */
    public static final String DEFAULT_CANAL_MYSQL_TYPE_SUB_TOKEN = "(";

    /**
     *  canal字段mysqlType中驼峰转换标识
     */
    public static final String CONVERTER_2_LOWER_CAMEL_TOKEN = "_";

}
