package com.zkq.weapon.market.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.zkq.weapon.R;
import com.zkq.weapon.application.BaseApplication;
import com.zkq.weapon.market.util.ThreadPool;
import com.zkq.weapon.market.util.ZLog;

/**
 * @author zkq
 * time: 2018/12/11:14:59
 * email: zkq815@126.com
 * desc: 图片工具类
 */
public class ToolImage {

    private static final String TAG = ToolImage.class.getCanonicalName();

    @SuppressLint("StaticFieldLeak")
    private static Picasso sPicasso;

    public static Picasso getPicasso() {
        if (null == sPicasso) {
            final Context context = BaseApplication.getInstance();
            sPicasso = new Picasso.Builder(context).downloader(new OkHttp3Downloader(context)).build();
            sPicasso.setIndicatorsEnabled(true);
        }
        return sPicasso;
    }

    /**
     * 退出程序时，shutdown Picasso的实例，优化cpu与内存占用
     */
    public static void destroy() {
        ZLog.e(TAG, "destroy");
        ThreadPool.exe(()->{
            synchronized (ToolImage.class) {
                if (null != sPicasso) {
                    try {
                        ZLog.i(TAG, "shutdown picasso");
                        sPicasso.shutdown();
                    } catch (Exception e) {
                        ZLog.t(TAG, "destroy", e);
                    }
                    sPicasso = null;
                }
            }
        });
    }

    public static void load(String url, ImageView view) {
        if (filter(url)) {
            return;
        }
        getPicasso().load(url).fit().centerCrop().placeholder(R.color.grey_fa).into(view);
    }

    public static void loadWithNoHolder(String url, ImageView view) {
        if (filter(url)) {
            return;
        }
        getPicasso().load(url).into(view);
    }

    public static void loadJpgWithCallBack(String url, ImageView view, Callback callback) {
        if (filter(url)) {
            return;
        }
        getPicasso().load(url).placeholder(R.color.grey_fa).into(view, callback);
    }

    public static void load(String url, ImageView view, Transformation transformation) {
        if (filter(url)) {
            return;
        }
        if (transformation != null) {
            getPicasso().load(url).placeholder(R.color.grey_fa).transform(transformation).into(view);
        } else {
            getPicasso().load(url).placeholder(R.color.grey_fa).into(view);
        }
    }

    public static void loadWithHolderColor(String url, ImageView view, int placeholderResId, Transformation transformation) {
        if (filter(url)) {
            return;
        }
        if (transformation != null) {
            getPicasso().load(url).placeholder(placeholderResId).transform(transformation).into(view);
        } else {
            getPicasso().load(url).placeholder(placeholderResId).into(view);
        }
    }

    static void fitLoad(final String url, final ImageView imageView) {
        if (filter(url)) {
            return;
        }
        getPicasso().load(url).fit().placeholder(R.color.grey_fa).into(imageView);
    }

    private static boolean filter(final String url) {
        if (url == null || url.trim().length() == 0) {
            return true;
        }

        return false;
    }
}
