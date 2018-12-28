package com.zkq.alldemo.fortest.countdown.demo2;

/**
 * @author zkq
 * time: 2018/12/26:01:18
 * email: zkq815@126.com
 * desc:
 */
public interface OnTimerListener {
    /**
     * 每次刷新视图外部回调
     * @param time 毫秒
     */
    void onEveryTime(long time, String id);

    /**
     * 倒计时结束外部回调
     * @param id 唯一标识
     */
    void onTimeOver(String id);

}
