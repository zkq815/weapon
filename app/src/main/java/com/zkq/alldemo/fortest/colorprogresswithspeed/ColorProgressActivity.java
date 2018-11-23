package com.zkq.alldemo.fortest.colorprogresswithspeed;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zkq.alldemo.R;
import com.zkq.weapon.base.BaseActivity;
import com.zkq.weapon.customview.ColorArcProgressBar;
import com.zkq.weapon.customview.ColorProgressWithSpeed;

public class ColorProgressActivity extends BaseActivity {

    private ColorArcProgressBar progressBar,progressBar2,progressBar3,progressBar4;
    private ColorProgressWithSpeed myColorProgress;
    private Button btnStart,btnStart2,btnStart3,btnStart4,btnStart5,btnRestart,btnRestart2,btnRestart3,btnRestart4,btnRestart5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_progress);

        progressBar = (ColorArcProgressBar) findViewById(R.id.bar1);
        progressBar2 = (ColorArcProgressBar) findViewById(R.id.bar2);
        progressBar3 = (ColorArcProgressBar) findViewById(R.id.bar3);
        progressBar4 = (ColorArcProgressBar) findViewById(R.id.bar4);
        myColorProgress = (ColorProgressWithSpeed) findViewById(R.id.mycolor);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart2 = (Button) findViewById(R.id.btn_start2);
        btnStart3 = (Button) findViewById(R.id.btn_start3);
        btnStart4 = (Button) findViewById(R.id.btn_start4);
        btnStart5 = (Button) findViewById(R.id.btn_start5);
        btnRestart = (Button) findViewById(R.id.btn_restart);
        btnRestart2 = (Button) findViewById(R.id.btn_restart2);
        btnRestart3 = (Button) findViewById(R.id.btn_restart3);
        btnRestart4 = (Button) findViewById(R.id.btn_restart4);
        btnRestart5 = (Button) findViewById(R.id.btn_restart5);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setCurrentValues(100);
            }
        });

        btnStart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setCurrentValues(100);
            }
        });

        btnStart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar3.setCurrentValues(100);
            }
        });

        btnStart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar4.setCurrentValues(100);
            }
        });

        btnStart5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myColorProgress.setCurrentValues(100);
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setCurrentValues(0);
            }
        });

        btnRestart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setCurrentValues(0);
            }
        });

        btnRestart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar3.setCurrentValues(0);
            }
        });

        btnRestart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar4.setCurrentValues(0);
            }
        });

        btnRestart5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myColorProgress.setCurrentValues(0);
            }
        });
    }
}
