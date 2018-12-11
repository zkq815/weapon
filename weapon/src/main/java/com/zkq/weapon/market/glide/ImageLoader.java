package com.zkq.weapon.market.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.zkq.weapon.market.glide.config.LoaderConfig;
import com.zkq.weapon.market.glide.glideimpl.GlideImageLoader;
import com.zkq.weapon.market.glide.strategy.ICacheStrategy;
import com.zkq.weapon.market.glide.strategy.IRequestStrategy;

import java.io.File;

/**
 * @author zkq
 * create:2018/12/11 9:57 AM
 * email:zkq815@126.com
 * desc: 图片加载框架的容器代理类，可切换不同图片框架,如{@link GlideImageLoader}
 */
public class ImageLoader implements IImageLoader<LoaderConfig>, ICacheStrategy, IRequestStrategy {

    private IImageLoader<? extends LoaderConfig> mLoader;

    private ImageLoader() {

    }

    /**
     * 静态内部类实现单例
     */
    public static ImageLoader getInstance() {
        return SingleInstance.sImageLoader;
    }

    /**
     * 手动设置图片加载框架
     *
     * @param imageLoader 外部传入的图片加载框架
     */
    public void setImageLoader(@NonNull IImageLoader<? extends LoaderConfig> imageLoader) {
        //不能传自己，防止调用load时死循环;
        if (imageLoader == ImageLoader.getInstance()) {
            return;
        }
        this.mLoader = imageLoader;
    }

    @NonNull
    public IImageLoader<? extends LoaderConfig> getLoader() {
        if (mLoader == null) {
            //默认使用glide
            mLoader = GlideImageLoader.getInstance();
        }
        return mLoader;
    }

    /**
     * 图片加载入口函数
     */
    @NonNull
    @Override
    public LoaderConfig load(@NonNull String url) {
        return getLoader().load(url);
    }

    @NonNull
    @Override
    public LoaderConfig load(@NonNull Uri uri) {
        return getLoader().load(uri);
    }

    @NonNull
    @Override
    public LoaderConfig load(@NonNull File file) {
        return getLoader().load(file);
    }

    @NonNull
    @Override
    public LoaderConfig load(@DrawableRes int res) {
        return getLoader().load(res);
    }


    /**
     * 清理内存缓存 需在主线程调用，在子线程调用无效（内部已做线程判断，不抛异常）
     */
    @Override
    public void clearMemoryCache(@NonNull Context context) {
        getLoader().clearMemoryCache(context);
    }

    /**
     * 清除磁盘缓存 需在子线程调用，在主线程调用无效（内部已做线程判断，不抛异常）
     */
    @Override
    public void clearDiskCache(@NonNull Context context) {
        getLoader().clearDiskCache(context);
    }

    /**
     * 设置内存缓存
     */
    @Override
    public void setMemoryCache() {
        getLoader().setMemoryCache();
    }

    /**
     * 设置磁盘缓存
     */
    @Override
    public void setDiskCache() {
        getLoader().setDiskCache();
    }

    /**
     * 低内存下的处理
     */
    @Override
    public void onLowMemory(@NonNull Context context) {
        getLoader().onLowMemory(context);
    }

    /**
     * 不同内存级别下的操作
     *
     * @param level {@link android.content.ComponentCallbacks2}
     */
    @Override
    public void onTrimMemory(@NonNull Context context, int level) {
        getLoader().onTrimMemory(context, level);
    }

    /**
     * 暂停图片加载
     */
    @Override
    public void pauseRequests(@NonNull Context context) {
        getLoader().pauseRequests(context);
    }

    /**
     * 恢复图片加载
     */
    @Override
    public void resumeRequests(@NonNull Context context) {
        getLoader().resumeRequests(context);
    }

    private static class SingleInstance {
        private static ImageLoader sImageLoader = new ImageLoader();
    }

    /**
     * 封装Picasso图片加载
     * 需导入：compile 'com.squareup.picasso:picasso:2.5.2'  //picasso 图片加载框架
     * 优点：加载速度快，电商首页动态配置目前没发现卡顿、屏幕跳跃等问题，也没有发生OOM
     * 缺点：缓存图片较大
     */
    public static void picassoLoadPic(Context context,String imageUrl,ImageView imageView) {
        Picasso.with(context)
                .load(imageUrl)
//                .resize(dp2px(250),dp2px(250))
//                .centerCrop()
                .config(Bitmap.Config.RGB_565)
                .into(imageView);
    }

    /**
     * 封装Gilde图片加载
     * 优点：加载速度快
     * 弊端：偶尔图片会出现加载出来后模糊,图片加载后内存回收严重，导致多网络图片加载后上滑时出现屏幕跳跃、卡顿等问题
     */
    public static void gildeLoadPic(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .dontAnimate()
    }

}
