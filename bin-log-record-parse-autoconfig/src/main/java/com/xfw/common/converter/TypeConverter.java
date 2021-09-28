package com.xfw.common.converter;

/**
 *
 * @author yb.Sun
 * @date 2021/09/27 17:03
 **/
public interface TypeConverter {

    /**
     *  获取类型名称
     * @return -
     */
    String getTypeName();

    /**
     * 转换
     * @author yb.sun
     * @date 2021/09/27 17:03
     * @param src 数据源
     * @return R 结果
     */
    Object converter(String src);
}
