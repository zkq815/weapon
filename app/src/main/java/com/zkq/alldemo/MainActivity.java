package com.zkq.alldemo;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zkq.alldemo.databinding.ActivityMainBinding;
import com.zkq.weapon.base.BaseActivity;
import com.zkq.weapon.market.util.ZLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/**
 * @author zkq
 * create:2019/5/28 12:54 AM
 * email:zkq815@126.com
 * desc:
 */
public class MainActivity extends BaseActivity {
    private ActivityMainBinding mBinding;
    private String path = "com.zkq.alldemo.fortest";
    private RecyclerView rv;
    private Button btnEventBus;
    String[] info = {".colorprogresswithspeed.ColorProgressActivity"
            , ".okhttp.OKHttpActivity", ".dialog.DialogTestActivity"
            , ".flowlayout.FlowLayoutActivity"
            , ".actionbar.MyToolbarActivity"
            , ".scaleanimation.ScaleAnimationActivity"
            , ".scrollclash.ScrollClashActivity"
            , ".rxjava_retrofit.RxjavaActivity"
            , ".fingertest.FingerprintMainActivity"
            , ".countdown.demo1.CountdownActivity"
            , ".countdown.demo2.Demo2Activity"
            , ".subscreen.SubScreenActivity"
            , ".lottieanimal.LottieAnimalActivity"
            , ".tangram.TangramTestActivity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        isShowBack(false);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }

    private void init() {
        rv = mBinding.rv;
        btnEventBus = mBinding.btnEvent;
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(new MainAdapter(this));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void btnChange(String text){
        ZLog.e("方法一");
        btnEventBus.setText(text);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void btnChangeTwo(String text){
        ZLog.e("方法二");
        btnEventBus.setText(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
                itemHolder.tvActivityNameOne.setText(info[position].split("\\.")[length-1]);
                itemHolder.tvActivityNameOne.setOnClickListener(v->{
                    try {
                        startActivity(new Intent(MainActivity.this
                                , Class.forName(path+info[position])));

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
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
        TextView tvActivityNameOne;
        TextView tvActivityNameTwo;

        ItemHolder(View view) {
            super(view);
            tvActivityNameOne = (TextView) view.findViewById(R.id.tv_one);
            tvActivityNameTwo = (TextView) view.findViewById(R.id.tv_two);
        }
    }


}
