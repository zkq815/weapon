package com.zkq.alldemo.fortest.flowlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zkq.alldemo.R;
import com.zkq.weapon.base.BaseActivity;
import com.zkq.weapon.customview.FlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zkq
 * create:2018/11/16 10:04 AM
 * email:zkq815@126.com
 * desc:
 */
public class FlowLayoutActivity extends BaseActivity {
    @BindView(R.id.flowlayout)
    FlowLayout flowlayout;

    @BindView(R.id.weaponfl)
    FlowLayout weaponfl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        ButterKnife.bind(this);

        for (int i = 0; i < 30; i++) {
            LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(this)
                    .inflate(R.layout.simple_item_viewholder,flowlayout,false);
            TextView textView = linearLayout.findViewById(R.id.tv_show);
            textView.setText("eh"+i);
            textView.setBackgroundColor(getResources().getColor(R.color.red_alpha_0));
            textView.setId(i);
            flowlayout.addView(textView,i);
        }


        for (int i = 0; i < 30; i++) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this)
                    .inflate(R.layout.simple_item_viewholder,weaponfl,false);
            TextView textView = linearLayout.findViewById(R.id.tv_show);
            textView.setText("eh"+i);
            textView.setBackgroundColor(getResources().getColor(R.color.red_alpha_0));
            textView.setId(i);
            weaponfl.addView(textView,i);

        }
    }
}
