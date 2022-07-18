package com.sunyb.logbin.entity;


import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    String getOperateType();

    /**
     * 获取主键
     * @author yb.sun
     * @date 2021/09/27 16:00
     * @return java.util.List<java.lang.String> 主键列表
     */
    List<String> getPk();

    /**
     *  抽取并转换记录的真实数据;
     *  <p>当source为null或者未填充真实数据时,返回null</p>
     * @author yb.Sun
     * @date 2021/10/22 15:57
     * @param source 数据源
     * @return java.util.Map<java.lang.String,java.lang.Object> 转换后数据
     */
    default Map<String, Object> getRecordRealData(BinRecordDetails<String, Object> source) {
        if (Objects.isNull(source)) {
            return null;
        }

        Set<Map.Entry<String, Object>> entries = source.entrySet();
        if (CollectionUtils.isEmpty(entries)) {
            return null;
        }

        Map<String, Object> result = Maps.newHashMap();

        for (Map.Entry<String, Object> item : entries) {
            result.put(item.getKey(), item.getValue());
        }

        return result;
    }

}
