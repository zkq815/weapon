package com.zkq.weapon.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zkq.weapon.R;
import com.zkq.weapon.databinding.ActivityThanksBinding;
import com.zkq.weapon.market.toast.ZToast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zkq
 * create:2019/5/28 12:54 AM
 * email:zkq815@126.com
 * desc:
 */
public class ThanksActivity extends BaseActivity {
    private ActivityThanksBinding mBinding;
    private RecyclerView rv;
    String[] info = {
            "感谢每天努力的自己，每天进步一点点"
            ,"感谢老婆、家人给予的支持！"
            ,"com.squareup.okhttp3:okhttp"
            , "com.squareup.retrofit2:retrofit"
            , "io.reactivex.rxjava2:rxjava"
            , "io.reactivex.rxjava2:rxandroid"
            , "com.google.code.gson:gson"
            , "com.alibaba:fastjson"
            , "org.greenrobot:eventbus"
            , "com.squareup.picasso:picasso"
            , "com.github.bumptech.glide:glide"
            , "com.alibaba.android:tangram"
            , "com.alibaba.android:vlayout"
            , "com.alibaba.android:virtualview"
            , "com.alibaba.android:ultraviewpager"
            , "com.alibaba:arouter-compiler"
            , "com.alibaba:arouter-api"
            , "com.annimon:stream"
            , "com.alibaba.android:ultraviewpager"
            , "com.tencent:mmkv"
            , "com.airbnb.android:lottie"
            , "com.google.zxing:core"
            , "com.squareup.leakcanary"
            , "com.google.code.findbugs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_thanks);
        getSupportActionBar().setTitle(getString(R.string.weapon_thanks_for_open));
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
            return new ItemHolder(layoutInflater.inflate(R.layout.simple_item_viewholder, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int index) {
            final int position = index;
            ItemHolder itemHolder;
            if (holder instanceof ItemHolder) {
                itemHolder = (ItemHolder) holder;
                itemHolder.tvTksName.setText(info[position]);
                itemHolder.tvTksName.setOnClickListener(v->{
                    ZToast.show("thanks for :"+info[position]);
                });
            }
        }

        @Override
        public int getItemCount() {
            return info.length;
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView tvTksName;

        ItemHolder(View view) {
            super(view);
            tvTksName = (TextView) view.findViewById(R.id.tv_show);
        }
    }

}
