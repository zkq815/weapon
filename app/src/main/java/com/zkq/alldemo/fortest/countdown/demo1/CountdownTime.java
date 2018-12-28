package com.zkq.alldemo.fortest.countdown.demo1;

/**
 * @author zkq
 * create:2018/12/19 10:26 AM
 * email:zkq815@126.com
 * desc:
 */
public class CountdownTime {
    private int hour;
    private int minute;
    private int second;
    private int seconds;
    private String id;
    private OnCountdownTimeListener listener;

    public void setListener(OnCountdownTimeListener listener) {
        this.listener = listener;
    }

    public CountdownTime(int time, String id, OnCountdownTimeListener listener) {
        this.seconds = time;
        this.id = id;
        this.listener = listener;
    }
    public String getId() {
        return id;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public boolean countdown(){
        synchronized (CountdownTime.class) {
            seconds -= 1;

            if (seconds <= 0) {
//            ZLog.e("倒计时结束id:"+id);
                listener.onTimeCountdownOver(id);
                listener.onCountdownTimeDraw(this);
                CountdownTimeQueueManager.getInstance().removeTime(this);
                return true;
            }

            listener.onCountdownTimeDraw(this);
            return false;
        }
    }

    /**
     * 处理时间显示
     * */
    public String getTimeText() {
        String text;
//        if(seconds >= 0){
//            if (seconds > 3600) {
//                hour = seconds / 3600;
//                if (seconds / 3600 > 0) {
//                    minute = seconds % 3600 / 60;
//                    second = seconds % 3600 % 60;
//                } else {
//                    minute = 0;
//                    seconds = seconds % 3600;
//                }
//            } else {
//                hour = 0;
//                minute = seconds / 60;
//                second = seconds % 60;
//            }
//        }else{
//            return "00:00:00:000";
//        }
//        if(hour == 0){
//            if(minute <10){
//                text = "0"+minute;
//                if(second < 10){
//                    text += ":0"+second;
//                }else{
//                    text += ":"+second;
//                }
//            }else{
//                text = ""+minute;
//                if(second < 10){
//                    text += ":0"+second;
//                }else{
//                    text += ":"+second;
//                }
//            }
//            return text;
//        }else{
//            //这边当倒计时大于一个小时时没做处理，可以自己改
//            return hour+":"+minute+":"+second;
//        }
//        if(seconds>0){
//
//            seconds--;
            return seconds+"";
//        }else{
//            return 0+"";
//        }

    }

    public interface OnCountdownTimeListener{
        void onCountdownTimeDraw(CountdownTime time);

        void onTimeCountdownOver(String id);
    }

    public interface OnTimeListener{
        /**
         * 每次刷新视图外部回调
         * */
        void onTick(int time);
        /**
         * 倒计时结束外部回调
         * */
        void onTimeOver(String id);
    }
}
