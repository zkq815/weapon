package com.zkq.weapon.market.tools;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author zkq
 * create:2018/12/11 9:59 AM
 * email:zkq815@126.com
 * desc:List工具类
 */
public interface ToolList {

    /**
     * 集合是否为空（null 或 size==0）。
     *
     * @param sourceList 源集合数据
     * @return true：为空
     */
    static <V> boolean isNullOrEmpty(@Nullable List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    /**
     * 集合中元素个数是否大于0。
     *
     * @param sourceList 源集合数据
     * @return true：是
     */
    static <V> boolean isNotEmpty(@Nullable List<V> sourceList) {
        return !isNullOrEmpty(sourceList);
    }

    /**
     * 获取集合中元素个数。
     *
     * @param sourceList 源集合数据
     * @return 集合元素个数
     */
    static <V> int getSize(@Nullable List<V> sourceList) {
        return sourceList == null ? 0 : sourceList.size();
    }

    /**
     * 将源集合中的元素 复制 到目标集合中。
     *
     * @param sourceList 源集合数据
     * @param destList   目标集合
     */
    static <V> void copy(@Nullable List<V> sourceList, @Nullable List<V> destList) {
        if (isNotEmpty(sourceList) && (destList != null)) {
            destList.addAll(sourceList);
        }
    }

    /**
     * 清除集合中的元素。
     * 注意：通过 {@link java.util.Arrays#asList(Object[])} 创建的List不能调用该函数，否则会抛出异常。
     *
     * @param sourceList 源集合数据
     */
    static <V> void clear(@Nullable List<V> sourceList) {
        if (isNotEmpty(sourceList)) {
            sourceList.clear();
        }
    }

}
