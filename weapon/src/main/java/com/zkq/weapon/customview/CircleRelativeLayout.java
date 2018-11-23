package com.zkq.weapon.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author zkq
 * create:2018/11/16 10:07 AM
 * email:zkq815@126.com
 * desc: 圆形布局
 */

public class CircleRelativeLayout extends RelativeLayout {

    private int color;
    private int[] colors;
    private int alpha;
    private Paint mPaint = new Paint();
    public CircleRelativeLayout(Context context) {
        super(context);
    }
    public CircleRelativeLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
        setWillNotDraw(false);
    }
    private void init() {
        color = 0X0000000;
        alpha = 255;
        setColors();
    }
    @Override
    protected void onDraw(Canvas canvas) { //构建圆形
        int width = getMeasuredWidth();
        mPaint.setARGB(alpha,colors[0],colors[1],colors[2]);
        mPaint.setAntiAlias(true);
        float cirX = width / 2;
        float cirY = width / 2;
        float radius = width / 2;
        canvas.drawCircle(cirX, cirY, radius, mPaint);
        super.onDraw(canvas);
    }

    public void setColor(int color) { //设置背景色
        this.color = color;
        setColors();
        invalidate();
    }

    public void setAlhpa(int alhpa) { //设置透明度
        this.alpha = alhpa;
        invalidate();
    }


    public void setColors() {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        this.colors = new int[]{red,green,blue};
    }
}
