package com.zkq.alldemo.fortest.subscreen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zkq.alldemo.R;
import com.zkq.weapon.base.BaseActivity;

/**
 * @author zkq
 * create:2019/5/23 11:56 PM
 * email:zkq815@126.com
 * desc: 截图测试
 */
public class SubScreenActivity extends BaseActivity {
    private Button btnSub;
    private ImageView imageViewOne,imageViewTwo;
    int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_screen);
        imageViewOne = findViewById(R.id.iv_content_one);
        imageViewTwo = findViewById(R.id.iv_content_two);
        btnSub = findViewById(R.id.btn_sub);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScreen();
            }
        });
    }

    private void getScreen(){

        getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap screenBitmap = getWindow().getDecorView().getDrawingCache();
        if(i==0){
            imageViewOne.setImageBitmap(screenBitmap);

        }else{

            imageViewTwo.setImageBitmap(screenBitmap);
        }
        i++;
    }
}
