package com.zkq.alldemo.fortest.actionbar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityActionBarDemoBinding;
import com.zkq.weapon.base.BaseActivity;
import com.zkq.weapon.customview.RadioButtonTimeTab;
/**
 * @author zkq
 * create:2018/12/11 3:54 PM
 * email:zkq815@126.com
 * desc: 测试ToolBar
 */
public class MyToolbarActivity extends BaseActivity {
    private ActivityActionBarDemoBinding mBinding;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_action_bar_demo);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
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
        initRadioButton();
    }


    private void initRadioButton(){

        radioGroup.removeAllViews();
        Point point = new Point();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        for (int i = 0; i < 4; i++) {
            RadioButtonTimeTab timeTab = new RadioButtonTimeTab(this, i == 0);
            timeTab.setData("time", "data");
            int systemWidth = 1000;
            int width = systemWidth / 4;
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(width, 120);
            timeTab.setLayoutParams(params);
            radioGroup.addView(timeTab);
        }
        ((RadioButtonTimeTab) radioGroup.getChildAt(0)).setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            }
        });

    }


}
