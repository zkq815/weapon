package com.zkq.weapon.basehodler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zkq.weapon.R;
import com.zkq.weapon.customview.RecyclerViewLoadMore;
import com.zkq.weapon.market.tools.ToolException;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author zkq
 * create:2019/5/28 11:36 PM
 * email:zkq815@126.com
 * desc: 实现RecyclerView 自动加载更多功能
 */
public class AdapterLoadMoreForRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int TYPE_FOOTER = 2000;

    private RecyclerView.Adapter mInternalAdapter;
    private RecyclerViewLoadMore mRecyclerView;
    private AdapterLoadMoreDataObserver mObserver;

    public AdapterLoadMoreForRecyclerView(RecyclerViewLoadMore recyclerViewLoadMore) {
        this.mRecyclerView = recyclerViewLoadMore;
        mObserver = new AdapterLoadMoreDataObserver();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.m_base_layout_recycler_load_more, parent, false));
        }
        return mInternalAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //不实现
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        int type = getItemViewType(position);
        if (type != TYPE_FOOTER) {
            mInternalAdapter.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder.getItemViewType() != TYPE_FOOTER) {
            mInternalAdapter.onViewAttachedToWindow(holder);
        } else if (holder instanceof FooterViewHolder) {
            //为了适应瀑布流加载更多，单行铺满.
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            ViewGroup.LayoutParams layoutParams = footerViewHolder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder.getItemViewType() != TYPE_FOOTER) {
            mInternalAdapter.onViewDetachedFromWindow(holder);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInternalAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mInternalAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        if (mInternalAdapter == null) {
            ToolException.throwNullPointerExceptionError("LoadMoreAdapter", "内部Adapter不能为空");
            return 0;
        }
        int countTemp = mInternalAdapter.getItemCount();
        if (mRecyclerView.isLoadMoreEnable() && countTemp != 0) {
            countTemp++;
        }
        return countTemp;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && mRecyclerView.isLoadMoreEnable()) {
            return TYPE_FOOTER;
        }
        return mInternalAdapter.getItemViewType(position);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof FooterViewHolder) {
            return;
        }
        mInternalAdapter.onViewRecycled(holder);
    }

    public void setInternalAdapter(RecyclerView.Adapter internalAdapter) {
        if (mInternalAdapter != null) {
            mInternalAdapter.unregisterAdapterDataObserver(mObserver);
        }
        this.mInternalAdapter = internalAdapter;
        if (internalAdapter != null) {
            internalAdapter.registerAdapterDataObserver(mObserver);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class AdapterLoadMoreDataObserver extends RecyclerView.AdapterDataObserver {

        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            notifyItemRangeChanged(positionStart, itemCount, payload);
        }
    }
}
