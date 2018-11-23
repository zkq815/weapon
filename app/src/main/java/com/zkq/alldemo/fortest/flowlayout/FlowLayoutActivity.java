package com.zkq.alldemo.fortest.flowlayout;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityFlowLayoutBinding;
import com.zkq.weapon.base.BaseActivity;

/**
 * @author zkq
 * create:2018/11/16 10:04 AM
 * email:zkq815@126.com
 * desc:
 */
public class FlowLayoutActivity extends BaseActivity {
    private ActivityFlowLayoutBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_flow_layout);

        for (int i = 0; i < 30; i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.main_item_viewholder,mBinding.flowlayout,false);
            textView.setText("eh"+i);
            textView.setBackgroundColor(getResources().getColor(R.color.red_alpha_0));
            textView.setId(i);
            mBinding.flowlayout.addView(textView,i);
        }


        for (int i = 0; i < 30; i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.main_item_viewholder,mBinding.weaponfl,false);
            textView.setText("eh"+i);
            textView.setBackgroundColor(getResources().getColor(R.color.red_alpha_0));
            textView.setId(i);
            mBinding.weaponfl.addView(textView,i);

        }
    }
}
