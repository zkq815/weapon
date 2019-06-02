package com.zkq.weapon.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


import com.zkq.weapon.basehodler.adapter.AdapterLoadMoreForRecyclerView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author zkq
 * create:2019/5/28 11:57 PM
 * email:zkq815@126.com
 * desc: 重写RecyclerView 实现上拉加载更多功能
 */
public class RecyclerViewLoadMore extends RecyclerView {
    //进行自动加载时最后显示的位置默认值
    private static final int LOADED_MORE_DEFAULT_POSITION = 3;
    //是否允许加载更多
    private boolean mIsLoadMoreEnable = true;

    private AdapterLoadMoreForRecyclerView mLoadMoreAdapter;
    private OnLoadMoreListener mOnLoadMoreListener;
    private OnRecycleViewScrollListener mOnRecycleViewScrollListener;

    //正在加载更多标示
    private boolean mIsLoadingMore;

    //加载失败标示
    private boolean mIsLoadFailed;

    //失败后重新加载标示
    private boolean mIsLoadingAgain;

    //自动加载时的最后显示的位置参数
    private int mLastLoadedIndex;

    /**
     * 没有条目时显示的视图
     */
    private View mEmptyView;

    public RecyclerViewLoadMore(Context context) {
        super(context);
        initScrollListener();
    }

    public RecyclerViewLoadMore(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initScrollListener();
    }

    public RecyclerViewLoadMore(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initScrollListener();
    }

    public boolean isLoadMoreEnable() {
        return mIsLoadMoreEnable;
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.mIsLoadMoreEnable = loadMoreEnable;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener OnLoadMoreListener) {
        this.mOnLoadMoreListener = OnLoadMoreListener;
    }

    public boolean isLoadingMore() {
        return mIsLoadingMore;
    }

    public void setLoadingMore(boolean loadingMore) {
        this.mIsLoadingMore = loadingMore;
    }

    public void setRecycleViewScrollListener(OnRecycleViewScrollListener scrollListener) {
        this.mOnRecycleViewScrollListener = scrollListener;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mLoadMoreAdapter == null) {
            mLoadMoreAdapter = new AdapterLoadMoreForRecyclerView(this);
            mLoadMoreAdapter.registerAdapterDataObserver(emptyObserver);
        }
        if (adapter != null) {
            mLoadMoreAdapter.setInternalAdapter(adapter);
        }
        super.setAdapter(mLoadMoreAdapter);
    }

    /**
     * 监听列表数据的变化判断是否显示 emptyView
     */
    private final AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }

    };

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof GridLayoutManager) {
            mLastLoadedIndex = ((GridLayoutManager) layout).getSpanCount();
        }
        super.setLayoutManager(layout);
    }

    /**
     * 通知更多的数据已经加载
     * 每次加载完成之后添加了Data数据，用notifyItemRemoved来刷新列表展示，
     * 而不是用notifyDataSetChanged来刷新列表
     *
     * @param hasMore 是否还有更多数据
     */
    public void loadMoreFinish(boolean hasMore) {
        mIsLoadingMore = false;
        if (getAdapter() != null) {
            getAdapter().notifyItemRemoved(getAdapter().getItemCount() - 1);
        }
        setLoadMoreEnable(hasMore);
    }

    /**
     * 加载失败时调用
     */
    public void loadMoreFailed() {
        mIsLoadFailed = true;
        loadMoreFinish(false);
    }

    /**
     * 加载失败时，点击重新加载更多
     */
    public void loadMoreAgainForClick() {
        if (!mIsLoadFailed || mOnLoadMoreListener == null || mLoadMoreAdapter == null) {
            return;
        }
        loadMoreAgain();
        this.scrollToPosition(mLoadMoreAdapter.getItemCount() - 1);
        mOnLoadMoreListener.onLoadMore();
    }

    private void initScrollListener() {
        mLastLoadedIndex = LOADED_MORE_DEFAULT_POSITION;
        ((DefaultItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);
        super.addOnScrollListener(new OnLoadMoreScrollListener());
    }

    /**
     * @return 获取最后一个显示的位置
     */
    private int getLastVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * @param positions 位置数据
     * @return 获得最大的位置
     */
    private int getMaxPosition(int[] positions) {
        int maxPosition = Integer.MIN_VALUE;
        for (int position : positions) {
            maxPosition = Math.max(maxPosition, position);
        }
        return maxPosition;
    }

    /**
     * 实现重新加载更多
     */
    private void loadMoreAgain() {
        mIsLoadFailed = false;
        mIsLoadingAgain = true;
        setLoadMoreEnable(true);
        if (getAdapter() != null) {
            getAdapter().notifyItemInserted(mLoadMoreAdapter.getItemCount());
        }
    }

    /**
     * 设置适配器为空时显示的视图
     *
     * @param emptyView 适配器为空时显示的视图
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        if (emptyView != null
                && emptyView.getImportantForAccessibility() == IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
            emptyView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
        }
        checkIfEmpty();
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    private void checkIfEmpty() {
        boolean isEmpty = getAdapter() == null || getAdapter().getItemCount() == 0;
        if (mEmptyView != null) {
            mEmptyView.setVisibility(isEmpty ? VISIBLE : GONE);
            setVisibility(isEmpty ? GONE : VISIBLE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    /**
     * 滑动监听
     */
    public interface OnRecycleViewScrollListener {
        void onScrollStateChanged(RecyclerView recyclerView, int newState);

        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

    /**
     * 加载更多监听
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    /**
     * 滑动监听，用于处理加载更多功能 <br/>
     */
    private class OnLoadMoreScrollListener extends OnScrollListener {

        private int mNorScrollY;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (mNorScrollY >= 0 && mIsLoadFailed && newState == SCROLL_STATE_DRAGGING) {
                loadMoreAgain();
            }
            if (mOnRecycleViewScrollListener != null) {
                mOnRecycleViewScrollListener.onScrollStateChanged(recyclerView, newState);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mNorScrollY = dy;
            if (null != mOnLoadMoreListener && mIsLoadMoreEnable && !mIsLoadingMore && dy > 0) {

                int lastVisiblePosition = getLastVisiblePosition();
                if (lastVisiblePosition >= mLoadMoreAdapter.getItemCount() - mLastLoadedIndex ||
                        (mIsLoadingAgain && lastVisiblePosition == mLoadMoreAdapter.getItemCount() - 1)) {//自动加载
                    mIsLoadingAgain = false;
                    setLoadingMore(true);
                    mOnLoadMoreListener.onLoadMore();
                }
            }
            if (mOnRecycleViewScrollListener != null) {
                mOnRecycleViewScrollListener.onScrolled(recyclerView, dx, dy);
            }
        }

    }

}
