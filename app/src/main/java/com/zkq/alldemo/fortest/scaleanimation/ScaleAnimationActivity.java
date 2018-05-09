package com.zkq.alldemo.fortest.scaleanimation;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityScaleAnimationBinding;

public class ScaleAnimationActivity extends AppCompatActivity {
    private ActivityScaleAnimationBinding mBinding;
    private ScaleAnimation sato0 = new ScaleAnimation(1,0,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);

    private ScaleAnimation sato1 = new ScaleAnimation(0,1,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_scale_animation);
        initView();
        mBinding.clAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.image1.getVisibility() == View.VISIBLE){
                    mBinding.image1.startAnimation(sato0);
                }else{
                    mBinding.image2.startAnimation(sato0);
                }
            }
        });
    }

    private void initView(){
        showImage1();
        sato0.setDuration(500);
        sato1.setDuration(500);

        sato0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (mBinding.image1.getVisibility() == View.VISIBLE){
                    mBinding.image1.setAnimation(null);
                    showImage2();
                    mBinding.image2.startAnimation(sato1);
                }else{
                    mBinding.image2.setAnimation(null);
                    showImage1();
                    mBinding.image1.startAnimation(sato1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void showImage1(){

        mBinding.image1.setVisibility(View.VISIBLE);
        mBinding.image2.setVisibility(View.GONE);
    }
    private void showImage2(){

        mBinding.image1.setVisibility(View.GONE);
        mBinding.image2.setVisibility(View.VISIBLE);
    }

}
