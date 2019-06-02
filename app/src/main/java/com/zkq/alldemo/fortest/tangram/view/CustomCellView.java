package com.zkq.alldemo.fortest.tangram.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zkq.weapon.market.tools.ToolSize;

/**
 * 使用自定义model方式的自定义View
 *
 * @author SunQiang
 * @since 2019-04-19
 */
public class CustomCellView extends LinearLayout {
    private ImageView mImageView;
    private TextView mTextView;

    public CustomCellView(Context context) {
        super(context);
        init();
    }

    public CustomCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        int padding = ToolSize.dp2Px(getContext(), 10);
        setPadding(padding, padding, padding, padding);
        mImageView = new ImageView(getContext());
        addView(mImageView, ToolSize.dp2Px(getContext(), 110), ToolSize.dp2Px(getContext(), 72));
        mTextView = new TextView(getContext());
        mTextView.setPadding(0, padding, 0, 0);
        addView(mTextView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setBackgroundColor(0xff00c3f5);
    }

    public void setImageUrl(String url) {
        Glide.with(this).load(url).into(mImageView);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }
}
