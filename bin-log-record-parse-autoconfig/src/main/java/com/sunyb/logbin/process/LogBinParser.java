package com.sunyb.logbin.process;


import com.sunyb.logbin.entity.BinRecordDetails;

import java.util.List;

/**
 *  bin_log 解析
 * @author yb.Sun
 * @date 2021/09/27 15:48
 **/
public interface LogBinParser<T, R extends BinRecordDetails<String, Object>> {

    /**
     * 原始数据转换
     * @author yb.sun
     * @date 2021/09/27 16:09
     * @param source 数据
     * @param clazz 目标类型
     * @return java.util.List<R> 结果
     */
    List<R> parseSourceLog(T source, Class<? extends BinRecordDetails<String, Object>> clazz);

    /**
     *  获取解析器名称
     * @return 解析器名称
     */
    String getParseName();
}
