package com.xfw.logbin.converter;

import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.Map;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 17:11
 **/
public class TypeConverterManager implements ApplicationContextAware, InitializingBean {

    private final static Map<String, TypeConverter> TYPE_CONVERTER_MAP = Maps.newHashMap();

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(TypeConverter.class).values().forEach(item -> TYPE_CONVERTER_MAP.put(item.getTypeName(), item));
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取转换器map
     * @author yb.sun
     * @date 2021/09/27 17:16
     * @return 转换器集合
     */
    public static Map<String, TypeConverter> getTypeConverterMap() {
        return TYPE_CONVERTER_MAP;
    }
}
