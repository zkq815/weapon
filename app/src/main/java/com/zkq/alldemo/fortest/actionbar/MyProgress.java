package com.zkq.alldemo.fortest.actionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.android.volley.toolbox.Volley;
import com.zkq.alldemo.R;

/**
 * Created by Administrator on 2016/12/2.
 */

public class MyProgress extends View {
    private int colorTitle,colorCircle,colorRingLike,color1,color2,color3,color4,color5,color6,color7,degree;
    private String strTitle;
    private float titleSize,mRadius,mCircleXY,screenWidth;
//    private RectF mArcRectf,

    public MyProgress(Context context) {
        this(context,null);
    }

    public MyProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MyProgress);
        colorTitle = ta.getColor(R.styleable.MyProgress_title_color,0);
        strTitle = ta.getString(R.styleable.MyProgress_title_string);
        titleSize = ta.getDimension(R.styleable.MyProgress_title_size,10);
        colorCircle = ta.getColor(R.styleable.MyProgress_circle_color,0);
        colorRingLike = ta.getColor(R.styleable.MyProgress_ring_like_color,0);
        color7 = ta.getColor(R.styleable.MyProgress_first_color,0);
        color1 = ta.getColor(R.styleable.MyProgress_second_color,0);
        color2 = ta.getColor(R.styleable.MyProgress_third_color,0);
        color3 = ta.getColor(R.styleable.MyProgress_fourth_color,0);
        color4 = ta.getColor(R.styleable.MyProgress_fifth_color,0);
        color5 = ta.getColor(R.styleable.MyProgress_sixth_color,0);
        color6 = ta.getColor(R.styleable.MyProgress_seventh_color,0);
        Volley.newRequestQueue(getContext());
        ta.recycle();
    }
}
