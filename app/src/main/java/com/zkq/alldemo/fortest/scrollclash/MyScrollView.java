package com.zkq.alldemo.fortest.scrollclash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by zkq
 * on 2018/5/14.
 */

public class MyScrollView extends ScrollView {


    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;





        return super.onInterceptTouchEvent(ev);
    }
}
