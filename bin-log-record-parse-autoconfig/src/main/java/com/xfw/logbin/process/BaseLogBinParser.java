package com.xfw.logbin.process;

import com.google.common.collect.Lists;
import com.xfw.logbin.entity.BinRecordDetails;
import com.xfw.logbin.policy.LogOperateStrategyManager;
import org.apache.commons.collections4.CollectionUtils;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 16:12
 **/
public abstract class BaseLogBinParser<T> implements LogBinParser<T, BinRecordDetails<String, Object>>{

    protected LogOperateStrategyManager logOperateStrategyManager;

    public BaseLogBinParser(LogOperateStrategyManager logOperateStrategyManager) {
        this.logOperateStrategyManager = logOperateStrategyManager;
    }

    @Override
    public List<BinRecordDetails<String, Object>> parseSourceLog(T source, Class<? extends BinRecordDetails<String, Object>> clazz) {
        if (!verifySourceIsLegal(source)) {
            throw new IllegalArgumentException("Missing required param, it's null or empty!");
        }

        List<BinRecordDetails<String, Object>> result = doParseSource(source);

        if (CollectionUtils.isEmpty(result)) {
            return Lists.newArrayList();
        }

        BinLogParseStatus status = BinLogParseStatus.builder().build();
        if (isNeedTransformedData(clazz)) {
           return converter2Target(result, clazz,status);
        }

        if (Objects.nonNull(status.getError())) {
            throw new RuntimeException(status.getErrorMsg());
        }

        return result;
    }

    /**
     *  解析
     * @param source 数据源
     * @return 解析结果
     */
    protected abstract List<BinRecordDetails<String, Object>> doParseSource(T source);

    /**
     *  是否需要转化数据
     * @param clazz 目标类型
     * @return 需要返回true，否则返回true
     */
    protected Boolean isNeedTransformedData(Class<? extends BinRecordDetails<String, Object>> clazz) {
        return Objects.nonNull(clazz);
    }

    /**
     *  参数校验
     * @param source 源
     * @return -
     */
    private Boolean verifySourceIsLegal(T source) {
        return Objects.nonNull(source);
    }

    /**
     *  数据转换
     * @param src 源
     * @param clazz 目标类型
     * @param status 状态
     * @return 结果
     */
    private List<BinRecordDetails<String, Object>> converter2Target(List<BinRecordDetails<String, Object>> src, Class<? extends BinRecordDetails<String, Object>> clazz, BinLogParseStatus status) {
        List<BinRecordDetails<String, Object>> result = Lists.newArrayListWithCapacity(src.size());
        try {
            for (Map<String, Object> item : src) {
                BinRecordDetails<String, Object> record = clazz.newInstance();
                for (Map.Entry<String, Object> field : item.entrySet()) {
                    String fieldName = field.getKey();
                    Field declaredField = clazz.getDeclaredField(fieldName);
                    declaredField.setAccessible(Boolean.TRUE);
                    Object value = field.getValue();
                    declaredField.set(record, value);
                }

                result.add(record);
            }

        } catch (Exception e) {
            // need log "It has error with converter source to target, error msg is {}, source data is {}", e.getMessage(), src
            status.setError(e);
            status.setHasError(Boolean.TRUE);
            status.setErrorMsg(e.getMessage());
        }

        return result;
    }
}
