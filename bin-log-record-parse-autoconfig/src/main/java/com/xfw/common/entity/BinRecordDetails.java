package com.xfw.common.entity;

import com.xfw.common.enums.BinLogOperateTypeEnum;
import lombok.*;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 17:26
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BinRecordDetails<K, V> extends HashMap<K, V> implements BinLogRecord {

    /**
     *  表名
     */
    private String tableName;

    /**
     *  库名
     */
    private String databaseName;

    /**
     *  操作类型
     */
    private BinLogOperateTypeEnum typeEnum;

    /**
     *  主键列表
     */
    private List<String> pk;

    @Override
    public String getDataBaseName() {
        return this.databaseName;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override
    public BinLogOperateTypeEnum geOperateType() {
        return this.typeEnum;
    }

    @Override
    public List<String> getPk() {
        return this.pk;
    }

}
