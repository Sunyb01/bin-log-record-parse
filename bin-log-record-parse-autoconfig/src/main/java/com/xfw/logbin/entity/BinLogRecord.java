package com.xfw.logbin.entity;


import com.xfw.logbin.enums.BinLogOperateTypeEnum;

import java.util.List;

/**
 *  bin_log 解析记录
 * @author yb.Sun
 * @date 2021/09/27 15:49
 **/
public interface BinLogRecord {

    /**
     * 获取库名称
     * @author yb.sun
     * @date 2021/09/27 15:54
     * @return java.lang.String 库名称
     */
    String getDataBaseName();

    /**
     * 获取表名称
     * @author yb.sun
     * @date 2021/09/27 15:54
     * @return java.lang.String 表名称
     */
    String getTableName();

    /**
     * 获取操作类型
     * @author yb.sun
     * @date 2021/09/27 15:55
     * @return com.sunyb.dataxdemo.common.BinLogOperateTypeEnum 操作类型
     */
    String geOperateType();

    /**
     * 获取主键
     * @author yb.sun
     * @date 2021/09/27 16:00
     * @return java.util.List<java.lang.String> 主键列表
     */
    List<String> getPk();
}
