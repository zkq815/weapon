package com.zkq.alldemo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zkq.alldemo.databinding.ActivityMainBinding;
import com.zkq.weapon.base.BaseActivity;
import com.zkq.weapon.base.WebViewPluginActivity;
import com.zkq.weapon.constants.WeaponConstants;
import com.zkq.weapon.market.util.ZLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
            , ".lottieanimal.LottieAnimalActivity"};

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
        rv.setLayoutManager(new LinearLayoutManager(this));
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
                            startActivity(new Intent(MainActivity.this
                                    , Class.forName(path+info[position])));

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

    private void testParcelable(){

//        Parcel parcel = Parcel.obtain();
//        Book book = new Book("abcsd","asdasdasd");
//        book.writeToParcel(parcel, book.describeContents());
//        parcel.setDataPosition(0);
//        Book createdFromParcel = Book.CREATOR.createFromParcel(parcel);
//        Log.e("zkq","****反序列化后==" + createdFromParcel.getTitle());
//
//
//        Parcel parcelBean = Parcel.obtain();
//        TestBean bean = new TestBean(parcelBean);
//        bean.mPosition = 19;
//        bean.writeToParcel(parcelBean,bean.describeContents());
//        parcelBean.setDataPosition(0);
//        TestBean now = TestBean.CREATOR.createFromParcel(parcelBean);
//        Log.e("zkq","**** now === " + now.mPosition);
//
//        Parcel parcelBean1 = Parcel.obtain();
//        TestBean1 bean1 = new TestBean1(parcelBean1,null);
//        bean1.mPosition = 22;
//        bean1.writeToParcel(parcelBean1,bean1.describeContents());
//        parcelBean1.setDataPosition(0);
//        TestBean1 now1 = TestBean1.CREATOR.createFromParcel(parcelBean1);
//        Log.e("zkq","**** now1 === " + now1.mPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
