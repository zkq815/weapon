package com.zkq.alldemo.fortest.countdown.demo1;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zkq
 * create:2018/12/20 10:23 AM
 * email:zkq815@126.com
 * desc: 倒计时列队管理
 */
public class CountdownTimeQueueManager{
    private static CountdownTimeQueueManager manager;
    private ArrayList<CountdownTime> timeQueue;
    private Timer timeTimer;
    private TimerTask timeTask;

    public static CountdownTimeQueueManager getInstance(){
        if (manager == null){
            manager = new CountdownTimeQueueManager();
            manager.initCountdownTimeQueueManager();
        }
        return manager;
    }

    /**
     * 初始化Manager
     * */
    void initCountdownTimeQueueManager() {
        timeQueue = new ArrayList<>();
        timeTimer = new Timer(true);
        timeTask = new TimerTask() {
            @Override
            public void run() {
                countdownTimeQueue();
            }
        };
        timeTimer.schedule(timeTask,0,30);
    }

    /**
     * 添加倒计时,先从列队中查询
     * */
    public CountdownTime addTime(int time, String id, CountdownTime.OnCountdownTimeListener listener) {
        CountdownTime countdownTime = null;
        if(timeQueue !=null && !timeQueue.isEmpty()) {
            for (int i = 0; i < timeQueue.size(); i++) {
                countdownTime = timeQueue.get(i);
                if (TextUtils.equals(countdownTime.getId(), id)) {
                    countdownTime.setListener(listener);
                    break;
                }
            }
        }else{
            if(timeQueue == null){
                timeQueue = new ArrayList<>();
            }
        }
        countdownTime = new CountdownTime(time,id,listener);
        timeQueue.add(countdownTime);
        return countdownTime;
    }

    synchronized void countdownTimeQueue() {
        if(timeQueue != null && !timeQueue.isEmpty()){
            for(int i = 0 ;i < timeQueue.size(); i++){
                if(timeQueue.get(i).countdown()) {
                    i --;
                }
            }
        }
    }

    /**
     * 移除一个倒计时
     * */
    public void removeTime(CountdownTime time) {
        if (timeQueue != null) {
            for (int i = 0; i < timeQueue.size(); i++) {
                if (TextUtils.equals(time.getId(), timeQueue.get(i).getId())) {
                    timeQueue.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * 移除一个销毁页面中的倒计时(页面销毁时调用)
     * */
    public void removePageTime(String pageName){
        if (timeQueue != null) {
            for (int i = 0; i < timeQueue.size(); i++) {
                if (timeQueue.get(i).getId().contains(pageName)) {
                    timeQueue.remove(i);
                }
            }
        }
    }

    /**
     * 移除所有的倒计时(关闭应用时调用)
     * */
    public void removeAllTime() {
        timeTask.cancel();
        if (timeQueue != null) {
            for (int i = 0; i < timeQueue.size(); i++) {
                timeQueue.remove(i);
            }
            timeTask = null;
            timeTimer.cancel();
            manager = null;
        }
    }

    public ArrayList<CountdownTime> getTimeQueue() {
        return timeQueue;
    }
}
