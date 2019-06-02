package com.zkq.alldemo.fortest.scrollclash;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityScrollClashBinding;
import com.zkq.weapon.base.BaseActivity;

import java.util.ArrayList;

/**
 * @author zkq
 * create:2018/12/11 3:55 PM
 * email:zkq815@126.com
 * desc: 测试组合滑动
 */
public class ScrollClashActivity extends BaseActivity {
    private ActivityScrollClashBinding mBinding;
    private ListView mLV;
    private View mHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_scroll_clash);
        ArrayList<String> dataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dataList.add("content"+i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.main_item_viewholder,R.id.tv_one,dataList);

        mBinding.lv.setAdapter(adapter);
    }
}
