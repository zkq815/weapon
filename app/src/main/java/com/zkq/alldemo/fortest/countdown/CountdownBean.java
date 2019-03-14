package com.zkq.alldemo.fortest.countdown;

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
     * 背景是否填充
     */
    private boolean isFill;

    /**
     * 边框宽度
     */
    private int strokeWidth;

    /**
     * 间隔时间
     */
    private int interval;

    /**
     * 背景颜色
     * */
    private String bgColor;

    /**
     * 背景颜色
     */
    private int bgColorCode;

    /**
     * 背景边框宽度
     */
    private int bgStrokeWidth;

    /**
     * 文字颜色
     * */
    private String textColor;

    /**
     * 文字颜色
     */
    private int textColorCode;

    /**
     * 文字大小
     * */
    private int textSize;

    /**
     * 连接符号或文字
     */
    private String split;

    /**
     * 连接符文字颜色
     */
    private String splitColor;

    /**
     * 连接符文字颜色
     */
    private int splitColorCode;

    /**
     * 圆角弧度
     */
    private int rad;

    /**
     * 总的倒计时
     */
    private long times;

    /**
     * 文字与背景边框的内边距
     */
    private int innerPadding;

    /**
     * 分隔符与背景边框的外边距
     */
    private int outPadding;

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

    public boolean isFill() {
        return isFill;
    }

    public void setFill(boolean fill) {
        isFill = fill;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public String getSplitColor() {
        return splitColor;
    }

    public void setSplitColor(String splitColor) {
        this.splitColor = splitColor;
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

    public int getBgColorCode() {
        return bgColorCode;
    }

    public void setBgColorCode(int bgColorCode) {
        this.bgColorCode = bgColorCode;
    }

    public int getTextColorCode() {
        return textColorCode;
    }

    public void setTextColorCode(int textColorCode) {
        this.textColorCode = textColorCode;
    }

    public int getSplitColorCode() {
        return splitColorCode;
    }

    public void setSplitColorCode(int splitColorCode) {
        this.splitColorCode = splitColorCode;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
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

    public int getInnerPadding() {
        return innerPadding;
    }

    public void setInnerPadding(int innerPadding) {
        this.innerPadding = innerPadding;
    }

    public int getOutPadding() {
        return outPadding;
    }

    public void setOutPadding(int outPadding) {
        this.outPadding = outPadding;
    }

    public int getRad() {
        return rad;
    }

    public void setRad(int rad) {
        this.rad = rad;
    }

    public int getBgStrokeWidth() {
        return bgStrokeWidth;
    }

    public void setBgStrokeWidth(int bgStrokeWidth) {
        this.bgStrokeWidth = bgStrokeWidth;
    }
}
