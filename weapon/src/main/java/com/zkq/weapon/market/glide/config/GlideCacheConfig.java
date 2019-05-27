package com.zkq.weapon.market.glide.config;

import android.content.Context;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.LibraryGlideModule;

/**
 * @author zkq
 * create:2018/12/11 9:53 AM
 * email:zkq815@126.com
 * desc: Glide缓存配置
 */
@GlideModule
public class GlideCacheConfig extends LibraryGlideModule {


    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}