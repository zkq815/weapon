package com.zkq.alldemo.fortest.countdown.demo2;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zkq
 * create:2018/12/25 10:59 AM
 * email:zkq815@126.com
 * desc:
 */
public class TimerUtils {
    static final String YEAR = "year";
    static final String MONTH = "month";
    static final String DAY = "day";
    static final String HOUR = "hour";
    static final String MINUTE = "minute";
    static final String SECOND = "second";
    static final String MILLISECOND = "millisecond";

    private static int TEN = 10;
    private static int HUNDRED = 100;
    private static String ZERO_END = "00";

    private static TimerUtils timerUtils;
    private static ArrayList<ZkqCountDownTimer> timerList;

    public static TimerUtils getInstance(){
        if(timerUtils == null){
            timerUtils = new TimerUtils();
            timerList = new ArrayList<>();
        }

        return timerUtils;
    }

    public ZkqCountDownTimer getTimerWithBean(TextView textView , long countdownTime
            , CountdownBean bean, OnTimerListener listener){
        ZkqCountDownTimer mCountDownTimer = new ZkqCountDownTimer(textView, countdownTime, bean, listener);
        timerList.add(mCountDownTimer);
        return mCountDownTimer;
    }

    public ArrayList<ZkqCountDownTimer> getTimerList() {
        return timerList;
    }

    public void setTimerList(ArrayList<ZkqCountDownTimer> timerList) {
        TimerUtils.timerList = timerList;
    }

    public void cancelById(String id){
        for (int i = 0; i < getTimerList().size(); i++) {
            ZkqCountDownTimer zkqCountDownTimer = getTimerList().get(i);
            if(id.equals(zkqCountDownTimer.getId())){
                zkqCountDownTimer.cancel();
                getTimerList().remove(i);
                break;
            }
        }
    }

    public void reStartCountDownById(String id){
        for (int i = 0; i < getTimerList().size(); i++) {
            ZkqCountDownTimer zkqCountDownTimer = getTimerList().get(i);
            if(id.equals(zkqCountDownTimer.getId())){
                zkqCountDownTimer.start();
                break;
            }
        }
    }

    public void continueCountDownById(String id){
        for (int i = 0; i < getTimerList().size(); i++) {
            ZkqCountDownTimer zkqCountDownTimer = getTimerList().get(i);
            if(id.equals(zkqCountDownTimer.getId())){
                zkqCountDownTimer.continueTimer();
                break;
            }
        }
    }

    /**
     * 获取倒计时字符串中的数值部分
     * @param mTimerStr 倒计时字符串
     * @return 数值数组
     */
    public static String[] getNumInTimerStr(String mTimerStr){
        return mTimerStr.split("[^\\d]");
    }

    /**
     * 得到倒计时中字符串中的非数值的字符串,并把数值过滤掉重新组合成一个字符串，
     * 并把字符串拆分字符数组，也就是保存倒计时中间的间隔
     * @param mTimerStr 倒计时字符串
     * @return 非数字的数组
     */
    public static char[] getNonNumInTimerStr(String mTimerStr){
        return mTimerStr.replaceAll("\\d","").toCharArray();
    }

    /**
     * 设置内容的Span
     * @param mSpan 目标span
     * @param span 子span
     * @param start 开始下标
     * @param end 结束下标
     */
    public static void setContentSpan(SpannableString mSpan, Object span, int start, int end) {
//        mSpan.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpan.setSpan(span, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }

    /**
     * 获取时间map，存放各个单位的时间
     *
     * @param ms 毫秒
     * @return 时间map
     */
    public static Map<String, String> getTimeMap(long ms) {
        if (ms >= 0) {
            Map<String, String> tempMap = new HashMap<>();
            int ss = 1000;
            int mi = ss * 60;
            int hh = mi * 60;
            int dd = hh * 24;
            int yy = dd * 365;

            long year = ms / yy;
            long day = (ms - year * yy) / dd;
            long hour = (ms - year * yy - day * dd) / hh;
            long minute = (ms - year * yy - day * dd - hour * hh) / mi;
            long second = (ms - year * yy - day * dd - hour * hh - minute * mi) / ss;
            long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

            String strMilliSecond = milliSecond <= TEN ? "0" + milliSecond : "" + milliSecond;
            strMilliSecond = milliSecond < HUNDRED ? "0" + strMilliSecond : "" + milliSecond;
            if (milliSecond < TEN) {
                strMilliSecond = ZERO_END + milliSecond;
            } else if (milliSecond >= TEN && milliSecond < HUNDRED) {
                strMilliSecond = "0" + milliSecond;
            }

            tempMap.put(MILLISECOND, strMilliSecond.substring(0, 2));
            tempMap.put(SECOND, second < TEN ? "0" + second : "" + second);
            tempMap.put(MINUTE, minute < TEN ? "0" + minute : "" + minute);
            tempMap.put(HOUR, hour < TEN ? "0" + hour : "" + hour);
            tempMap.put(DAY, day < TEN ? "0" + day : "" + day);
            tempMap.put(YEAR, year < TEN ? "0" + year : "" + year);

            return tempMap;
        }
        return null;
    }

    /**
     * 获取展示的时间
     *
     * @param ms   毫秒
     * @param bean 参数对象
     * @return 获取展示的倒计时
     */
    public static String getShowTime(long ms, CountdownBean bean, Map<String,String> map) {

        StringBuilder showTime = new StringBuilder();
        if (ms >= 0 && bean != null) {
            String conn = TextUtils.isEmpty(bean.getConnection()) ? ":" : bean.getConnection();
            if (bean.isShowYear()) {
                showTime.append(map.get(YEAR));
            }

            if (bean.isShowDay()) {
                if (!TextUtils.isEmpty(showTime.toString())) {
                    if(bean.isShowUnit()){
                        showTime.append("年").append(map.get(DAY));
                    }else{

                        showTime.append(conn).append(map.get(DAY));
                    }
                } else {
                    showTime.append(map.get(DAY));
                }
            }

            if (bean.isShowHour()) {
                if (!TextUtils.isEmpty(showTime.toString())) {
                    if(bean.isShowUnit()){
                        showTime.append("日").append(map.get(HOUR));
                    }else{

                        showTime.append(conn).append(map.get(HOUR));
                    }
                } else {
                    showTime.append(map.get(HOUR));
                }
            }

            if (bean.isShowMinutes()) {
                if (!TextUtils.isEmpty(showTime.toString())) {
                    if(bean.isShowUnit()){
                        showTime.append("时").append(map.get(MINUTE));
                    }else{

                        showTime.append(conn).append(map.get(MINUTE));
                    }
                } else {
                    showTime.append(map.get(MINUTE));
                }
            }

            if (bean.isShowSecond()) {
                if (!TextUtils.isEmpty(showTime.toString())) {
                    if(bean.isShowUnit()){
                        showTime.append("分").append(map.get(SECOND));
                    }else{

                        showTime.append(conn).append(map.get(SECOND));
                    }
                } else {
                    showTime.append(map.get(SECOND));
                }
            }

            if (bean.isShowMillisecond()) {
                if (!TextUtils.isEmpty(showTime.toString())) {
                    if(bean.isShowUnit()){
                        showTime.append("秒").append(map.get(MILLISECOND));
                    }else{

                        showTime.append(conn).append(map.get(MILLISECOND));
                    }
                } else {
                    showTime.append(map.get(MILLISECOND));
                }
            }
        }

        return showTime.toString();
    }

    /**
     * 获取展示的时间
     *
     * @return 结束时显示的倒计时
     */
    public static String getEndShowTime(CountdownBean mBean) {
        StringBuilder endTime = new StringBuilder();
        String conn = TextUtils.isEmpty(mBean.getConnection()) ? ":" : mBean.getConnection();
        if (mBean.isShowYear()) {
            endTime.append("00");
        }

        if (mBean.isShowDay()) {
            if (!TextUtils.isEmpty(endTime.toString())) {
                if(mBean.isShowUnit()){
                    endTime.append("年").append("00");
                }else{
                    endTime.append(conn).append("00");
                }
            } else {
                endTime.append("00");
            }
        }

        if (mBean.isShowHour()) {
            if (!TextUtils.isEmpty(endTime.toString())) {
                if(mBean.isShowUnit()){
                    endTime.append("日").append("00");
                }else{
                    endTime.append(conn).append("00");
                }
            } else {
                endTime.append("00");
            }
        }

        if (mBean.isShowMinutes()) {
            if (!TextUtils.isEmpty(endTime.toString())) {
                if(mBean.isShowUnit()){
                    endTime.append("时").append("00");
                }else{
                    endTime.append(conn).append("00");
                }
            } else {
                endTime.append("00");
            }
        }

        if (mBean.isShowSecond()) {
            if (!TextUtils.isEmpty(endTime.toString())) {
                if(mBean.isShowUnit()){
                    endTime.append("分").append("00");
                }else{
                    endTime.append(conn).append("00");
                }
            } else {
                endTime.append("00");
            }
        }

        if (mBean.isShowMillisecond()) {
            if (!TextUtils.isEmpty(endTime.toString())) {
                if(mBean.isShowUnit()){
                    endTime.append("秒").append("00");
                }else{
                    endTime.append(conn).append("00");
                }
            } else {
                endTime.append("00");
            }
        }

        return endTime.toString();
    }

}
