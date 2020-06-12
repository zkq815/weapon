package com.zkq.alldemo.fortest.scrollclash;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zkq.alldemo.R;
import com.zkq.weapon.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zkq
 * create:2018/12/11 3:55 PM
 * email:zkq815@126.com
 * desc: 测试组合滑动
 */
public class ScrollClashActivity extends BaseActivity {
    @BindView(R.id.lv)
    ListView mLV;
    @BindView(R.id.v_head)
    View mHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_clash);
        ButterKnife.bind(this);
        ArrayList<String> dataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dataList.add("content"+i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.simple_item_viewholder,R.id.tv_show,dataList);

        mLV.setAdapter(adapter);
    }
}
