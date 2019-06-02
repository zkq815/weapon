package com.zkq.weapon.basehodler.holder;

import android.view.View;


import com.zkq.weapon.basehodler.adapter.AdapterRecyclerBase;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zkq
 * create:2019/5/28 11:54 PM
 * email:zkq815@126.com
 * desc: ViewHolder基类 -- 实现Item点击效果
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private AdapterRecyclerBase.OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    private AdapterRecyclerBase.OnRecyclerItemLongClickListener mOnRecyclerItemLongClickListener;
    private boolean mHasClick;//是否设置点击监听
    private boolean mHasLongClick;//是否设置长按监听
    private View mContainerView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContainerView = itemView;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        if (FastClickEventVManager.getInstance().isFastClick(v)) {
//            return;
//        }
        if (mOnRecyclerItemClickListener != null) {
            mOnRecyclerItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnRecyclerItemLongClickListener != null) {
            mOnRecyclerItemLongClickListener.onItemLongClick(v, getLayoutPosition());
        }
        return false;
    }

    public void setOnRecyclerItemLongClickListener(AdapterRecyclerBase.OnRecyclerItemLongClickListener onRecyclerItemLongClickListener) {
        mHasLongClick = true;
        this.mOnRecyclerItemLongClickListener = onRecyclerItemLongClickListener;
    }

    public void setOnRecyclerItemClickListener(AdapterRecyclerBase.OnRecyclerItemClickListener onRecyclerItemClickListener) {
        mHasClick = true;
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public boolean hasClick() {
        return mHasClick;
    }

    public boolean hasLongClick() {
        return mHasLongClick;
    }

    public View getContainerView() {
        return mContainerView;
    }
}
