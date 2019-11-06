package com.zkq.alldemo.fortest.gestureviewbinder;

import android.os.Bundle;
import android.widget.TextView;

import com.jarvislau.destureviewbinder.GestureViewBinder;
import com.zkq.alldemo.R;
import com.zkq.weapon.base.BaseActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GestureViewBinderActivity extends BaseActivity {

    @BindView(R.id.ctl)
    ConstraintLayout constraintLayout;
    @BindView(R.id.tv_test)
    TextView tvTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_view_binder);
        ButterKnife.bind(this);
        GestureViewBinder binder = GestureViewBinder.bind(this, constraintLayout, tvTest);
        binder.setFullGroup(true);
    }
}
