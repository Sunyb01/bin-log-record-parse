package com.xfw.logbin.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;

import com.xfw.logbin.converter.TypeConverter;
import com.xfw.logbin.converter.TypeConverterManager;
import com.xfw.logbin.entity.BinRecordDetails;
import com.xfw.logbin.policy.LogOperateStrategyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.xfw.logbin.constants.CanalBinLogRecordKeyConstants.*;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 16:49
 **/
@Component
public class DefaultCanalStyleLogBinParser extends BaseLogBinParser<String>{

    @Autowired
    public DefaultCanalStyleLogBinParser(LogOperateStrategyManager logOperateStrategyManager) {
        super(logOperateStrategyManager);
    }

    @Override
    protected List<BinRecordDetails<String, Object>> doParseSource(String source) {
        JSONObject jsonSrc = JSON.parseObject(source);
        Boolean isDdl = (Boolean) jsonSrc.get(IS_DDL_KEY);
        // ddl的sql不做处理
        if (isDdl) {
            return null;
        }

        JSONObject mysqlType = (JSONObject) jsonSrc.get(MYSQL_TYPE_KEY);
        List<Map<String, String>> data = JSON.parseObject(JSON.toJSONString(jsonSrc.get(DATA_KEY)),
                new TypeReference<List<Map<String, String>>>() {});
        Map<String, TypeConverter> stringTypeConverterMap = extractColumnAndTypeConverter(mysqlType);

        LogRecordContext context = populateContext(jsonSrc);
        return logOperateStrategyManager.process(data, context, stringTypeConverterMap);
    }

    @Override
    public String getParseName() {
        return "CANAL";
    }

    /**
     *  填充上下文
     * @param jsonSrc 数据源
     * @return 上下文
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
     * @return 返回一个列名为键，类型转换器为值得集合
     */
    private Map<String, TypeConverter> extractColumnAndTypeConverter(JSONObject mysqlType) {
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

        return result;
    }
}
