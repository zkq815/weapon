package com.zkq.alldemo.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.zkq.alldemo.R;

public class RTextView extends View {

	/**
	 * 基本属性
	 */
	private String mText = "Loading";
	private int mTextColor;
	private int mTextSize;

	/**
	 * 画笔,文本绘制范围
	 */
	private Rect mBound;
	private Paint mPaint;

	public RTextView(Context context) {
		this(context, null);
	}

	public RTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		/*
		 * 获取基本属性
		 */
//		TypedArray a = context.obtainStyledAttributes(attrs,
//				R.styleable.RTextView);
//		mText = a.getString(R.styleable.RTextView_text);
//		mTextSize = a.getDimensionPixelSize(R.styleable.RTextView_textSize, 20);
//		mTextColor = a.getColor(R.styleable.RTextView_textColor, Color.BLACK);
//		a.recycle();

		mText = "zkqsdasd";
		mTextSize = 50;
		mTextColor = getContext().getColor(R.color.blue_beika);
		/*
		 * 初始化画笔
		 */
		mBound = new Rect();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.FILL);
		mPaint.setTextSize(mTextSize);
		mPaint.getTextBounds(mText, 0, mText.length(), mBound);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = onMeasureR(0, widthMeasureSpec);
		int height = onMeasureR(1, heightMeasureSpec);
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		mPaint.setColor(mTextColor);

		/*
		 * 控件宽度/2 - 文字宽度/2
		 */
		float startX = getWidth() / 2 - mBound.width() / 2;

		/*
		 * 控件高度/2 + 文字高度/2,绘制文字从文字左下角开始,因此"+"
		 */
		// float startY = getHeight() / 2 + mBound.height() / 2;

		FontMetricsInt fm = mPaint.getFontMetricsInt();

		// int startY = getHeight() / 2 - fm.descent + (fm.descent - fm.ascent)
		// / 2;

		int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;

		// 绘制文字
		canvas.drawText(mText, startX, startY, mPaint);

		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);
		// 中线,做对比
		canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
	}

	/**
	 * 计算控件宽高
	 * 
	 * @param	 *            [0宽,1高]
	 * @param oldMeasure
	 * @author Ruffian
	 */
	public int onMeasureR(int attr, int oldMeasure) {

		int newSize = 0;
		int mode = MeasureSpec.getMode(oldMeasure);
		int oldSize = MeasureSpec.getSize(oldMeasure);

		switch (mode) {
		case MeasureSpec.EXACTLY:
			newSize = oldSize;
			break;
		case MeasureSpec.AT_MOST:
			float value;

			if (attr == 0) {

				// value = mBound.width();
				value = mPaint.measureText(mText);

				// 控件的宽度 + getPaddingLeft() + getPaddingRight()
				newSize = (int) (getPaddingLeft() + value + getPaddingRight());

			} else if (attr == 1) {

				// value = mBound.height();
				FontMetrics fontMetrics = mPaint.getFontMetrics();
				value = Math.abs((fontMetrics.bottom - fontMetrics.top));

				// 控件的高度 + getPaddingTop() + getPaddingBottom()
				newSize = (int) (getPaddingTop() + value + getPaddingBottom());

			}

			break;
			default:
				break;
		}

		return newSize;
	}

}
