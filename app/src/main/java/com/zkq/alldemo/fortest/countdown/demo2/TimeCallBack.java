package com.zkq.alldemo.fortest.countdown.demo2;

/**
 * @author zkq
 * time: 2019/1/11:16:19
 * email: zkq815@126.com
 * desc:
 */
public interface TimeCallBack {
    void onTick(long timeLeft);

    void onFinish();
}
