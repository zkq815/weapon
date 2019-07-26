package com.zkq.alldemo.fortest.gsvideo.gsdemo.gsyvideoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.zkq.alldemo.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmptyActivity extends AppCompatActivity {

    @BindView(R.id.jump_other)
    Button jumpOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.jump_other)
    public void onViewClicked() {
        startActivity(new Intent(this, EmptyActivity.class));
    }
}
