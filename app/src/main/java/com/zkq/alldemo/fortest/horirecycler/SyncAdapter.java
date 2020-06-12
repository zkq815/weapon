package com.zkq.alldemo.fortest.horirecycler;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zkq.alldemo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zkq
 * time: 2019/11/15 3:55 PM
 * email: zkq815@126.com
 * desc:
 */
public class SyncAdapter extends RecyclerView.Adapter<SyncAdapter.ScrollViewHolder> {

    private final SynScrollerLayout mSynScrollerview;
    private final List<String> mData;

    public SyncAdapter(@Nullable List<String> data, SynScrollerLayout synScrollerview) {
        mSynScrollerview = synScrollerview;
        mData = data;

    }

    @NonNull
    @Override
    public ScrollViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(viewGroup.getContext(), R.layout.item_scroll_layout2, null);


        return new ScrollViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final ScrollViewHolder holder, final int position) {

        holder.mView.setText(mData.get(position));
        mSynScrollerview.setOnScrollListener(new SynScrollerLayout.OnItemScrollView() {
            @Override
            public void OnScroll(int l, int t, int oldl, int oldt) {
                holder.mSynScrollerLayout.smoothScrollTo(l, 0);
            }

        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mSynScrollerview.onTouchEvent(v, position, event);
                return false;
            }
        });
        holder.ll_view.setVisibility(holder.ll_view.getTag() == null ? View.GONE:(((int) holder.ll_view.getTag())==position? View.VISIBLE: View.GONE));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ScrollViewHolder extends RecyclerView.ViewHolder {

        public final TextView mView;
        public final SynScrollerLayout mSynScrollerLayout;
        public final LinearLayout mChildRoot;
        public final LinearLayout ll_view;

        public ScrollViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.tv);
            mSynScrollerLayout = itemView.findViewById(R.id.synscrollerview);
            mChildRoot = itemView.findViewById(R.id.ll_child_root);
            ll_view = itemView.findViewById(R.id.ll_view);
            for (int i = 0; i < 20; i++) {
                View inflate = View.inflate(itemView.getContext(), R.layout.item_child_layout, null);
                TextView name = inflate.findViewById(R.id.tv);
                name.setText("内容" + i);
                mChildRoot.addView(inflate);
            }
        }
    }
}
