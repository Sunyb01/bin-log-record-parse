package com.xfw.logbin.entity;

import lombok.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 17:26
 **/
@Getter
@Setter
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
    private String operateType;

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
    public String geOperateType() {
        return this.operateType;
    }

    @Override
    public List<String> getPk() {
        return this.pk;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BinRecordDetails(tableName=").append(this.getTableName());
        sb.append(", databaseName=").append(this.getDataBaseName());
        sb.append(", operateType=").append(this.getOperateType());
        sb.append(", pk=").append(this.getPk());
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (! i.hasNext()) {
            sb.append(")");
            return sb.toString();
        }

        sb.append(", ");
        for (;;) {
            Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key   == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (! i.hasNext()) {
                return sb.append(')').toString();
            }

            sb.append(',').append(' ');
        }
    }

}
