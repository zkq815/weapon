package com.zkq.weapon.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.zkq.weapon.R;
import com.zkq.weapon.market.tools.ToolSize;

import java.lang.ref.WeakReference;

import androidx.core.content.ContextCompat;

/**
 * @author zkq
 * create:2020/6/29 3:34 PM
 * email:zkq815@126.com
 * desc: 这个View可覆盖于View之上，用于UI的绘制，比如扫描框，扫描线,扫描框内外的背景颜色以及透明度等等。
 */
public class CustomPhotoCoverView extends View {

    //背景色
    private final Paint outsidePaint = new Paint();
    //扫描边框角的颜色
    private final Paint cornerPaint = new Paint();

    //扫描线相关属性,设置方式详见以下方法：
    private final Paint laserPaint = new Paint();   //扫描线Paint
    private int laserColor;
    private boolean showLaser = true;      //是否显示扫描线
    private float laserStartH = 0f;        //扫描线初始位置
    private int laserChangeBounds = 20;    //渐变线的默认高度，默认20px
    private int laserChangeTime = 40;      //UI刷新间隔

    private QRHandler handler;

    //扫描框的宽和高
    private float scannerW;
    private float scannerH;
    //扫描框的上边距和左边距
    private float top;
    private float left;
    //扫描边框的长度和厚度
    private float cornerLength;
    private float cornerWidth;
    private Context context;
    //扫描边框是否包裹扫描框
    private boolean isCornerOutside = false;
    //扫描框的Rect
    private RectF viewFinderRect;

    public CustomPhotoCoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initAttrs(context, attrs);
        handler = new QRHandler(this);
    }

    private void initAttrs(final Context context, final AttributeSet attrs) {
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QRCoverView);
        try {
            //默认扫描框大小为180dp,分母为扫描框所处View位置的大概比例，若有需求可以自己修改
            scannerW = typedArray.getDimension(R.styleable.QRCoverView_scanRectWidth,
                    ToolSize.dip2px(context, 180));
            scannerW = displayMetrics.widthPixels - ToolSize.dip2px(context, 90);
            left =  ToolSize.dip2px(context, 45);
            scannerH = scannerW;
            top = (displayMetrics.heightPixels - scannerH) / 3;

            //默认扫描框为16dp长，3dp宽
            cornerLength = typedArray.getDimension(R.styleable.QRCoverView_cornerLength,
                    ToolSize.dip2px(context, 16));
            cornerWidth = typedArray.getDimension(R.styleable.QRCoverView_cornerWidth,
                    ToolSize.dip2px(context, 3));
            isCornerOutside = typedArray.getBoolean(R.styleable.QRCoverView_cornerOutside, false);

            //默认背景色和扫描边框颜色
            outsidePaint.setColor(typedArray.getColor(R.styleable.QRCoverView_outsideBackgroundColor,
                    ContextCompat.getColor(context, R.color.white_alpha_65)));
            cornerPaint.setColor(typedArray.getColor(R.styleable.QRCoverView_cornerColor,
                    ContextCompat.getColor(context, R.color.white)));
            laserColor = typedArray.getColor(R.styleable.QRCoverView_laserColor,
                    ContextCompat.getColor(context, android.R.color.white));

            showLaser = typedArray.getBoolean(R.styleable.QRCoverView_laserVisible, true);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //默认扫描框为180dp
        if (scannerW == 0f || scannerH == 0f) {
            viewFinderRect = new RectF(left, top, ToolSize.dip2px(context, 180), top + ToolSize.dip2px(context, 180));
        } else {
            viewFinderRect = new RectF(left, top, left + scannerW, top + scannerH);
        }
        //绘制背景色
        drawScanBackground(canvas, viewFinderRect);
        //绘制扫描边框
        drawScanCorner(canvas, viewFinderRect);
        //绘制扫描线
//        drawLaserLine(canvas, viewFinderRect);
    }

    /**
     * 绘制扫描线
     *
     * @param canvas
     * @param viewFinderRect 扫描框的Rect
     */
    private void drawLaserLine(Canvas canvas, RectF viewFinderRect) {
        if (showLaser) {
            //显示并绘制扫描线
            Path mPath = new Path();
            laserPaint.setAntiAlias(true);
            laserPaint.setColor(Color.TRANSPARENT);
            laserPaint.setAlpha(255);

            laserStartH += 10;
            mPath.reset();
            canvas.clipPath(mPath);
            mPath.addRect(viewFinderRect, Path.Direction.CW);
            if(Build.VERSION.SDK_INT >= 26){
                canvas.clipPath(mPath);
            }else {
                canvas.clipPath(mPath, Region.Op.REPLACE);
            }
            //渐变色的扫描线（白色->透明）
            LinearGradient linearGradient = new LinearGradient(0, laserStartH - laserChangeBounds, 0, laserStartH, new int[]{Color.TRANSPARENT, laserColor}, null, Shader.TileMode.CLAMP);
            //给扫描线Paint设置着色器并绘制
            laserPaint.setShader(linearGradient);
            canvas.drawRect(0, laserStartH - laserChangeBounds, getWidth(), laserStartH, laserPaint);

            //当扫描线达到扫描框底部时，返回扫描线返回顶部
            if (laserStartH >= viewFinderRect.bottom || laserStartH <= viewFinderRect.top) {
                laserStartH = viewFinderRect.top;
            }
        } else {
            //不显示扫描线,重置扫描线
            laserStartH = 0;
        }
        handler.sendEmptyMessageDelayed(MESSAGE_LASER, laserChangeTime);//每隔一段时间之后重新绘制UI
    }

    /**
     * 绘制扫描框外部背景
     *
     * @param canvas
     * @param viewFinderRect
     */
    private void drawScanBackground(Canvas canvas, RectF viewFinderRect) {
        canvas.drawRect(0, 0, viewFinderRect.left, getHeight(), outsidePaint);
        canvas.drawRect(viewFinderRect.left, 0, getWidth(), viewFinderRect.top, outsidePaint);
        canvas.drawRect(viewFinderRect.right, viewFinderRect.top, getWidth(), getHeight(), outsidePaint);
        canvas.drawRect(viewFinderRect.left, viewFinderRect.bottom, viewFinderRect.right,
                getHeight(), outsidePaint);
    }

    /**
     * 绘制扫描边框
     *
     * @param canvas
     * @param viewFinderRect
     */
    private void drawScanCorner(Canvas canvas, RectF viewFinderRect) {
        //扫描边框在扫描框外部
        if (isCornerOutside) {
            canvas.drawRect(viewFinderRect.left - cornerWidth, viewFinderRect.top - cornerWidth,
                    viewFinderRect.left, viewFinderRect.top + cornerLength - cornerWidth, cornerPaint);
            canvas.drawRect(viewFinderRect.left, viewFinderRect.top - cornerWidth,
                    viewFinderRect.left + cornerLength - cornerWidth, viewFinderRect.top, cornerPaint);
            canvas.drawRect(viewFinderRect.right - cornerLength + cornerWidth, viewFinderRect.top - cornerWidth,
                    viewFinderRect.right + cornerWidth, viewFinderRect.top, cornerPaint);
            canvas.drawRect(viewFinderRect.right, viewFinderRect.top, viewFinderRect.right + cornerWidth,
                    viewFinderRect.top + cornerLength - cornerWidth, cornerPaint);
            canvas.drawRect(viewFinderRect.right, viewFinderRect.bottom - cornerLength + cornerWidth,
                    viewFinderRect.right + cornerWidth, viewFinderRect.bottom + cornerWidth, cornerPaint);
            canvas.drawRect(viewFinderRect.right - cornerLength + cornerWidth, viewFinderRect.bottom,
                    viewFinderRect.right, viewFinderRect.bottom + cornerWidth, cornerPaint);
            canvas.drawRect(viewFinderRect.left - cornerWidth, viewFinderRect.bottom - cornerLength + cornerWidth,
                    viewFinderRect.left, viewFinderRect.bottom + cornerWidth, cornerPaint);
            canvas.drawRect(viewFinderRect.left, viewFinderRect.bottom,
                    viewFinderRect.left + cornerLength - cornerWidth, viewFinderRect.bottom + cornerWidth, cornerPaint);
        } else {
            //扫描边框在扫描框内部
            canvas.drawRect(viewFinderRect.left, viewFinderRect.top,
                    viewFinderRect.left + cornerWidth, viewFinderRect.top + cornerLength, cornerPaint);
            canvas.drawRect(viewFinderRect.left + cornerWidth, viewFinderRect.top,
                    viewFinderRect.left + cornerLength, viewFinderRect.top + cornerWidth, cornerPaint);
            canvas.drawRect(viewFinderRect.right - cornerLength, viewFinderRect.top,
                    viewFinderRect.right, viewFinderRect.top + cornerWidth, cornerPaint);
            canvas.drawRect(viewFinderRect.right - cornerWidth, viewFinderRect.top + cornerWidth, viewFinderRect.right,
                    viewFinderRect.top + cornerLength, cornerPaint);
            canvas.drawRect(viewFinderRect.right - cornerWidth, viewFinderRect.bottom - cornerLength,
                    viewFinderRect.right, viewFinderRect.bottom, cornerPaint);
            canvas.drawRect(viewFinderRect.right - cornerLength, viewFinderRect.bottom - cornerWidth,
                    viewFinderRect.right - cornerWidth, viewFinderRect.bottom, cornerPaint);
            canvas.drawRect(viewFinderRect.left, viewFinderRect.bottom - cornerLength,
                    viewFinderRect.left + cornerWidth, viewFinderRect.bottom, cornerPaint);
            canvas.drawRect(viewFinderRect.left + cornerWidth, viewFinderRect.bottom - cornerWidth,
                    viewFinderRect.left + cornerLength, viewFinderRect.bottom, cornerPaint);
        }
    }

    /**
     * 设置扫描框外部颜色
     *
     * @param colorRes
     */
    public CustomPhotoCoverView setCoverViewOutsideColor(int colorRes) {
        outsidePaint.setColor(getResources().getColor(colorRes));
        commitUi();
        return this;
    }

    /**
     * 设置扫描边框颜色
     *
     * @param colorRes
     */
    public CustomPhotoCoverView setCoverViewCornerColor(int colorRes) {
        cornerPaint.setColor(getResources().getColor(colorRes));
        commitUi();
        return this;
    }

    /**
     * 设置扫描边框宽高
     *
     * @param cornerH 扫描边框的长度
     * @param cornerW 扫描边框的厚度
     * @param isDp    若为true则将传入的参数单位视为dp，false 则单位视为px
     */
    public CustomPhotoCoverView setCoverViewCorner(int cornerH, int cornerW, boolean isDp) {
        if (isDp) {
            this.cornerLength = ToolSize.dip2px(context, cornerH);
            this.cornerWidth = ToolSize.dip2px(context, cornerW);
        } else {
            this.cornerLength = cornerH;
            this.cornerWidth = cornerW;
        }
        commitUi();
        return this;
    }

    /**
     * 设置扫描边框宽高（像素）{@link CustomPhotoCoverView#setCoverViewCorner(int, int, boolean)}
     */
    public CustomPhotoCoverView setCoverViewCorner(int cornerH, int cornerW) {
        setCoverViewCorner(cornerH, cornerW, false);
        commitUi();
        return this;
    }

    /**
     * 设置扫描框的宽高(dp)
     *
     * @param width
     * @param height
     */
    public CustomPhotoCoverView setCoverViewScanner(int width, int height) {
        //配置扫描框的宽高
        this.scannerH = ToolSize.dip2px(context, height);
        this.scannerW = ToolSize.dip2px(context, width);
        //重新获取扫描框的左边距和上边距
        left = (getResources().getDisplayMetrics().widthPixels - scannerW) / 2;
        top = (getResources().getDisplayMetrics().heightPixels - scannerH) / 3;
        commitUi();
        return this;
    }


    /**
     * 设置扫描边框的位置；
     * 若调用该setCoverViewConnerFace(true)，则扫描边框则会包裹扫描框显示在界面上；
     * 否则默认扫描边框内贴在扫描框内（大多数app默认都是该模式）。
     *
     * @param isOutside
     */
    public CustomPhotoCoverView setCoverViewConnerFace(boolean isOutside) {
        this.isCornerOutside = isOutside;
        commitUi();
        return this;
    }

    /**
     * 获得扫描框的坐标Rect
     *
     * @return
     */
    public RectF getViewFinderRect() {
        return viewFinderRect;
    }

    /**
     * 设置扫描线是否显示
     *
     * @param showLaser
     * @return
     */
    public CustomPhotoCoverView setShowLaser(boolean showLaser) {
        this.showLaser = showLaser;
        return this;
    }

    private static final int MESSAGE_LASER = -1;

    private void commitUi() {
        //直接invalidate()，会导致重复绘制2次扫描线，所以我们先停止绘制扫描线
        if (handler.hasMessages(MESSAGE_LASER)) {
            handler.removeMessages(MESSAGE_LASER);
        }
        //重新执行onDraw
        invalidate();
    }

    private static class QRHandler extends Handler {

        WeakReference<CustomPhotoCoverView> softReference;

        public QRHandler(CustomPhotoCoverView coverView) {
            softReference = new WeakReference<CustomPhotoCoverView>(coverView);
        }

        @Override
        public void handleMessage(Message msg) {
            if (MESSAGE_LASER == msg.what){
                softReference.get().invalidate();
            }
        }
    }

}