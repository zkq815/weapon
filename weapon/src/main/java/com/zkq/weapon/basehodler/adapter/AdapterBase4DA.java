package com.zkq.weapon.basehodler.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.Range;
import com.zkq.weapon.basehodler.holder.BaseViewHolder;
import com.zkq.weapon.market.tools.ToolList;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

/**
 * @author zkq
 * create:2019/5/28 11:35 PM
 * email:zkq815@126.com
 * desc: Vlayout Adapter基类 也可以当普通
 */
public abstract class AdapterBase4DA<VH extends BaseViewHolder, T> extends DelegateAdapter.Adapter<VH> {

    /**
     * 未匹配到代理的类型
     */
    public final int TYPE_NONE = -1;

    private LayoutHelper layoutHelper;
    private Context mContext;
    private List<T> mList;
    private LayoutInflater mLayoutInflater;
    private AdapterRecyclerBase.OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    private AdapterRecyclerBase.OnRecyclerItemLongClickListener mOnRecyclerItemLongClickListener;
    private int itemType = TYPE_NONE;

    public AdapterBase4DA(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public AdapterBase4DA(Context context, T item) {
        this.mContext = context;
        if (item != null) {
            this.mList = new ArrayList<>();
            this.mList.add(item);
        }
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setLayoutHelper(@NonNull LayoutHelper layoutHelper) {
        this.layoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemViewType(int position) {
        return itemType < 0 ? super.getItemViewType(position) : itemType;
    }

    /**
     * PS：子类重写时 一定要带上 <br/>
     * super.onBindViewHolder(viewHolder, position);
     */
    @Override
    @CallSuper
    public void onBindViewHolder(VH holder, int position) {

        holder.setOnRecyclerItemClickListener(mOnRecyclerItemClickListener);
        holder.setOnRecyclerItemLongClickListener(mOnRecyclerItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    /**
     * 获取偏移量
     *
     * @return
     */
    public int getOffset() {
        if (layoutHelper != null) {
            Range<Integer> range = layoutHelper.getRange();
            Integer lower = range.getLower();
            return lower < 0 ? 0 : lower;
        }
        return 0;
    }

    /**
     * NOTE: 这个方法在 Vlayout 中才会调用, 普通的只会调用{@link #onBindViewHolder(BaseViewHolder, int)}
     *
     * @param holder
     * @param position
     * @param offsetTotal
     */
    @Override
    protected final void onBindViewHolderWithOffset(VH holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
    }

    /**
     * NOTE: 这个方法在 Vlayout 中才会调用, 普通的只会调用{@link #onBindViewHolder(BaseViewHolder, int)}
     *
     * @param holder
     * @param position
     * @param offsetTotal
     */
    @Override
    protected final void onBindViewHolderWithOffset(VH holder, int position, int offsetTotal, List<Object> payloads) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal, payloads);
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

    public void addAll(List<T> list) {
        if (mList != null) {
            mList.addAll(list);
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

    public void setList(List<T> list) {
        this.mList = list;
    }

    public void setItem(T item) {
        if (item == null) {
            mList = null;
            return;
        }
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        mList.add(item);
    }

    public void setOnItemClickListener(AdapterRecyclerBase.OnRecyclerItemClickListener onItemClickListener) {
        if (onItemClickListener != null) {
            this.mOnRecyclerItemClickListener = onItemClickListener;
        }
    }

    public void setOnItemLongClickLitener(AdapterRecyclerBase.OnRecyclerItemLongClickListener onItemLongClickListener) {
        if (onItemLongClickListener != null) {
            this.mOnRecyclerItemLongClickListener = onItemLongClickListener;
        }
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
