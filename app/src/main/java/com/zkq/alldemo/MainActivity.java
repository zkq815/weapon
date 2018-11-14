package com.zkq.alldemo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zkq.alldemo.base.BaseActivity;
import com.zkq.alldemo.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding mBinding;
    private String path = "com.zkq.alldemo.fortest";
    String[] info = {".colorprogresswithspeed.ColorProgressActivity",
            ".okhttp.OKHttpActivity",
            ".dialog.DialogTestActivity",
            ".flowlayout.FlowLayoutActivity",
            ".actionbar.MyToolbarActivity",
            ".scaleanimation.ScaleAnimationActivity",
            ".scrollclash.ScrollClashActivity",
            ".rxjava_retrofit.RxjavaActivity"};
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }

    private void init() {
        rv = mBinding.rv;
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MainAdapter(this));
    }

    class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context context;
        LayoutInflater layoutInflater;

        MainAdapter(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(this.context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(layoutInflater.inflate(R.layout.main_item_viewholder, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int index) {
            final int position = index;
            ItemHolder itemHolder;
            if (holder instanceof ItemHolder) {
                itemHolder = (ItemHolder) holder;
                int length = info[position].split("\\.").length;
                itemHolder.tvAcivityName.setText(info[position].split("\\.")[length-1]);
                itemHolder.tvAcivityName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            startActivity(new Intent(MainActivity.this, Class.forName(path+info[position])));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return info.length;
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView tvAcivityName;

        ItemHolder(View view) {
            super(view);
            tvAcivityName = (TextView) view.findViewById(R.id.tv_activity);
        }
    }

}
