package com.zkq.alldemo.fortest.actionbar;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityActionBarDemoBinding;

public class MyToolbarActivity extends AppCompatActivity {
    private ActivityActionBarDemoBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_action_bar_demo);
        mBinding.toolbar.setOnTopbarClickListener(new TopbarClickLinstener() {
            @Override
            public void leftClick() {
                Toast.makeText(getBaseContext(), "左侧", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(getBaseContext(), "右侧", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
