package com.zkq.weapon.market.glide.strategy;

import android.content.Context;
import androidx.annotation.NonNull;

/**
 * @author zkq
 * create:2018/12/11 9:55 AM
 * email:zkq815@126.com
 * desc: 图片加载框架缓存处理
 */
public interface ICacheStrategy {

    /**
     * 清理内存缓存 需在主线程调用
     */
    void clearMemoryCache(@NonNull Context context);

    /**
     * 清理硬盘缓存，请勿在UI线程调用
     */
    void clearDiskCache(@NonNull Context context);

    /**
     * 设置内存缓存
     */
    void setMemoryCache();

    /**
     * 设置硬盘缓存
     */
    void setDiskCache();

    /**
     * 低内存下的操作
     */
    void onLowMemory(@NonNull Context context);

    /**
     * 低内存下的操作
     */
    void onTrimMemory(@NonNull Context context, int level);

}
