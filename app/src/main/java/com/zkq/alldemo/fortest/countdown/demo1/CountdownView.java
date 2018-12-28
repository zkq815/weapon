package com.zkq.alldemo.fortest.countdown.demo1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.zkq.weapon.market.util.ZLog;

import java.lang.ref.WeakReference;

/**
 * @author zkq
 * create:2018/12/19 10:23 AM
 * email:zkq815@126.com
 * desc:
 */
public class CountdownView extends TextView implements CountdownTime.OnCountdownTimeListener {
    /**
     * 当前控件绑定的倒计时实践对象id，由于重用，RecyclerView滚动的时候，
     * 会复用view，导致里面显示的时间其实是不一样的
     */
    private String nowId;
    private String mPage;
    private int leftTime;
    private int allTime;
    private boolean isGoing = true;
    private CountdownTimeQueueManager manager;
    private CountdownTime countdownTime;
    private float TEXT_SIZE = 63;
    private int TEXT_COLOR = 0xFF7BAFD4;
    private Paint textPaint;
    private CountdownTime.OnTimeListener onTimeListener;

    public CountdownView(Context context) {
        super(context);
        init();
    }

    public CountdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public boolean isGoing() {
        return isGoing;
    }

    public void setGoing(boolean going) {
        isGoing = going;
    }

    private void init() {
        textPaint = getPaint();
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setStrokeWidth(1);
        manager = CountdownTimeQueueManager.getInstance();
    }

    private void drawText(Canvas canvas) {
        String testString;
        if (countdownTime == null) {
            testString = "00:00";
        } else {
            testString = countdownTime.getTimeText();
        }
        Rect bounds = new Rect();
        textPaint.getTextBounds(testString, 0, testString.length(), bounds);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(testString, getMeasuredWidth() / 2 - bounds.width() / 2, baseline, textPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawText(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * 测量宽度
     * */
    private int measureWidth(int width) {
        int result = (int) textPaint.measureText("00:00");
        int specMode = MeasureSpec.getMode(width);
        int specSize = MeasureSpec.getSize(width);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 测量高度
     * */
    private int measureHeight(int height) {
        int result = (int) textPaint.measureText("00");
        int specMode = MeasureSpec.getMode(height);
        int specSize = MeasureSpec.getSize(height);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 绘制倒计时
     * */
    @Override
    public void onCountdownTimeDraw(CountdownTime time) {
//        ZLog.e("剩余时间=="+time.getSeconds());
        leftTime = time.getSeconds();
        if (TextUtils.equals(nowId, time.getId())) {
            countdownTime = time;
            postInvalidate();
            if (onTimeListener != null) {
                onTimeListener.onTick(time.getSeconds());
            }
        }
    }

    @Override
    public void onTimeCountdownOver(String id) {
        if (onTimeListener != null) {
            onTimeListener.onTimeOver(id);
        }
    }

    /**
     * id参数，需要保证唯一性
     */
    public void setCountdownTime(int time, String id) {
        this.allTime = time;
        this.mPage = id.split("_|_")[0];
        this.nowId = id;
        if (time <= 0) {
            if (countdownTime != null) {
                countdownTime.setSeconds(0);
            }
        } else {
            WeakReference<CountdownView> weakReference = new WeakReference<>(this);
            countdownTime = manager.addTime(time, id, weakReference.get());
        }
        postInvalidate();
    }

    public CountdownTime getCountdownTime() {
        return countdownTime;
    }

    public CountdownTime.OnTimeListener getOnTimeListener() {
        return onTimeListener;
    }

    public void setOnTimeListener(CountdownTime.OnTimeListener onTimeListener) {
        this.onTimeListener = onTimeListener;
    }

    /**
     * 滚动时调用
     * */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);

        //不可见
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            if (null == getContext()) {
                manager.removePageTime(mPage);
            } else {
                pauseTimer();
            }
        } else {
            if (leftTime > 0 && isGoing) {
                setCountdownTime(leftTime, nowId);
            }
        }
    }

    /**
     * 暂停倒计时
     * */
    public void pauseTimer() {
        ZLog.e("剩余时间=="+leftTime);
        manager.removeTime(getCountdownTime());
    }

    /**
     * 继续倒计时
     * */
    public void goonTimer() {
        setCountdownTime(leftTime, nowId);
    }

    /**
     * 重新开始倒计时
     * */
    public void restartTimer() {
        setCountdownTime(allTime, nowId);
    }
}
