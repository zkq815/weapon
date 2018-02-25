package com.zkq.alldemo.costomview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zkq.alldemo.R;


/**
 * @author yc
 * @email yangchao@meizu.com
 * @since 2017/4/1
 */
public class LoadingView extends FrameLayout {

    public enum Type {
        LOADING_FAIL(R.string.loading_view_loading_fail, R.mipmap.loading_view_loading_fail, true),
        NETWORK_ERROR(R.string.loading_view_network_error, R.mipmap.loading_view_network_error, true),
        NO_NETWORK(R.string.loading_view_no_network, R.mipmap.loading_view_no_network, true),
        NO_SHOP_NEAR(R.string.near_shop_no_shop,R.mipmap.no_shop_near,true),
        NO_PERMISSION(R.string.near_shop_no_permission,R.mipmap.loading_view_no_network,true),
        CUSTOM(R.string.empty, R.mipmap.loading_view_loading_fail, true);

        @StringRes
        private final int infoRes;
        @DrawableRes
        private final int imgRes;
        private final boolean reload;

        Type(@StringRes final int infoRes, @DrawableRes final int imgRes, final boolean reload) {
            this.infoRes = infoRes;
            this.imgRes = imgRes;
            this.reload = reload;
        }
    }

    private Type type;
    private ImageView image;
    private TextView info;
    private ViewGroup loading;
    private OnClickListener onClickListener;

    public LoadingView(Context context) {
        super(context);

        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        setBackgroundResource(android.R.color.white);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

        inflate(getContext(), R.layout.loading_view, this);
        image = (ImageView) findViewById(R.id.loading_view_image);
        info = (TextView) findViewById(R.id.loading_view_info);
        loading = (ViewGroup) findViewById(R.id.loading_view_layout);

        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (null != type && type.reload && null != onClickListener) {
                    onClickListener.onClick(v);
                }
            }
        });

//        load();
    }

    @Override
    public void setOnClickListener(@Nullable final OnClickListener l) {
        this.onClickListener = l;
    }

    public LoadingView load() {
        setVisibility(VISIBLE);

        image.setVisibility(GONE);
        info.setVisibility(GONE);
        loading.setVisibility(VISIBLE);

        return this;
    }

    public LoadingView success() {
        setVisibility(GONE);

        return this;
    }

    public LoadingView fail(@NonNull final Type type, final OnClickListener l) {
        this.type = type;

        return failInternal(type.infoRes, l);
    }

    public LoadingView fail(@StringRes final int resId, final OnClickListener l) {
        this.type = Type.CUSTOM;

        return failInternal(resId, l);
    }

    private LoadingView failInternal(@StringRes final int resId, final OnClickListener l) {
        setVisibility(VISIBLE);

        image.setVisibility(VISIBLE);
        info.setVisibility(VISIBLE);
        loading.setVisibility(GONE);
        image.setBackgroundResource(type.imgRes);
        info.setText(resId);

        this.onClickListener = l;

        return this;
    }
}
