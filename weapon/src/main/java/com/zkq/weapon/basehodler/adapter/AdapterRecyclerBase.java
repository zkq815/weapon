package com.zkq.weapon.basehodler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.zkq.weapon.basehodler.holder.BaseViewHolder;
import com.zkq.weapon.market.tools.ToolList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zkq
 * create:2019/5/28 11:34 PM
 * email:zkq815@126.com
 * desc: Recycler Adapter的基类 --- 实现了每个Item的点击监听
 */
public abstract class AdapterRecyclerBase<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    private Context mContext;
    private List<T> mList;
    private LayoutInflater mLayoutInflater;
    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    private OnRecyclerItemLongClickListener mOnRecyclerItemLongClickListener;

    public AdapterRecyclerBase(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * PS：子类重写时 一定要带上 <br/>
     * super.onBindViewHolder(viewHolder, position);
     */
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (holder instanceof BaseViewHolder) {
            BaseViewHolder baseViewHolder = (BaseViewHolder) holder;

            if (!baseViewHolder.hasClick() && mOnRecyclerItemClickListener != null) {
                baseViewHolder.setOnRecyclerItemClickListener(mOnRecyclerItemClickListener);
            }

            if (!baseViewHolder.hasLongClick() && mOnRecyclerItemLongClickListener != null) {
                baseViewHolder.setOnRecyclerItemLongClickListener(mOnRecyclerItemLongClickListener);
            }
        }

    }

    @Override
    public int getItemCount() {
        return ToolList.isNullOrEmpty(mList) ? 0 : mList.size();
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public Context getContext() {
        return mContext;
    }

    public void setList(List<T> list) {
        this.mList = list;
    }

    public void addAll(List<T> list) {
        if (mList != null) {
            mList.addAll(list);
        }
    }

    public void addAll(int index, List<T> list) {
        if (mList != null) {
            mList.addAll(index, list);
        }
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
    }

    public List<T> getList() {
        return mList;
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener) {
        if (onItemClickListener != null) {
            this.mOnRecyclerItemClickListener = onItemClickListener;
        }
    }

    public void setOnItemLongClickLitener(OnRecyclerItemLongClickListener onItemLongClickListener) {
        if (onItemLongClickListener != null) {
            this.mOnRecyclerItemLongClickListener = onItemLongClickListener;
        }
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnRecyclerItemLongClickListener {
        void onItemLongClick(View v, int position);
    }

    /**
     * 创建每个Item视图的spmItemValue值 <br/>
     * PS:用于OnItemClick方法中对spmItemValue赋值
     *
     * @param position 列表下标
     * @return itemSpm值
     */
    public String createSpmItemValue(int position) {
        //子类重写
        return null;
    }

}
