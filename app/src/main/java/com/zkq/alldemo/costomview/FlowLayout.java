package com.zkq.alldemo.costomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zkq
 * on 2018/4/25.
 */

public class FlowLayout extends ViewGroup {
    List<View> lineList = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置宽度和宽度的测量模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        //设置高度和高度的测量模式
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //wrap_content 时的测量
        int width = 0;
        int height = 0;
        //记录每一行的宽和高
        int lineWidth = 0;
        int lineHeight = 0;
        //获得内部元素的个数
        int sunCount = getChildCount();
        for (int i = 0; i < sunCount; i++) {
            //获取子控件
            View view = getChildAt(i);
            //测量子view的宽和高
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
            int childWidth = view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //判断是否要换行
            if (lineWidth + childWidth > sizeWidth - getPaddingRight() - getPaddingLeft()) {//去掉padding
                //宽度取行宽与子view宽度较大的一个
                width = Math.max(width, lineWidth);
                //下一行的行宽为子view的宽度
                lineWidth = childWidth;
                //高度累加
                height += lineHeight;
                lineHeight = childHeight;
            }
            //累加行宽
            lineWidth += childWidth;
            //行高取最大一个
            lineHeight = Math.max(lineHeight, childHeight);
            //最后一个子view特殊处理
            if (i == sunCount - 1) {
                width = Math.max(lineWidth, childWidth);
                height += lineHeight;
            }
        }
        //根据不同的测量模式，使用不同的宽高
        // wrap_parent -> MeasureSpec.AT_MOST
        // match_parent -> MeasureSpec.EXACTLY
        // 具体值 -> MeasureSpec.EXACTLY
        //UNSPECIFIED：不指定其大小测量模式，View想多大就多大，通常情况下在自定义View时才会使用
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingLeft(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom());
    }

    //存储子view，以行为单位
    private List<List<View>> mLineList = new ArrayList<>();
    //存储行高（因为行宽已经判断过了）
    private List<Integer> mHeightList = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLineList.clear();
        mHeightList.clear();
        lineList.clear();
        //当前viewgroup的宽度
        int width = getMeasuredWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        int sunCount = getChildCount();

        for (int i = 0; i < sunCount; i++) {
            View view = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
            int childWidth = view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();
            //如果需要换行
            if (childWidth + lineWidth + lp.rightMargin + lp.leftMargin > width - getPaddingLeft() - getPaddingRight()) {
                //添加一行view
                mLineList.add(lineList);
                //添加一行高度
                mHeightList.add(lineHeight);
                //重置行宽和行高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                lineList = new ArrayList<>();
            }
            //不管是否需要换行，这一个子view都要添加进去，所以不用else，因为每次都要执行
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineList.add(view);
        }
        //处理最后一行
        mLineList.add(lineList);
        mHeightList.add(lineHeight);
        //设置子view位置
        int top = getPaddingTop();
        int left = getPaddingLeft();
        int lineNumber = mLineList.size();

        for (int i = 0; i < lineNumber; i++) {
            //当前行所有的view
            lineList = mLineList.get(i);
            //当前行高度
            lineHeight = mHeightList.get(i);
            //处理一行
            for (int j = 0; j < lineList.size(); j++) {
                View view = lineList.get(j);
                if (view.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
                int viewLeft = left + lp.leftMargin;
                int viewTop = top + lp.topMargin;
                int viewRight = viewLeft + view.getMeasuredWidth();
                int viewBottom = viewTop + view.getMeasuredHeight();
                view.layout(viewLeft, viewTop, viewRight, viewBottom);
                left += view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
