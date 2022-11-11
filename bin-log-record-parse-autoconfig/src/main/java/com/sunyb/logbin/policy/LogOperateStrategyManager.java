package com.sunyb.logbin.policy;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.sunyb.logbin.constants.CanalBinLogRecordKeyConstants;
import com.sunyb.logbin.converter.TypeConverter;
import com.sunyb.logbin.entity.BinRecordDetails;
import com.sunyb.logbin.enums.BinLogOperateTypeEnum;
import com.sunyb.logbin.process.LogRecordContext;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 18:43
 **/
public class LogOperateStrategyManager implements ApplicationContextAware, InitializingBean {

    private static final Map<BinLogOperateTypeEnum, BinLogOperateStrategy> ACTION_HANDLER_MAP = Maps.newHashMap();

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        applicationContext.getBeansOfType(BinLogOperateStrategy.class).values()
                .forEach(item -> ACTION_HANDLER_MAP.put(item.getSupportName(), item));
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<BinRecordDetails<String, Object>> process(List<Map<String, String>> data, LogRecordContext context, Map<String, TypeConverter> stringTypeConverterMap) {
        BinLogOperateStrategy binLogOperateStrategy = ACTION_HANDLER_MAP.get(BinLogOperateTypeEnum.valueOf(context.getActionType()));
        if (Objects.isNull(binLogOperateStrategy)) {
            return null;
        }

        if (CollectionUtils.isNotEmpty(data)) {
            List<Map<String, String>> records = data.stream()
                    .map(item -> item.entrySet()
                            .stream()
                            .collect(Collectors.toMap(entry -> converter2LowCamelIfNecessary(entry.getKey()), Map.Entry::getValue, (n, o) -> n)))
                    .collect(Collectors.toList());
            context.setRecordData(records);
        }

        return binLogOperateStrategy.execute(data, context, stringTypeConverterMap);
    }

    /**
     *  将包含下划线的键转换为小驼峰， 如user_name -> userName
     * @author yb.sun
     * @date 2021/09/28 15:19
     * @param src 源
     * @return java.lang.String 结果
     */
    public String converter2LowCamelIfNecessary(String src) {
        if (src.contains(CanalBinLogRecordKeyConstants.CONVERTER_2_LOWER_CAMEL_TOKEN)) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, src);
        }

        return src;
    }
}
