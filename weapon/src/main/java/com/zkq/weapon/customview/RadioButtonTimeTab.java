package com.zkq.weapon.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.zkq.weapon.R;


/**
 * @author zkq
 * time: 2018/11/15:14:01
 * email: zkq815@126.com
 * desc:
 *
 * 自定义RadioButton
 */
public class RadioButtonTimeTab extends AppCompatRadioButton {

    private Paint mPaintTime = new Paint(Paint.ANTI_ALIAS_FLAG);//时间画笔
    private Paint mPaintDate = new Paint(Paint.ANTI_ALIAS_FLAG);//日期画笔

    private String mTimeSt;//时间
    private String mDateSt;//日期
    private boolean mStart;
    private Typeface defaultFont;
    private Typeface boldFont;


    public RadioButtonTimeTab(Context context) {
        super(context);
        setButtonDrawable(new StateListDrawable());
        initFontTypeface();
    }

    public RadioButtonTimeTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        setButtonDrawable(new StateListDrawable());
        initFontTypeface();
    }

    public RadioButtonTimeTab(Context context, boolean isStart) {
        super(context);
        setButtonDrawable(new StateListDrawable());
        initFontTypeface();
        mStart = isStart;
    }

    /**
     * 字体初始化
     */
    private void initFontTypeface() {
        defaultFont = Typeface.DEFAULT;
        boldFont = Typeface.DEFAULT_BOLD;
    }

    public void setData(String time, String date) {
        this.mTimeSt = time;
        this.mDateSt = date;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaintDate.setTextSize(30);
        mPaintDate.setTypeface(defaultFont);


        if (isChecked()) {
            mPaintTime.setTypeface(boldFont);
            mPaintTime.setTextSize(54);
            mPaintTime.setColor(ContextCompat.getColor(getContext(), R.color.white));
            mPaintDate.setColor(ContextCompat.getColor(getContext(), R.color.white));
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_alpha_0));
        } else {
            mPaintTime.setTypeface(defaultFont);
            mPaintTime.setTextSize(getContext().getResources().getDimension(R.dimen.action_bar_text_color_blue_size));
            mPaintTime.setColor(ContextCompat.getColor(getContext(), R.color.grey_cbc));
            mPaintDate.setColor(ContextCompat.getColor(getContext(), R.color.blue_category_selected));
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue_category_selected));
        }

        mPaintTime.setTextAlign(Paint.Align.CENTER);
        mPaintDate.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetricsInt fontMetricsTime = mPaintTime.getFontMetricsInt();
        float baseLineTime = ((getHeight() - fontMetricsTime.bottom + fontMetricsTime.top)
                / 2 - fontMetricsTime.top - 15);

        Paint.FontMetricsInt fontMetricsDate = mPaintTime.getFontMetricsInt();
        float baseLineDate = ((getHeight() - fontMetricsDate.bottom + fontMetricsDate.top)
                / 2 - fontMetricsDate.top) + 15;

        canvas.drawText(mTimeSt, (getWidth() / 2), baseLineTime, mPaintTime);
        if (mStart) {
            canvas.drawText("进行中", (getWidth() / 2), baseLineDate, mPaintDate);
        } else {
            canvas.drawText(mDateSt, (getWidth() / 2), baseLineDate, mPaintDate);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        invalidate();
    }

    public String getTimeSt() {
        return mTimeSt;
    }

    public void setStart(boolean mStart) {
        this.mStart = mStart;
    }

    public boolean isStart() {
        return mStart;
    }

}
