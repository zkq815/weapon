package com.zkq.alldemo.fortest.countdown.demo2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zkq.alldemo.fortest.countdown.CountdownBean;
import com.zkq.alldemo.fortest.countdown.OnTimerListener;
import com.zkq.alldemo.fortest.countdown.demo1.TimerUtils;
import com.zkq.weapon.market.tools.ToolRegex;
import com.zkq.weapon.market.tools.ToolString;
import com.zkq.weapon.market.util.ZLog;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author zkq
 * time: 2019/1/4:11:27
 * email: zkq815@126.com
 * desc:
 * <p>
 * 1.基准点是baseline
 * 2.ascent：是baseline之上至字符最高处的距离
 * 3.descent：是baseline之下至字符最低处的距离
 * 4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
 * 5.top：是指的是最高字符到baseline的值,即ascent的最大值
 * 6.bottom：是指最低字符到baseline的值,即descent的最大值
 */
public class CustomCountDown extends View {
    /**
     * 默认的文字颜色
     */
    private static final String DEFAULT_TEXT_COLOR = "#000000";
    /**
     * 默认的文字大小
     */
    private static final int DEFAULT_TEXT_SIZE = 40;
    /**
     * 默认的背景颜色
     */
    private static final String DEFAULT_BG_COLOR = "#ffffff";
    /**
     * 默认的分隔符颜色
     */
    private static final String DEFAULT_SPLIT_COLOR = "#000000";
    /**
     * 默认的分隔符
     */
    private static final String DEFAULT_SPLIT_TEXT = ":";
    /**
     * 默认的内部间隔，文字与背景边框之间间隔
     */
    private static final int DEFAULT_INNER_PADDING = DEFAULT_TEXT_SIZE / 5;
    /**
     * 默认的外部间隔，分隔符与背景边框之间的间隔
     */
    private static final int DEFAULT_OUTSIDE_PADDING = DEFAULT_TEXT_SIZE / 5;

    /**
     * 默认的时间间隔
     */
    private static final int DEFAULT_INTERVAL = 43;

    private Context mContext;
    /**
     * 背景画笔
     */
    private Paint mPaintStroke;
    /**
     * 文字画笔
     */
    private Paint mPaintText;
    /**
     * 分隔符画笔
     */
    private Paint mPaintSplit;
    /**
     * 文字与背景边框之间的间隔
     */
    private int innerPadding = 10;
    /**
     * 分隔符与文字背景边框之间的间隔
     */
    private int outsidePadding = 10;

    /**
     * 数字部分文字框
     */
    private Rect mRectNumber;

    /**
     * 连接符文字框
     */
    private Rect mRectSplit;

    /**
     * 背景颜色
     */
    private String mBgColor;

    /**
     * 背景的弧度
     */
    private int rad;

    /**
     * 文字颜色
     */
    private String mTextColor;

    /**
     * 文字大小
     */
    private int mTextSize;
    /**
     * 分隔符颜色
     */
    private String mSplitColor;
    /**
     * 倒计时总时长
     */
    private long times;
    /**
     * 时间存储map，以年、日、时、分、秒、毫秒为key进行存储
     */
    private Map<String, String> timeMap;
    /**
     * 间隔时间
     */
    private int interval;
    /**
     * 唯一标示key
     */
    private String key = "";
    /**
     * 展示出来的倒计时
     */
    private String showTime = "19:00:01:12";
    /**
     * 倒计时单位列表，根据显示的内容存放单位
     */
    private ArrayList<String> unitArray;
    /**
     * 分隔符
     */
    private String split = "";
    /**
     * 配置文件bean
     */
    private CountdownBean mBean;
    /**
     * 官方倒计时
     */
    private CustonCountdowmTimer mTimer;
    /**
     * 外部监听回调
     */
    private OnTimerListener onTimerListener;

    /**
     * 开始倒计时的时间，用于暂停恢复
     */
    private long startTime;
    /**
     * 剩余倒计时时间，用于重新开始倒计时
     */
    private long leftTime;

    /**
     * 倒计时是否在进行
     */
    private boolean isGoing;

    /**
     * 倒计时是否已启动
     */
    private boolean isStart;

    public CustomCountDown(Context context) {
        this(context, null);
    }

    public CustomCountDown(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCountDown(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mPaintStroke = new Paint();
        mPaintText = new Paint();
        mPaintSplit = new Paint();
        mRectNumber = new Rect();
        mRectSplit = new Rect();
        unitArray = new ArrayList<>(3);
        setPaintStroke();
        setPaintText();
        setPaintSplit();
    }

    /**
     * 内部倒计时回调
     */
    private TimeCallBack mListener = new TimeCallBack() {
        @Override
        public void onTick(long timeLeft) {
            leftTime = timeLeft;
            timeMap = TimerUtils.getTimeMap(timeLeft);
            showTime = TimerUtils.getTimerStr(TimerUtils.getShowTime(timeLeft, mBean, timeMap)
                    , DEFAULT_SPLIT_TEXT);
            setText(showTime);
            onTimerListener.onEveryTime(timeLeft, key);
        }

        @Override
        public void onFinish() {
            showTime = TimerUtils.getTimerStr(TimerUtils.getEndShowTime(mBean), DEFAULT_SPLIT_TEXT);
            setText(showTime);
            isGoing = false;
            onTimerListener.onTimeOver(key);
        }
    };

    /**
     * 开启倒计时
     */
    public void start() {
        if (null != mTimer) {
            startTime = System.currentTimeMillis();
            mTimer.start();
            isGoing = true;
            isStart = true;
        }
    }

    /**
     * 暂停倒计时
     */
    public void pause() {
        startTime = System.currentTimeMillis();
        cancel();
    }

    /**
     * 继续倒计时（暂停后调用）
     */
    public void goon() {
        if (!isGoing) {
            long pauseTime = System.currentTimeMillis() - startTime;
            if (leftTime > pauseTime) {
                mTimer = new CustonCountdowmTimer(leftTime - pauseTime, interval, mListener);
                mTimer.start();
                startTime = System.currentTimeMillis();
                isGoing = true;
            } else {
                if (null != mBean) {
                    setText(TimerUtils.getEndShowTime(mBean));
                    isGoing = false;
                }
            }
        }
    }

    /**
     * 取消倒计时
     */
    public void cancel() {
        if (null != mTimer) {
            mTimer.cancel();
        }
        isGoing = false;
    }

    /**
     * 重新启动倒计时
     */
    public void restart() {
        if (!isGoing) {
            mTimer = new CustonCountdowmTimer(times, interval, mListener);
            startTime = System.currentTimeMillis();
            mTimer.start();
            isGoing = true;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 测量宽度
     */
    private int measureWidth(int defaultWidth, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                int size = showTime.split(DEFAULT_SPLIT_TEXT).length;
                if (mBean.isShowUnit()) {
                    split = "年";
                } else {
                    split = mBean.getSplit();
                }
                //文字长度 + 两端padding + 文字内边距*2*显示文字区域的个数 + 分割区域的个数 *（2*padding + 分隔符的宽度）

                defaultWidth = (int) mPaintText.measureText(showTime)
                        + getPaddingLeft() + getPaddingRight()
                        + 2 * innerPadding * size
                        + (2 * outsidePadding + (int) mPaintSplit.measureText(split)) * (size - 1);
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
                break;
            default:
                break;
        }
        return defaultWidth;
    }

    /**
     * 测量高度
     */
    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            //最大值模式
            case MeasureSpec.AT_MOST:
                defaultHeight = (int) (-mPaintText.ascent() + mPaintText.descent())
                        + getPaddingTop() + getPaddingBottom()
                        + 2 * innerPadding;
                break;
            //精准模式
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            //自定义
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = (int) (-mPaintText.ascent() + mPaintText.descent())
                        + getPaddingTop() + getPaddingBottom()
                        + 2 * innerPadding;
                break;
            default:
                break;
        }
        return defaultHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String[] str = TimerUtils.getNumInTimerStr(showTime);
        int left = 0;
        for (int i = 0; i < str.length; i++) {
            //绘制背景框
            canvas.drawRoundRect(left, 0, left + getHeight(), getHeight(), rad, rad, mPaintStroke);
            //绘制文字，居中显示
            canvas.drawText(str[i]
                    , left + innerPadding
                    , -mPaintText.ascent() + innerPadding
                    , mPaintText);

            //准备绘制下一个内容的起始位置偏移量
            left += getHeight() + outsidePadding;

            //绘制连接符
            if (i != str.length - 1) {
                //判断是否显示时间单位，当前的索引要小于存储单位的数据列表长度
                if (mBean.isShowUnit() && i < unitArray.size()) {
                    if (i < unitArray.size()) {
                        split = unitArray.get(i);
                    }
                } else {
                    split = ToolString.isEmptyOrNull(mBean.getSplit()) ? ":" : mBean.getSplit();
                }

                canvas.drawText(split, left, -mPaintText.ascent() + innerPadding, mPaintSplit);
                //准备绘制下一个内容的起始位置偏移量
                left += mPaintSplit.measureText(split) + outsidePadding;
            }
        }
    }

    /**
     * 初始化背景画笔
     */
    private void setPaintStroke() {
        mPaintStroke.setTextAlign(Paint.Align.LEFT);
        mPaintStroke.setColor(Color.RED);
        mPaintStroke.setAntiAlias(true);
        mPaintStroke.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     * 初始化文字画笔
     */
    private void setPaintText() {
        mPaintText.setTextAlign(Paint.Align.LEFT);
        mPaintText.setColor(Color.BLUE);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(50);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.getTextBounds(showTime, 0, showTime.length(), mRectNumber);
    }

    /**
     * 初始化分隔符画笔
     */
    private void setPaintSplit() {
        mPaintSplit.setTextAlign(Paint.Align.LEFT);
        mPaintSplit.setColor(Color.BLUE);
        mPaintSplit.setAntiAlias(true);
        mPaintSplit.setTextSize(50);
        mPaintSplit.setStyle(Paint.Style.STROKE);
        mPaintSplit.getTextBounds(split, 0, split.length(), mRectSplit);
    }

    /**
     * 设置显示文字，重新绘制
     *
     * @param text 显示内容
     */
    private void setText(String text) {
        this.showTime = text;
        invalidate();
    }

    /**
     * 设置配置bean
     *
     * @param bean 配置bean
     * @return 当前对象
     */
    public CustomCountDown setCountdownBean(@Nullable CountdownBean bean) {
        this.mBean = bean;
        resetParams(mBean);
        return this;
    }

    /**
     * 根据配置项重置配置
     *
     * @param bean 配置bean
     */
    private void resetParams(CountdownBean bean) {
        if (null != bean) {
//            LinearGradient linearGradient = new LinearGradient(0,0,getHeight(),getHeight()
//                    ,new int[]{0xff00ffff,0xff000000}
//                    ,new float[]{0f,1.0f}
//                    , Shader.TileMode.CLAMP);
//            mPaintStroke.setShader(linearGradient);
            //背景色重置
            mPaintStroke.setColor(
                    Color.parseColor(ToolString.isEmptyOrNull(bean.getBgColor()) ? DEFAULT_BG_COLOR : bean.getBgColor()));
            //背景色style
            mPaintStroke.setStyle(bean.isFill() ? Paint.Style.FILL : Paint.Style.STROKE);
            if (bean.isFill()) {
                mPaintStroke.setStyle(Paint.Style.FILL);
            } else {
                mPaintStroke.setStyle(Paint.Style.STROKE);
                mPaintStroke.setStrokeWidth(bean.getStrokeWidth() == 0 ? 2 : bean.getStrokeWidth());
            }

            rad = bean.getRad();
            mTextSize = Integer.parseInt(ToolString.isEmptyOrNull(bean.getTextSize())
                    ? String.valueOf(DEFAULT_TEXT_SIZE) : bean.getTextSize());

            //文字与背景边框之间的距离
            innerPadding = bean.getInnerPadding() == 0 ? DEFAULT_INNER_PADDING : bean.getInnerPadding();
            //文字颜色
            mPaintText.setColor(
                    Color.parseColor(ToolString.isEmptyOrNull(bean.getTextColor())
                            ? DEFAULT_TEXT_COLOR : bean.getTextColor()));
            //文字大小
            mPaintText.setTextSize(
                    Integer.parseInt(ToolString.isEmptyOrNull(bean.getTextSize())
                            ? String.valueOf(DEFAULT_TEXT_SIZE) : bean.getTextSize()));
            //连接符颜色
            mPaintSplit.setColor(Color.parseColor(
                    ToolString.isEmptyOrNull(bean.getSplitColor())
                            ? DEFAULT_SPLIT_COLOR : bean.getSplitColor()));
            //连接符文字大小
            mPaintSplit.setTextSize(
                    Integer.parseInt(ToolString.isEmptyOrNull(bean.getTextSize())
                            ? String.valueOf(DEFAULT_TEXT_SIZE) : bean.getTextSize()));
            //分隔符与背景边框之间的距离
            outsidePadding = bean.getInnerPadding() == 0 ? DEFAULT_OUTSIDE_PADDING : bean.getOutPadding();
            //倒计时间隔
            this.interval = bean.getInterval() == 0 ? DEFAULT_INTERVAL : bean.getInterval();
            //倒计时总时间
            this.times = bean.getTimes();
            //初始化倒计时
            mTimer = new CustonCountdowmTimer(times, interval, mListener);
            //创建倒计时单位
            setUnitArray(bean);
            //分隔符
            this.split = bean.getSplit();
            //创建所有时间集合map
            timeMap = TimerUtils.getTimeMap(bean.getTimes());
            //获取显示时间
            showTime = TimerUtils.getTimerStr(TimerUtils.getShowTime(bean.getTimes(), bean, timeMap)
                    , DEFAULT_SPLIT_TEXT);
        }
    }

    /**
     * 根据配置bean 设置单位
     *
     * @param bean 配置bean
     */
    private void setUnitArray(CountdownBean bean) {
        if (bean.isShowUnit()) {
            unitArray.clear();
            if (bean.isShowHour()) {
                unitArray.add("时");
            }

            if (bean.isShowMinutes()) {
                unitArray.add("分");
            }

            if (bean.isShowSecond()) {
                unitArray.add("秒");
            }

            if (bean.isShowMillisecond()) {
                unitArray.add("秒");
            }
        }
    }


    /**
     * 滚动时调用
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        //不可见
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            cancel();
        } else {
            if (!isGoing && isStart) {
                goon();
            }
        }
    }

    @Override
    public void destroyDrawingCache() {
        super.destroyDrawingCache();
        ZLog.e("destroyDrawingCache");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ZLog.e("onDetachedFromWindow");
//        destory();
    }

    /**
     * 销毁时，清空页面资源
     */
    public void destory() {
        mPaintStroke = null;
        mPaintText = null;
        mPaintSplit = null;
        unitArray = null;
        mContext = null;
        onTimerListener = null;
        timeMap = null;
        mBean = null;
        mTimer = null;
    }

    /**
     * 设置外部监听回调
     */
    public CustomCountDown setOnTimerListener(OnTimerListener onTimerListener) {
        this.onTimerListener = onTimerListener;
        return this;
    }

    public int getInnerPadding() {
        return innerPadding;
    }

    public CustomCountDown setInnerPadding(int innerPadding) {
        this.innerPadding = innerPadding;
        mBean.setInnerPadding(innerPadding);
        return this;
    }

    public int getOutsidePadding() {
        return outsidePadding;
    }

    public CustomCountDown setOutsidePadding(int outsidePadding) {
        this.outsidePadding = outsidePadding;
        mBean.setOutPadding(outsidePadding);
        return this;
    }

    public String getBgColor() {
        return mBgColor;
    }

    public CustomCountDown setBgColor(String bgColor) {
        this.mBgColor = bgColor;
        mPaintStroke.setColor(
                Color.parseColor(null == bgColor ? DEFAULT_BG_COLOR : bgColor));
        mBean.setBgColor(bgColor);
        return this;
    }

    public String getTextColor() {
        return mTextColor;
    }

    public CustomCountDown setTextColor(String textColor) {
        this.mTextColor = textColor;
        mBean.setTextColor(textColor);
        mPaintText.setColor(
                Color.parseColor(null == textColor ? DEFAULT_BG_COLOR : textColor));
        return this;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public CustomCountDown setTextSize(int textSize) {
        this.mTextSize = textSize;
        mBean.setTextSize(String.valueOf(textSize));
        mPaintText.setTextSize(textSize);
        return this;
    }

    public String getSplitColor() {
        return mSplitColor;
    }

    public CustomCountDown setSplitColor(String splitColor) {
        this.mSplitColor = splitColor;
        mBean.setSplitColor(splitColor);
        mPaintSplit.setColor(
                Color.parseColor(null == splitColor ? DEFAULT_BG_COLOR : splitColor));
        return this;
    }

    public int getRad() {
        return rad;
    }

    public CustomCountDown setRad(int rad) {
        this.rad = rad;
        mBean.setRad(rad);
        return this;
    }

    public int getInterval() {
        return interval;
    }

    /**
     * 如果当前倒计时已经开始，设置无效,
     */
    public CustomCountDown setInterval(int interval) {
        if (!isGoing) {
            this.interval = interval;
            mBean.setInterval(interval);
        }
        return this;
    }

    public String getKey() {
        return key;
    }

    public CustomCountDown setKey(String key) {
        this.key = key;
        return this;
    }

    public String getSplit() {
        return split;
    }

    public CustomCountDown setSplit(String split) {
        this.split = split;
        mBean.setSplit(split);

        return this;
    }

    public interface TimeCallBack {
        void onTick(long timeLeft);

        void onFinish();
    }
}
