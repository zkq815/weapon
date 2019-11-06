package com.zkq.alldemo.fortest.okhttp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityOkhttpBinding;
import com.zkq.weapon.base.BaseActivity;
import com.zkq.weapon.market.util.ZLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author zkq
 * create:2018/12/11 3:54 PM
 * email:zkq815@126.com
 * desc: 测试OKhttp
 */
public class OKHttpActivity extends BaseActivity {
    private ActivityOkhttpBinding mBinding;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_okhttp);
        viewPager = findViewById(R.id.vp);
        mBinding.btnOkhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okGet();
            }
        });
        final List<TestFragment> fragments = new ArrayList<>();
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public TestFragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    void okGet(){
        OKHttpUtil.get("http://www.imooc.com", null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("zkq", "failure");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
//                Log.e("zkq", "response==" + response.body().string());
                final String temp = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                            tvResult.setText("result:\n"+temp);
                            ZLog.e("result:\n"+temp);
                    }
                });
            }
        });
    }
}
