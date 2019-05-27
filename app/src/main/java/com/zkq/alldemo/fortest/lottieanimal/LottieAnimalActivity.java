package com.zkq.alldemo.fortest.lottieanimal;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityLottieAnimalBinding;
import com.zkq.weapon.base.BaseActivity;

/**
 * @author zkq
 * create:2019/5/28 12:23 AM
 * email:zkq815@126.com
 * desc:
 */
public class LottieAnimalActivity extends BaseActivity {
    private ActivityLottieAnimalBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_lottie_animal);
    }
}
