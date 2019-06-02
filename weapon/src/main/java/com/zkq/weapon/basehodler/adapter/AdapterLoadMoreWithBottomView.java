package com.zkq.weapon.basehodler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zkq.weapon.R;
import com.zkq.weapon.customview.RecyclerViewLoadMoreWithBottomView;
import com.zkq.weapon.market.tools.ToolException;
import com.zkq.weapon.market.tools.ToolView;

import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author zkq
 * create:2019/5/28 11:36 PM
 * email:zkq815@126.com
 * desc: 实现上拉加载更多功能且当不能加载更多时底部自动添加自定义的bottomView
 */
public abstract class AdapterLoadMoreWithBottomView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_LOAD_MORE = 2000;
    private final static int TYPE_BOTTOM_VIEW = 2001;

    private RecyclerView.Adapter mInternalAdapter;
    private RecyclerViewLoadMoreWithBottomView mRecyclerView;
    private AdapterLoadMoreDataObserver mObserver;
    private View.OnClickListener mOnClickListener;
    private int[] mResIds;

    public AdapterLoadMoreWithBottomView(RecyclerViewLoadMoreWithBottomView recyclerViewLoadMore) {
        this.mRecyclerView = recyclerViewLoadMore;
        mObserver = new AdapterLoadMoreDataObserver();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOAD_MORE) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.m_base_layout_recycler_load_more, parent, false), TYPE_LOAD_MORE);
        } else if (viewType == TYPE_BOTTOM_VIEW) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    getBomttomViewResId(), parent, false), TYPE_BOTTOM_VIEW);
        }
        return mInternalAdapter.onCreateViewHolder(parent, viewType);
    }

    @LayoutRes
    public abstract int getBomttomViewResId();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //不实现
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        int type = getItemViewType(position);
        if (type != TYPE_LOAD_MORE && type != TYPE_BOTTOM_VIEW) {
            mInternalAdapter.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder.getItemViewType() != TYPE_LOAD_MORE && holder.getItemViewType() != TYPE_BOTTOM_VIEW) {
            mInternalAdapter.onViewAttachedToWindow(holder);
        } else if (holder instanceof FooterViewHolder) {
            //为了适应瀑布流加载更多，单行铺满.
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            ViewGroup.LayoutParams layoutParams = footerViewHolder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p
                        = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder.getItemViewType() != TYPE_LOAD_MORE) {
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
        if (countTemp != 0) {
            countTemp++;
        }
        return countTemp;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return mRecyclerView.isLoadMoreEnable() ? TYPE_LOAD_MORE : TYPE_BOTTOM_VIEW;
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

    public void setBottomViewClickListener(View.OnClickListener listener, @IdRes int... resIds) {
        this.mOnClickListener = listener;
        this.mResIds = resIds;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterViewHolder(View itemView, int viewType) {
            super(itemView);
            // 为bottom view添加点击事件
            if (mOnClickListener != null && mResIds != null && viewType == TYPE_BOTTOM_VIEW) {
                for (int resId : mResIds) {
                    View view = itemView.findViewById(resId);
                    if (view != null) {
                        ToolView.setOnClickListener(mOnClickListener, view);
                    }
                }
            }
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
