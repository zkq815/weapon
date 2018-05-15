package com.zkq.alldemo.fortest.scrollclash;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityScrollClashBinding;

import java.util.ArrayList;

public class ScrollClashActivity extends AppCompatActivity {
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.main_item_viewholder,R.id.tv_activity,dataList);

        mBinding.lv.setAdapter(adapter);



    }
}
