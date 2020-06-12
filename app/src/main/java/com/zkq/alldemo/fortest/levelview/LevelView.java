package com.zkq.alldemo.fortest.levelview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @author zkq
 * time: 2019/11/6 3:55 PM
 * email: zkq815@126.com
 * desc:
 */
public class LevelView extends LinearLayout {

    public LevelView(Context context) {
        this(context, null);
    }

    public LevelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LevelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
