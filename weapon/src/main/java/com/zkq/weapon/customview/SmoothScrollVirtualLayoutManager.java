package com.zkq.weapon.customview;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import com.alibaba.android.vlayout.VirtualLayoutManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zkq
 * create:2019/5/28 11:59 PM
 * email:zkq815@126.com
 * desc: RecycleView缓慢滑动LayoutManager
 */
public class SmoothScrollVirtualLayoutManager extends VirtualLayoutManager {

    /**
     * 是否可以缓慢滑动
     */
    private boolean mSmoothScrollEnable;

    /**
     * 默认速度
     * {@link LinearSmoothScroller#MILLISECONDS_PER_INCH}
     */
    private static final float MILLISECONDS_PER_INCH = 25.0f;

    /**
     * 滑动相对速度 值越大越慢
     */
    private float mSmoothScrollSpeed = MILLISECONDS_PER_INCH;

    public SmoothScrollVirtualLayoutManager(@NonNull Context context) {
        super(context);
    }

    public SmoothScrollVirtualLayoutManager(@NonNull Context context, int orientation) {
        super(context, orientation);
    }

    public SmoothScrollVirtualLayoutManager(@NonNull Context context, int orientation, boolean
            reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public void setSmoothScrollEnable(boolean enable) {
        this.mSmoothScrollEnable = enable;
    }

    /**
     * 设置滑动相对速度 值越大越慢
     */
    public void setSmoothScrollSpeed(float speed) {
        this.mSmoothScrollEnable = true;
        this.mSmoothScrollSpeed = speed;
    }

    public void smoothScrollToPosition(int position, float speed) {
        this.mSmoothScrollEnable = true;
        this.mSmoothScrollSpeed = speed;
        getRecyclerView().smoothScrollToPosition(position);
    }

    //实现缓慢滑动
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state
            , int position) {
        if (!mSmoothScrollEnable) {
            super.smoothScrollToPosition(recyclerView, state, position);
            return;
        }
        LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(recyclerView.getContext()) {
                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        return SmoothScrollVirtualLayoutManager.this
                                .computeScrollVectorForPosition(targetPosition);
                    }

                    //This returns the milliseconds it takes to scroll one pixel.
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        //返回滑动一个pixel需要多少毫秒
                        return mSmoothScrollSpeed / displayMetrics.density;
                    }

                    @Override
                    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart
                            , int boxEnd, int snapPreference) {
                        return boxStart - viewStart;
                    }
                };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
}
