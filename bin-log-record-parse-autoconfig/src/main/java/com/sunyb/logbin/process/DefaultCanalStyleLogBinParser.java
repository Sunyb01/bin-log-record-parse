package com.sunyb.logbin.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Maps;
import com.sunyb.logbin.converter.TypeConverter;
import com.sunyb.logbin.converter.TypeConverterManager;
import com.sunyb.logbin.entity.BinRecordDetails;
import com.sunyb.logbin.policy.LogOperateStrategyManager;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.sunyb.logbin.constants.CanalBinLogRecordKeyConstants.*;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 16:49
 **/
public class DefaultCanalStyleLogBinParser extends BaseLogBinParser<String>{

    private final Cache<String, Map<String, TypeConverter>> TABLE_TYPE_CONVERTER_CACHE = Caffeine.newBuilder()
            .expireAfterWrite(24 * 60 * 60L, TimeUnit.SECONDS)
            .initialCapacity(100)
            .maximumSize(2000)
            .weakValues()
            .weakKeys()
            .build();

    public DefaultCanalStyleLogBinParser(LogOperateStrategyManager logOperateStrategyManager) {
        super(logOperateStrategyManager);
    }

    @Override
    protected List<BinRecordDetails<String, Object>> doParseSource(String source) {
        JSONObject jsonSrc = JSON.parseObject(source);
        Boolean isDdl = (Boolean) jsonSrc.get(IS_DDL_KEY);
        String tableName = (String) jsonSrc.get(TABLE_KEY);
        String databaseName = (String) jsonSrc.get(DATABASE_KEY);
        String completeCacheKey = String.format("%s.%s", databaseName, tableName);
        // ddl的sql
        if (isDdl) {
            // 说明表格式有改动,清除缓存
            removeTypeConverterCacheIfNecessary(completeCacheKey);
            return null;
        }

        LogRecordContext context = populateContext(jsonSrc);

        Map<String, TypeConverter> stringTypeConverterMap = getTypeConverter(jsonSrc, completeCacheKey);

        List<Map<String, String>> data = JSON.parseObject(JSON.toJSONString(jsonSrc.get(DATA_KEY)),
                new TypeReference<List<Map<String, String>>>() {});

        return logOperateStrategyManager.process(data, context, stringTypeConverterMap);
    }

    @Override
    public String getParseName() {
        return PARSE_TYPE_CANAL;
    }

    /**
     * 获取转换器集合
     * @author yb.sun
     * @date 2021/09/30 9:40
     * @param jsonSrc 数据源json格式字符串
     * @param completeCacheKey 缓存键(databaseName + tableName)
     * @return java.util.Map<java.lang.String, com.sunyb.logbin.converter.TypeConverter> 转换器列表
     */
    private Map<String, TypeConverter> getTypeConverter(JSONObject jsonSrc, String completeCacheKey) {
        JSONObject mysqlType = (JSONObject) jsonSrc.get(MYSQL_TYPE_KEY);
        Map<String, TypeConverter> cache = getTypeConverterByCache(completeCacheKey);
        if (MapUtils.isNotEmpty(cache)) {
            return cache;
        }

        return extractColumnAndTypeConverter(mysqlType, completeCacheKey);
    }

    /**
     * 填充上下文
     * @author yb.sun
     * @date 2021/09/30 16:38
     * @param jsonSrc 数据源
     * @return com.sunyb.logbin.process.LogRecordContext 上下文
     */
    private LogRecordContext populateContext(JSONObject jsonSrc) {
        String tableName = (String) jsonSrc.get(TABLE_KEY);
        String dataBaseName = (String) jsonSrc.get(DATABASE_KEY);
        List<String> pks = JSON.parseObject(JSON.toJSONString(jsonSrc.get(PK_NAME_KEY)), new TypeReference<List<String>>() {});
        List<Map<String, Object>> olds = JSON.parseObject(JSON.toJSONString(jsonSrc.get(OLD_KEY)),
                new TypeReference<List<Map<String, Object>>>() {});

        String actionType = (String) jsonSrc.get(TYPE_KEY);
        return LogRecordContext.builder()
                .databaseName(dataBaseName)
                .actionType(actionType)
                .tableName(tableName)
                .olds(olds)
                .pks(pks)
                .build();
    }

    /**
     *  解析数据列类型
     * @param mysqlType 数据列类型列表
     * @param completeCacheKey 缓存键(databaseName + tableName)
     * @return 返回一个列名为键，类型转换器为值得集合
     */
    private Map<String, TypeConverter> extractColumnAndTypeConverter(JSONObject mysqlType, String completeCacheKey) {
        if (MapUtils.isNotEmpty(TABLE_TYPE_CONVERTER_CACHE.getIfPresent(completeCacheKey))) {
            return TABLE_TYPE_CONVERTER_CACHE.getIfPresent(completeCacheKey);
        }

        Map<String, TypeConverter> converterMap = TypeConverterManager.getTypeConverterMap();
        Map<String, TypeConverter> result = Maps.newHashMap();

        for (Map.Entry<String, Object> item : mysqlType.entrySet()) {
            String valueType = (String) item.getValue();
            int subMark = valueType.lastIndexOf(DEFAULT_CANAL_MYSQL_TYPE_SUB_TOKEN);

            String typeConverterName;
            if (-1 == subMark) {
                typeConverterName = valueType;
            }
            else {
                typeConverterName = valueType.substring(0, subMark);
            }

            TypeConverter typeConverter = converterMap.get(typeConverterName);
            if (Objects.nonNull(typeConverter)) {
                result.put(item.getKey(), typeConverter);
            }
        }

        if (MapUtils.isNotEmpty(TABLE_TYPE_CONVERTER_CACHE.getIfPresent(completeCacheKey))) {
            TABLE_TYPE_CONVERTER_CACHE.put(completeCacheKey, result);
        }

        return result;
    }

    /**
     * 根据库名称与表名称，清除类型转换器缓存
     * @author yb.sun
     * @date 2021/09/29 14:01
     * @param completeCacheKey 缓存键(databaseName + tableName)
     */
    private void removeTypeConverterCacheIfNecessary(String completeCacheKey) {
        if (StringUtils.isBlank(completeCacheKey)) {
            return;
        }

        Map<String, TypeConverter> typeConverterMap = TABLE_TYPE_CONVERTER_CACHE.getIfPresent(completeCacheKey);
        if (MapUtils.isEmpty(typeConverterMap)) {
            return;
        }

        TABLE_TYPE_CONVERTER_CACHE.invalidate(completeCacheKey);
    }

    /**
     * 获取缓存
     * @author yb.sun
     * @date 2021/09/29 14:17
     * @param completeCacheKey 缓存键(databaseName + tableName)
     * @return java.util.Map<java.lang.String, com.sunyb.logbin.converter.TypeConverter>
     */
    private Map<String, TypeConverter> getTypeConverterByCache(String completeCacheKey) {
        return TABLE_TYPE_CONVERTER_CACHE.getIfPresent(completeCacheKey);
    }

}
