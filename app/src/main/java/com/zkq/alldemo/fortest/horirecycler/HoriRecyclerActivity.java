package com.zkq.alldemo.fortest.horirecycler;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zkq.alldemo.R;
import com.zkq.alldemo.fortest.recyclertoviewpager.view.DividerItemDecoration;
import com.zkq.weapon.base.BaseActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HoriRecyclerActivity extends BaseActivity{

    @BindView(R.id.synscrollerview)
    SynScrollerLayout mSynScrollerview;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hori_recycler);
        ButterKnife.bind(this);
        LinearLayout linearLayout = findViewById(R.id.item_root);
        LinearLayout childRoot = findViewById(R.id.ll_child_root);
        linearLayout.setClickable(true);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            strings.add("左侧条目" + i);
        }

        for (int i = 0; i < 20; i++) {
            View inflate = View.inflate(this, R.layout.item_child_layout, null);
            TextView name = inflate.findViewById(R.id.tv);
            name.setText("类别" + i);
            childRoot.addView(inflate);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        SyncAdapter adapter = new SyncAdapter(strings, mSynScrollerview);
        recyclerView.setAdapter(adapter);


        recyclerView.setOnTouchListener(getListener());
        linearLayout.setOnTouchListener(getListener());

        mSynScrollerview.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(view.getContext(), "条目" + position, Toast.LENGTH_SHORT).show();
                try {
                    View viewById = view.findViewById(R.id.ll_view);
                    if (mView != null) {
                        mView.setVisibility(View.GONE);
                        viewById.setTag(null);
                    }
                    boolean b = viewById.getVisibility() == View.GONE;
                    if (b) {
                        viewById.setTag(position);
                        viewById.setVisibility(View.VISIBLE);
                        mView = viewById;
                    } else {
                        viewById.setTag(null);
                        viewById.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @NonNull
    private View.OnTouchListener getListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mSynScrollerview.onTouchEvent(motionEvent);
                return false;
            }
        };
    }



}
