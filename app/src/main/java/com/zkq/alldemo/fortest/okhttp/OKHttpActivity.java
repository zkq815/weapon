package com.zkq.alldemo.fortest.okhttp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityOkhttpBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OKHttpActivity extends AppCompatActivity {
    private ActivityOkhttpBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_okhttp);
        mBinding.btnOkhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okGet();
            }
        });
    }

    void okGet(){
        OKHttpUtil.get("http://www.imooc.com", null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("zkq", "failure");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
//                Log.e("zkq", "response==" + response.body().string());
                final String temp = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                            tvResult.setText("result:\n"+temp);
                    }
                });
            }
        });
    }
}
