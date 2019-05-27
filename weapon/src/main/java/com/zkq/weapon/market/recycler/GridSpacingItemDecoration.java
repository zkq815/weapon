package com.zkq.weapon.market.recycler;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * @author zkq
 * create:2018/11/16 10:21 AM
 * email:zkq815@126.com
 * desc: recyclerView 网格布局item间隔
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
        // item position
        int position = parent.getChildAdapterPosition(view);

        int count = parent.getAdapter().getItemCount();
        // item column
        int row = count / spanCount + count % spanCount;
        outRect.top = spacing;
        if ((position % spanCount) == 0) {
            //左边列表左边距8dp
            outRect.left = spacing * 2;
        } else {
            outRect.left = spacing;
            //右边列表右边距8dp
            outRect.right = spacing * 2;
        }
        if ((((position + 1) / spanCount) + ((position + 1) % spanCount)) == row) {
            outRect.bottom = spacing;
        }
    }

}
