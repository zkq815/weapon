package com.zkq.weapon.market.tangram;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.util.IInnerImageSetter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author zkq
 * time: 2019/5/29 6:00 PM
 * email: zkq815@126.com
 * desc: Tangram初始化
 */
public class TangramUtils {
    public static TangramBuilder.InnerBuilder initTrngram(Context context){
        // 初始化 Tangram 环境
        TangramBuilder.init(context, new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view,
                                                                 @Nullable String url) {
                Glide.with(view).load(url).into(view);
            }
        }, ImageView.class);
        // 初始化 TangramBuilder
        TangramBuilder.InnerBuilder builder = TangramBuilder.newInnerBuilder(context);

        return builder;
    }
}
