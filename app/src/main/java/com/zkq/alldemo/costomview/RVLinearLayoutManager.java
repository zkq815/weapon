package com.zkq.alldemo.costomview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by zkq
 * on 2018/5/18.
 * 抓取Recyclerview异常
 * java.lang.IndexOutOfBoundsException: Inconsistency detected
 */

public class RVLinearLayoutManager extends LinearLayoutManager {
    public RVLinearLayoutManager(Context context) {
        super(context);
    }

    public RVLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public RVLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
