package com.zkq.alldemo.fortest.countdown.demo1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.zkq.alldemo.R;
import com.zkq.alldemo.fortest.countdown.CountdownBean;
import com.zkq.alldemo.fortest.countdown.OnTimerListener;
import com.zkq.weapon.market.tools.ToolString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zkq
 * create:2018/12/25 10:01 AM
 * email:zkq815@126.com
 * desc: 倒计时组件
 */
public class ZkqCountDownTimer extends CountDownTimer {

    /**
     * 默认的时间间隔
     */
    private static long INTERVAL = 30;
    /**
     * 唯一标识
     */
    private String id;
    private Context mContext;
    private TextView mTv;
    /**
     * 初始化的时间
     */
    private long nowTime;
    /**
     * 传入设置的时间间隔即倒计时的总时长
     */
    private long mCountdownTime;
    /**
     * 剩余的倒计时
     */
    private long leftTime;
    /**
     * 处理后的时间字符串
     */
    private String mTimeStr;
    /**
     * 数字背景span
     */
    private List<ZkqSpan> mBgSpanList;
    /**
     * 时间间隔span
     */
    private List<ForegroundColorSpan> mConnectColorSpanList;
//    protected List<ZkqSpan> mConnectColorSpanList;
    /**
     * 默认数字背景资源
     */
    private int mDrawableId = R.drawable.timer_shape;

    /**
     * 默认数字背景色
     */
    private int defaultBgColor;

    /**
     * 数字显示的颜色
     * */
    private int mTimerTextColor;
    /**
     * 默认分割符颜色
     */
    private int mSplitColor;

    /**
     * 设置标记flag,用于控制使得初始化Span的数据一次
     */
    private boolean flag = false;

    /**
     * 暂停的标识
     */
    private boolean isPause = false;

    private SpannableString mSpan;
    /**
     * 此数组用于保存每个倒计时字符拆分后的天,时,分,秒的数值
     */
    private String[] numbers;
    /**
     * 保存了天,时,分,秒之间的间隔("天","时","分","秒"或者":")
     */
    private char[] nonNumbers;
    /**
     * 用于倒计时样式的内间距
     */
    private int defaultPadding = 10;

    private int mSpanPaddingLeft, mSpanPaddingRight, mSpanPaddingTop, mSpanPaddingBottom;
    private int mSpanTextSize = 40;
    private CountdownBean mBean;
    private Map<String, String> map;
    private OnTimerListener mListener;

    public ZkqCountDownTimer(TextView textView, long countdownTime
            , CountdownBean bean, OnTimerListener listener) {
        super(countdownTime, bean.getInterval()==0 ? INTERVAL : bean.getInterval());
        this.mTv = textView;
        mListener = listener;
        this.id = String.valueOf(mTv.getId());
        this.mContext = textView.getContext();
        this.mCountdownTime = countdownTime;
        this.mBean = bean;
        this.defaultBgColor = ToolString.isEmpty(bean.getBgColor())
                ? Color.BLACK : Color.parseColor(bean.getBgColor());
        this.mTimerTextColor = ToolString.isEmpty(bean.getTextColor())
                ? Color.WHITE : Color.parseColor(bean.getTextColor());
        this.mSplitColor = ToolString.isEmpty(bean.getSplitColor())
                ? Color.BLACK : Color.parseColor(bean.getSplitColor());
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        map = new HashMap<>(6);
        mBgSpanList = new ArrayList<>();
        mConnectColorSpanList = new ArrayList<>();
    }

    /**
     * 倒计时回调方法
     * @param time
     */
    @Override
    public void onTick(long time) {
        if (time >= 0 &&  null != mTv) {
            map = TimerUtils.getTimeMap(time);
            mTimeStr = TimerUtils.getShowTime(time, mBean,map);
            setBackgroundSpan(mTimeStr);
            mListener.onEveryTime(time, getId());
            leftTime = time - (System.currentTimeMillis() - nowTime);
        }
    }

    /**
     * 倒计时结束
     */
    @Override
    public void onFinish() {
        if(null != mTv){
            setBackgroundSpan(TimerUtils.getEndShowTime(mBean));
            mListener.onTimeOver(getId());
            destory();
        }
    }

    /**
     * 开始倒计时
     */
    public void startTimer() {
        nowTime = System.currentTimeMillis();
        this.start();
        isPause = false;
    }

    /**
     * 暂停倒计时
     */
    public void pause(){
        isPause = true;
    }

    /**
     * 继续倒计时
     */
    public void continueTimer(){
        nowTime = System.currentTimeMillis();
        this.mCountdownTime = leftTime;
        this.start();
    }

    /**
     * 取消倒计时
     */
    public void cancelTimer() {
        this.cancel();
    }

    /**
     * 设置Span的样式
     *
     * @param timeStr 倒计时字符串
     */
    private void setBackgroundSpan(String timeStr) {
        if (!flag) {
            initSpanData(timeStr);
            flag = true;
        }
        int mGapLen = 1;
        mSpan = new SpannableString(timeStr);
        for (int i = 0; i < mBgSpanList.size(); i++) {
            int start = i * numbers[i].length() + i * mGapLen;
            int end = start + numbers[i].length();
            //设置数字的span
            TimerUtils.setContentSpan(mSpan, mBgSpanList.get(i), start, end);

            //防止数组越界
            if (i < mConnectColorSpanList.size()) {
                //设置间隔的span
                TimerUtils.setContentSpan(mSpan, mConnectColorSpanList.get(i), end, end + mGapLen);
            }
        }
        //此方法防止绘制出来的倒计时重叠
        mTv.setMovementMethod(LinkMovementMethod.getInstance());
        mTv.setText(mSpan);
    }

    /**
     * 通过number数组得到每块数值对应的自定义Span对象
     *
     * @param timeStr 倒计时字符串
     */
    private void initSpanData(String timeStr) {
        numbers = TimerUtils.getNumInTimerStr(timeStr);
        nonNumbers = TimerUtils.getNonNumInTimerStr(timeStr);

        for (int i = 0; i < numbers.length; i++) {
//            ZkqSpan mBackSpan = new ZkqSpan(mContext.getDrawable(mDrawableId), ImageSpan.ALIGN_BOTTOM);
            ZkqSpan mBackSpan = null;
            if(ToolString.isEmpty(mBean.getBgColor())){
                mBackSpan = new ZkqSpan(mContext.getDrawable(mDrawableId));
            }else{
                mBackSpan = new ZkqSpan(new ColorDrawable(defaultBgColor));
            }
            initTimerSpanStyle(mBackSpan);
            mBgSpanList.add(mBackSpan);
        }

        for (int i = 0; i < nonNumbers.length; i++) {
            ForegroundColorSpan mGapSpan = new ForegroundColorSpan(mSplitColor);
            mConnectColorSpanList.add(mGapSpan);
//            ZkqSpan mBackSpan = new ZkqSpan(new ColorDrawable(defaultBgColor), ImageSpan.ALIGN_BOTTOM);
//            initConnectSpanStyle(mBackSpan);
//            mConnectColorSpanList.add(mBackSpan);
        }
    }

    /**
     * 设置倒计时数字的背景
     *
     * @param mBackSpan span
     */
    private void initTimerSpanStyle(ZkqSpan mBackSpan) {
        mBackSpan.setTimerPadding(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
        mBackSpan.setTimerTextColor(mTimerTextColor);
        mBackSpan.setTimerTextSize(mSpanTextSize);
    }

    /**
     * 设置倒计时分隔符的背景
     *
     * @param mBackSpan span
     */
    public void initConnectSpanStyle(ZkqSpan mBackSpan) {
        mBackSpan.setTimerPadding(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
        mBackSpan.setTimerTextColor(mSplitColor);
        mBackSpan.setTimerTextSize(mSpanTextSize);
    }

    /**
     * 用于返回显示倒计时的TextView的对象
     *
     * @return 显示的textview
     */
    public TextView getmTv() {
        return mTv;
    }

    public String getmTimeStr() {
        return mTimeStr;
    }

    public void destory() {
        mContext = null;
        mBgSpanList = null;
        mConnectColorSpanList = null;
        mTv = null;
    }

    /**
     * 设置唯一标示 id
     *
     * @param id id
     */
    public ZkqCountDownTimer setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 获取唯一标识
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置文字大小
     *
     * @param textSize 文字大小
     * @return 倒计时对象
     */
    public ZkqCountDownTimer setTimerTextSize(int textSize) {
        this.mSpanTextSize = textSize;
        return this;
    }

    /**
     * 设置padding
     *
     * @param left   左侧
     * @param top    顶部
     * @param right  右侧
     * @param bottom 底部
     * @return 倒计时对象
     */
    public ZkqCountDownTimer setTimerPadding(int left, int top, int right, int bottom) {
        this.mSpanPaddingLeft = left;
        this.mSpanPaddingBottom = bottom;
        this.mSpanPaddingRight = right;
        this.mSpanPaddingTop = top;
        return this;
    }

    /**
     * 设置倒计时颜色
     *
     * @param color 颜色
     * @return 倒计时对象
     */
    public ZkqCountDownTimer setTimerTextColor(int color) {
        this.mTimerTextColor = color;
        return this;
    }

    /**
     * 设置连接字符的颜色
     * @param color 颜色
     * @return 倒计时对象
     */
    public ZkqCountDownTimer setTimeConnectColor(int color) {
        this.mSplitColor = color;
        return this;
    }

}
