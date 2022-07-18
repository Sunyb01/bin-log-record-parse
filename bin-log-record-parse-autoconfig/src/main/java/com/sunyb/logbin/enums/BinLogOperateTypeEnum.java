package com.sunyb.logbin.enums;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 15:50
 **/
public enum BinLogOperateTypeEnum {

    /**
     *  插入-行级别
     */
    INSERT,
    /**
     *  修改-行级别
     */
    UPDATE,
    /**
     *  删除-行级别
     */
    DELETE,
    /**
     *  新建-库级别
     */
    CREATE,
    /**
     *  删除-库级别
     */
    DROP,
    /**
     *  修改-库级别
     */
    ALTER
}
