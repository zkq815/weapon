package com.zkq.weapon.market.glide.strategy;

import androidx.annotation.NonNull;

/**
 * @author zkq
 * create:2018/12/11 9:55 AM
 * email:zkq815@126.com
 * desc: 图片加载框架需实现的功能
 */
public interface ILoadStrategy<T> {

    /**
     * 调用三方图片加载框架的入口，根据config获取需要的加载参数
     *
     * @param config 图片配置参数
     */
    void loadImage(@NonNull T config);
}
