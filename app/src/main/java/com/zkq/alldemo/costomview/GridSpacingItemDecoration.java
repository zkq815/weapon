package com.zkq.alldemo.costomview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * modefy by zkq
 * on 2016/4/6.
 *
 * recyclerView 网格布局item间隔
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;

    public GridSpacingItemDecoration(int spanCount, int spacing) {
        this.spanCount = spanCount;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position

        int count = parent.getAdapter().getItemCount();
        int row = count / spanCount + count % spanCount; // item column
        outRect.top = spacing;
        if ((position % spanCount) == 0) {
            outRect.left = spacing * 2;//与首页保持一致，左边列表左边距8dp
        } else {
            outRect.left = spacing;
            outRect.right = spacing * 2;//与首页保持一致，右边列表右边距8dp
        }
        if ((((position + 1) / spanCount) + ((position + 1) % spanCount)) == row) {
            outRect.bottom = spacing;
        }
    }

}
