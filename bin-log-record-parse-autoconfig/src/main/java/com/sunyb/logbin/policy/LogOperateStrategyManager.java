package com.sunyb.logbin.policy;

import com.google.common.collect.Maps;
import com.sunyb.logbin.converter.TypeConverter;
import com.sunyb.logbin.entity.BinRecordDetails;
import com.sunyb.logbin.enums.BinLogOperateTypeEnum;
import com.sunyb.logbin.process.LogRecordContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

        return binLogOperateStrategy.execute(data, context, stringTypeConverterMap);
    }
}
