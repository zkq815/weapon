package com.zkq.weapon.market.glide.glideimpl;

import android.support.annotation.NonNull;
import com.zkq.weapon.market.glide.config.BaseConfigFactory;

/**
 * @author zkq
 * create:2018/12/11 9:54 AM
 * email:zkq815@126.com
 * desc: glide加载图片时的配置工厂
 */
public class GlideConfigFactory extends BaseConfigFactory<GlideLoaderConfig> {


    /**
     * 如果glide都是在主线程调用的  size = 1即能满足需求，但不确定都是在主线程调用但
     */
    private static final int DEFAULT_POOL_SIZE = 10;


    private GlideConfigFactory() {
        super(DEFAULT_POOL_SIZE);
    }

    @NonNull
    @Override
    public GlideLoaderConfig createNewInstance() {
        return new GlideLoaderConfig();
    }

    public static GlideConfigFactory getInstance() {
        return SingleInstanceHolder.sGlideConfigFactory;
    }

    private static class SingleInstanceHolder {
        private static GlideConfigFactory sGlideConfigFactory = new GlideConfigFactory();
    }
}
