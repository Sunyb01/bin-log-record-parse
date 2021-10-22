package com.xfw.logbin.utils;

import com.google.common.collect.Maps;
import com.xfw.logbin.entity.BinRecordDetails;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 记录数据提取工具
 * @author yb.Sun
 * @date 2021/10/22 15:52
 * @since 1.0.0
 */
public class RecordUtil {

    private RecordUtil() { }

    /**
     *  抽取并转换记录的真实数据;
     *  <p>当source为null或者未填充真实数据时,返回null</p>
     * @author yb.Sun
     * @date 2021/10/22 15:57
	 * @param source 数据源
     * @return java.util.Map<java.lang.String,java.lang.Object> 转换后数据
     */
    public static Map<String, Object> extractAndTransferRecordRealData(BinRecordDetails<String, Object> source) {
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
