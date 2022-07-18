package com.sunyb.logbin.config;

import com.sunyb.logbin.converter.*;
import com.sunyb.logbin.policy.*;
import com.sunyb.logbin.process.BaseLogBinParser;
import com.sunyb.logbin.process.DefaultCanalStyleLogBinParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  自动配置类
 * @author yb.Sun
 * @date 2021/09/28 9:39
 **/
@Configuration
@EnableConfigurationProperties(LogRecordProperties.class)
public class LogRecordAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = LogOperateStrategyManager.class)
    public LogOperateStrategyManager logOperateManager() {
        return new LogOperateStrategyManager();
    }

    @Bean
    @ConditionalOnMissingBean(BaseLogBinParser.class)
    public BaseLogBinParser<String> defaultCanalStyleLogBinParser() {
        return new DefaultCanalStyleLogBinParser(logOperateManager());
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

        @Bean
        public TypeConverter decimalTypeConverter() {
            return new DecimalConverter();
        }

        @Bean
        public TypeConverter bitTypeConverter() {
            return new BitTypeConverter();
        }

        @Bean
        public TypeConverter jsonTypeConverter() {return new JsonTypeConverter();}

        @Bean
        public TypeConverter longtextTypeConverter() {return new LongtextTypeConverter();}
    }
}
