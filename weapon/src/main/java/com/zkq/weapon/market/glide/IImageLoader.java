package com.zkq.weapon.market.glide;

import android.net.Uri;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.zkq.weapon.market.glide.config.LoaderConfig;
import com.zkq.weapon.market.glide.strategy.ICacheStrategy;
import com.zkq.weapon.market.glide.strategy.IRequestStrategy;

import java.io.File;

/**
 * @author zkq
 * create:2018/12/11 9:55 AM
 * email:zkq815@126.com
 * desc: 图片加载框架容器需实现的接口,也可添加一些额外接口，如{@link IRequestStrategy}
 */
public interface IImageLoader<T extends LoaderConfig> extends ICacheStrategy, IRequestStrategy {

    @NonNull
    T load(@NonNull String url);

    @NonNull
    T load(@NonNull Uri uri);

    @NonNull
    T load(@NonNull File file);

    @NonNull
    T load(@DrawableRes int res);
}
