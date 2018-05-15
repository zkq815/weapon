package com.zkq.weapon.customview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
/**
 * @author zkq
 * 简单的线性布局，使得子view自动换行排列
 */
public class FlowLayout extends ViewGroup{

    private List<View> mLineViews = new ArrayList<>();
    private List<List<View>> mLineList = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();

    public FlowLayout(Context context){
        this(context,null);
    }

    public FlowLayout(Context context,AttributeSet attr){
        this(context,attr,0);
    }

    public FlowLayout(Context context,AttributeSet attr, int defStyleAttr){
        super(context,attr,defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mLineList.clear();
        mLineViews.clear();
        //获取宽度以及宽的测量模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        //获取高度以及高的测量模式
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
        //每一行的宽高
        int lWidth = 0;
        int lHeight =0;
        //定义每一个view的宽高和
        int vWidth = 0;
        int vHeight = 0;
        int lineWidthMax = 0;//定义所有行中最宽的一行，为viewgroup的最大宽度
        int groupHeight = 0;
        int viewCount = getChildCount();
        for (int i = 0; i < viewCount; i++) {
            View view = getChildAt(i);
            //测量子view
            measureChild(view,widthMeasureSpec,heightMeasureSpec);
            vWidth = view.getMeasuredWidth();
            vHeight = view.getMeasuredHeight();
            lHeight = Math.max(lHeight,vHeight);
            //判断是否需要换行，只处理换行的逻辑和换行后的数据重置
            if(lWidth + vWidth > widthSize - getPaddingLeft() - getPaddingRight()){
                //存储最大行宽
                lineWidthMax = Math.max(lineWidthMax,lWidth);
                mLineHeight.add(lHeight);
                lHeight = 0;
                lHeight += vHeight;
                //已行为单位存储view列表
                mLineList.add(mLineViews);
                mLineViews = new ArrayList<>();
                lWidth = 0;
                groupHeight +=lHeight;
            }
            //不需要换行
            //宽度累加
            lWidth += vWidth;
            //为每一行存储view
            mLineViews.add(view);
        }
        mLineList.add(mLineViews);
        groupHeight +=lHeight;
        //根据测量模式传递宽高
        //根据不同的测量模式，使用不同的宽高
        // wrap_parent -> MeasureSpec.AT_MOST
        // match_parent -> MeasureSpec.EXACTLY
        // 具体值 -> MeasureSpec.UNSPECIFIED
        //UNSPECIFIED：不指定其大小测量模式，View想多大就多大，通常情况下在自定义View时才会使用
        setMeasuredDimension(widthSpec == MeasureSpec.EXACTLY?widthSize:lineWidthMax + getPaddingLeft() + getPaddingLeft(),
                heightSpec == MeasureSpec.EXACTLY?heightSize:groupHeight + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineHeight= 0;
        //1、每一行遍历
        for (int i = 0; i < mLineList.size(); i++) {
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < mLineList.get(i).size(); j++) {
                View view = mLineList.get(i).get(j);
                if(view.getVisibility()!= GONE){
                    view.layout(left,top,left+view.getMeasuredWidth(),top+view.getMeasuredHeight());
                    left+=view.getMeasuredWidth();
                }
            }
            left = getPaddingLeft();
            top +=lineHeight;
        }
    }
}
