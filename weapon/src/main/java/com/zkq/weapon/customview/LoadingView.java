package com.zkq.weapon.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zkq.weapon.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/**
 * @author zkq
 * create:2018/11/16 10:21 AM
 * email:zkq815@126.com
 * desc: 加载页面
 */
public class LoadingView extends FrameLayout {

    public enum Type {
        /**
         *
         * */
        LOADING_FAIL(R.string.weapon_loading_view_loading_fail, R.drawable.loading_view_loading_fail, true),
        NETWORK_ERROR(R.string.weapon_loading_view_network_error, R.drawable.loading_view_network_error, true),
        NO_NETWORK(R.string.weapon_loading_view_no_network, R.drawable.loading_view_no_network, true),
        CUSTOM(R.string.weapon_empty, R.drawable.loading_view_loading_fail, true);

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

        inflate(getContext(), R.layout.loading_layout, this);
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
