package com.zkq.alldemo.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/12/2.
 */

public class MyTextView extends AppCompatTextView {

    private Paint paint1,paint2;

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint1 = new Paint();
        paint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        paint1.setStyle(Paint.Style.FILL);
        paint2 = new Paint();
        paint2.setColor(getResources().getColor(android.R.color.holo_red_light));
        paint2.setStyle(Paint.Style.FILL);
    }


    public MyTextView(Context context) {
        this(context,null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制外层矩形
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint1);
        //绘制内层矩形
        canvas.drawRect(10,10,getMeasuredWidth()-10,getMeasuredHeight()-10,paint2);
        canvas.save();
        //绘制文字前平移10像素
        canvas.translate(10,0);
        //父类完成的方法，绘制文本
        super.onDraw(canvas);
        canvas.restore();//释放资源
    }
}
