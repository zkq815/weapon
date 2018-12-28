package com.zkq.alldemo.fortest.countdown.demo2;

/**
 * @author:zkq
 * time: 2018/12/18:14:47
 * email: zkq815@126.com
 * desc:
 */
public class CountdownBean {

    /**
     * 是否显示年
     */
    private boolean showYear;

    /**
     * 是否显示月
     */
    private boolean showMonth;

    /**
     * 是否显示日
     */
    private boolean showDay;

    /**
     * 是否显示时
     */
    private boolean showHour;

    /**
     * 是否显示分
     */
    private boolean showMinutes;

    /**
     * 是否显示秒
     */
    private boolean showSecond;

    /**
     * 是否显示毫秒
     */
    private boolean showMillisecond;

    /**
     * 是否显示单位
     */
    private boolean showUnit;

    /**
     * 间隔时间
     */
    private int interval;

    /**
     * 连接符号或文字
     */
    private String connection;

    /**
     * 连接符号或文字颜色
     */
    private String connectColor;
    /**
     * 文字颜色
     * */
    private String textColor;

    /**
     * 文字大小
     * */
    private String textSize;

    /**
     * 背景图片url
     * */
    // TODO: 2018/12/26 quchu 
    private String bgPicUrl;

    /**
     * 背景颜色
     * */
    private String bgColor;

    private long times;

    public boolean isShowYear() {
        return showYear;
    }

    public void setShowYear(boolean showYear) {
        this.showYear = showYear;
    }

    public boolean isShowMonth() {
        return showMonth;
    }

    public void setShowMonth(boolean showMonth) {
        this.showMonth = showMonth;
    }

    public boolean isShowDay() {
        return showDay;
    }

    public void setShowDay(boolean showDay) {
        this.showDay = showDay;
    }

    public boolean isShowHour() {
        return showHour;
    }

    public void setShowHour(boolean showHour) {
        this.showHour = showHour;
    }

    public boolean isShowMinutes() {
        return showMinutes;
    }

    public void setShowMinutes(boolean showMinutes) {
        this.showMinutes = showMinutes;
    }

    public boolean isShowSecond() {
        return showSecond;
    }

    public void setShowSecond(boolean showSecond) {
        this.showSecond = showSecond;
    }

    public boolean isShowMillisecond() {
        return showMillisecond;
    }

    public void setShowMillisecond(boolean showMillisecond) {
        this.showMillisecond = showMillisecond;
    }

    public boolean isShowUnit() {
        return showUnit;
    }

    public void setShowUnit(boolean showUnit) {
        this.showUnit = showUnit;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getConnectColor() {
        return connectColor;
    }

    public void setConnectColor(String connectColor) {
        this.connectColor = connectColor;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    public String getBgPicUrl() {
        return bgPicUrl;
    }

    public void setBgPicUrl(String bgPicUrl) {
        this.bgPicUrl = bgPicUrl;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

}
