package com.zkq.alldemo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zkq.alldemo.base.BaseActivity;
import com.zkq.alldemo.costomview.FlowLayout;
import com.zkq.alldemo.databinding.ActivityMainBinding;
import com.zkq.alldemo.network.WeakRefDataListener;
import com.zkq.alldemo.network.exception.NetworkException;
import com.zkq.alldemo.network.netdemo.BaseBean;
import com.zkq.alldemo.network.netdemo.TestNetHelper;
import com.zkq.alldemo.util.ZKQLog;

import java.util.Arrays;
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
    //lambda语法测试
    private void test(){
        List<Shape> shapes = null;
        Runnable run = ()->Log.e("","");
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


    private void testNetRequest() {
        TestNetHelper testNetHelper = new TestNetHelper();
        testNetHelper.post("http://app.store.res.meizu.com/mzstore/home/get/v2", new NetWortDataListener(new BaseBean()));
    }

    /**
     * 请求监听回调
     * 第一个参数为Presenter
     * 第二个参数为返回的数据对象
     */
    private static class NetWortDataListener extends WeakRefDataListener<BaseBean, BaseBean> {
        NetWortDataListener(BaseBean arg1) {
            super(arg1);
        }

        @Override
        protected void onSuccess(@NonNull BaseBean baseBean, @NonNull BaseBean data) {

        }

        @Override
        public void onError(@NonNull BaseBean baseBean, @NonNull NetworkException error) {

        }
    }


}
