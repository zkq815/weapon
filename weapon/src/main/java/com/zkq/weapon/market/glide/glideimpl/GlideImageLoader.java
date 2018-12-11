package com.zkq.weapon.market.glide.glideimpl;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import com.zkq.weapon.market.glide.IImageLoader;
import com.zkq.weapon.market.glide.ILoad;
import java.io.File;

/**
 * @author zkq
 * create:2018/12/11 9:54 AM
 * email:zkq815@126.com
 * desc: 作为glide框架的容器
 */
public class GlideImageLoader implements IImageLoader<GlideLoaderConfig> {


    private ILoad<GlideLoaderConfig> mLoader;


    private GlideImageLoader() {

    }

    public static GlideImageLoader getInstance() {
        return SingleIntance.sImageLoader;
    }

    /**
     * 图片加载入口函数
     */
    @NonNull
    @Override
    public GlideLoaderConfig load(@NonNull String url) {
        return GlideConfigFactory.getInstance().obtain().load(url);
    }

    @NonNull
    @Override
    public GlideLoaderConfig load(@NonNull Uri uri) {
        return GlideConfigFactory.getInstance().obtain().load(uri);
    }

    @NonNull
    @Override
    public GlideLoaderConfig load(@NonNull File file) {
        return GlideConfigFactory.getInstance().obtain().load(file);
    }

    @NonNull
    @Override
    public GlideLoaderConfig load(@DrawableRes int res) {
        return GlideConfigFactory.getInstance().obtain().load(res);
    }

    /**
     * 调用指定的loader去加载图片
     * 在config 的 into 方法中调用
     */
    public void loadOptions(@NonNull GlideLoaderConfig options) {
        getGlideLoader().loadImage(options);
        GlideConfigFactory.getInstance().release(options);
    }

    @Override
    public void clearMemoryCache(@NonNull Context context) {
        getGlideLoader().clearMemoryCache(context);
    }

    @Override
    public void clearDiskCache(@NonNull Context context) {
        getGlideLoader().clearDiskCache(context);
    }

    @Override
    public void setMemoryCache() {
        getGlideLoader().setMemoryCache();
    }

    @Override
    public void setDiskCache() {
        getGlideLoader().setDiskCache();
    }

    @Override
    public void onLowMemory(@NonNull Context context) {
        getGlideLoader().onLowMemory(context);
    }

    @Override
    public void onTrimMemory(@NonNull Context context, int level) {
        getGlideLoader().onTrimMemory(context, level);
    }

    private ILoad<GlideLoaderConfig> getGlideLoader() {
        if (mLoader == null) {
            mLoader = new GlideLoaderImpl();
        }
        return mLoader;
    }

    @Override
    public void pauseRequests(@NonNull Context context) {
        getGlideLoader().pauseRequests(context);
    }

    @Override
    public void resumeRequests(@NonNull Context context) {
        getGlideLoader().resumeRequests(context);
    }

    private static class SingleIntance {
        private static GlideImageLoader sImageLoader = new GlideImageLoader();
    }
}
