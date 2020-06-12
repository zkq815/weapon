package com.zkq.alldemo.fortest.scaleanimation;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.zkq.alldemo.R;
import com.zkq.weapon.base.BaseActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ScaleAnimationActivity extends BaseActivity {

    @BindView(R.id.cl_all)
    ConstraintLayout clAll;

    @BindView(R.id.image1)
    ImageView image1;

    @BindView(R.id.image2)
    ImageView image2;

    private ScaleAnimation sato0 = new ScaleAnimation(1,0,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);

    private ScaleAnimation sato1 = new ScaleAnimation(0,1,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_animation);
        ButterKnife.bind(this);
        initView();
        clAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1.getVisibility() == View.VISIBLE){
                    image1.startAnimation(sato0);
                }else{
                    image2.startAnimation(sato0);
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

                if (image1.getVisibility() == View.VISIBLE){
                    image1.setAnimation(null);
                    showImage2();
                    image2.startAnimation(sato1);
                }else{
                    image2.setAnimation(null);
                    showImage1();
                    image1.startAnimation(sato1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void showImage1(){
        image1.setVisibility(View.VISIBLE);
        image2.setVisibility(View.GONE);
    }
    private void showImage2(){
        image1.setVisibility(View.GONE);
        image2.setVisibility(View.VISIBLE);
    }

}
