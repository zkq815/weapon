package com.zkq.alldemo.fortest.countdown.demo2;

import android.os.CountDownTimer;


/**
 * @author zkq
 * time: 2019/1/7:14:23
 * email: zkq815@126.com
 * desc:
 */
public class CustonCountdowmTimer extends CountDownTimer {
    private CustomCountDown.TimeCallBack mListener;

    public CustonCountdowmTimer(long millisInFuture, long countDownInterval, CustomCountDown.TimeCallBack listener) {
        super(millisInFuture, countDownInterval);
        this.mListener = listener;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mListener.onTick(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        mListener.onFinish();
    }
}
