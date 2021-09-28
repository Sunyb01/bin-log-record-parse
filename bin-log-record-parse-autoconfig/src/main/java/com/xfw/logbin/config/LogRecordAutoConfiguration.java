package com.xfw.logbin.config;

import com.xfw.logbin.converter.*;
import com.xfw.logbin.policy.*;
import com.xfw.logbin.process.BaseLogBinParser;
import com.xfw.logbin.process.DefaultCanalStyleLogBinParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author yb.Sun
 * @date 2021/09/28 9:39
 **/
@Configuration
@EnableConfigurationProperties(LogRecordProperties.class)
public class LogRecordAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(BaseLogBinParser.class)
    public BaseLogBinParser<String> defaultCanalStyleLogBinParser() {
        return new DefaultCanalStyleLogBinParser(logOperateManager());
    }

    @Bean
    @ConditionalOnMissingBean(value = LogOperateStrategyManager.class)
    public LogOperateStrategyManager logOperateManager() {
        return new LogOperateStrategyManager();
    }

    @Configuration
    static class LogOperateAutoConfiguration {

        @Bean
        public BinLogOperateStrategy insertOperateStrategy() {
            return new BinLogInsertOperateStrategy();
        }

        @Bean
        public BinLogOperateStrategy updateOperateStrategy() {
            return new BinLogUpdateOperateStrategy();
        }

        @Bean
        public BinLogOperateStrategy deleteOperateStrategy() {
            return new BinLogDeleteOperateStrategy();
        }
    }

    @Bean
    @ConditionalOnMissingBean(value = TypeConverterManager.class)
    public TypeConverterManager defaultTypeConverterManager() {
        return new TypeConverterManager();
    }

    @Configuration
    static class TypeConverterAutoConfiguration {

        @Bean
        public TypeConverter bigint2LongConverter() {
            return new BigintTypeConverter();
        }

        @Bean
        public TypeConverter charTypeConverter() {
            return new CharTypeConverter();
        }

        @Bean
        public TypeConverter dateTimeTypeConverter() {
            return new DateTimeTypeConverter();
        }

        @Bean
        public TypeConverter timeStampTypeConverter() {
            return new TimeStampTypeConverter();
        }

        @Bean
        public TypeConverter tinyIntTypeConverter() {
            return new TinyIntTypeConverter();
        }

        @Bean
        public TypeConverter varcharTypeConverter() {
            return new VarcharTypeConverter();
        }

        @Bean
        public TypeConverter intTypeConverter() {
            return new IntTypeConverter();
        }
    }
}
