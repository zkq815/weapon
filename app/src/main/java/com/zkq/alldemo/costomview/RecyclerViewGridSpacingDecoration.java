package com.zkq.alldemo.costomview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zkq
 * on 2017/5/30.
 */
public class RecyclerViewGridSpacingDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;

    public RecyclerViewGridSpacingDecoration(int spanCount, int spacing) {
        this.spanCount = spanCount;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int count = parent.getAdapter().getItemCount();
        int row  = count%spanCount==0?count/spanCount :count/spanCount +1; // item column
        //判断是否是第一行
//        if(position/spanCount ==0){
//            outRect.top = 0;
//        }else{
            outRect.top = spacing;
//        }

        //判断是否是最后一行
//        if(position/spanCount == row  || (position/spanCount +1) == row){
//            outRect.bottom = 0;
//        }else{
//            outRect.bottom = spacing;
//        }
        //判断是否是第一列
        if(position%spanCount == 0){
            outRect.left = 0;
        }else{
            outRect.left = spacing;
        }
        //判断是否是最后一列
//        if(position%spanCount == (spanCount-1)){
//            outRect. right = 0;
//        }else{
//            outRect.right = spacing;
//        }
    }
}
