package com.zkq.alldemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zkq.weapon.base.BaseActivity;
import com.zkq.weapon.base.WebViewPluginActivity;
import com.zkq.weapon.constants.WeaponConstants;
import com.zkq.weapon.market.util.ZLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zkq
 * create:2019/5/28 12:54 AM
 * email:zkq815@126.com
 * desc:
 */
public class MainActivity extends BaseActivity {
    private String path = "com.zkq.alldemo.fortest";
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.btn_story_one)
    Button btnEventBus;
    String[] info = {".colorprogresswithspeed.ColorProgressActivity"
            , ".okhttp.OKHttpActivity"
            , ".dialog.DialogTestActivity"
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
            , ".tangram.TangramTestActivity"
            , ".ijkplayer.VideoMediaActivity"
            , ".gsvideo.GSVideoActivity"
            , ".gsvideo.gsdemo.gsyvideoplayer.GsMainActivity"
            , "com.zkq.weapon.base.ScanActivity"
            , ".recyclertoviewpager.RecyclerToViewPagerActivity"
            , ".recyclertoviewpager.RecyclerToViewPagerDemoActivity"
            , ".gestureviewbinder.GestureViewBinderActivity"
            , ".textureview.TestTextureViewActivity"
            , ".drawerLayout.DrawerLayoutActivity"
            , ".levelview.LevelViewActivity"
            , ".horirecycler.HoriRecyclerActivity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        isShowBack(false);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        btnEventBus.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, WebViewPluginActivity.class);
            intent.putExtra(WeaponConstants.WEB_URL,WeaponConstants.NORMAL_TO_GOD_URL);
            intent.putExtra(WeaponConstants.WEB_TITLE,WeaponConstants.NORMAL_TO_GOD_TITLE);
            startActivity(intent);
        });
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
            return new ItemHolder(layoutInflater.inflate(R.layout.simple_item_viewholder, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int index) {
            final int position = index;
            ItemHolder itemHolder;
            if (holder instanceof ItemHolder) {
                itemHolder = (ItemHolder) holder;
                int length = info[position].split("\\.").length;
                itemHolder.tvActivityName.setText(info[position].split("\\.")[length-1]);
                itemHolder.tvActivityName.setOnClickListener(v->{
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
        TextView tvActivityName;

        ItemHolder(View view) {
            super(view);
            tvActivityName = (TextView) view.findViewById(R.id.tv_show);
        }
    }


}
