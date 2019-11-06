package com.zkq.alldemo.fortest.textureview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Surface;
import android.view.TextureView;

import com.zkq.alldemo.R;

import androidx.core.content.ContextCompat;

/**
 * @author zkq
 * time: 2019/9/4 4:53 PM
 * email: zkq815@126.com
 * desc:
 */
public class DrawThread extends HandlerThread implements Handler.Callback {
    private static final int MSG_REFRESH = 101;
    private Surface mDrawingSurface;
    private int mDrawingWidth, mDrawingHeight;
    private TextureView.SurfaceTextureListener mListener;
    private float mTouchX;
    private Handler mHandler;
    private Context context;
    private boolean mRequestRender;
    private Paint mCircleLine;

    public DrawThread(TextureView.SurfaceTextureListener listener, Context context) {
        super("DrawingThread");
        this.mListener = listener;
        this.context = context;
        //画小圆点
        mCircleLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleLine.setStyle(Paint.Style.FILL);
    }

    public void init(Surface surface, int width, int height, boolean isFullScreen) {
        mDrawingSurface = surface;
        mDrawingWidth = width;
        mDrawingHeight = height;
    }

    public void initHandler() {
        mHandler = new Handler(getLooper(), this);
    }

    public synchronized void setTouchX(float touchX) {
        float prevTouchX = mTouchX;
        mTouchX = touchX;

        drawValueInfos(prevTouchX);
    }

    private void drawValueInfos(float prevTouchX) {
        if (mListener == null) {
            return;
        }
    }

    public synchronized void requestRender() {
        requestRenderDelayed(0L);
    }

    public void requestRenderDelayed(final long delay) {

        synchronized (this) {
            mRequestRender = true;
        }

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(MSG_REFRESH, delay);
        }
    }

    public void refreshLineView() {
        synchronized (this) {
            if (!readyToDraw()) {
                return;
            }
            mRequestRender = false;

            Canvas c = null;
            try {
                if (!mDrawingSurface.isValid()) {
                    return;
                }
                c = mDrawingSurface.lockCanvas(null);
                if (c == null) {
                    return;
                }
                //画背景色，否则会显示为全黑背景
                c.drawColor(ContextCompat.getColor(context, R.color.red_alpha_15));
                drawValue(c);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (c != null) {
                        mDrawingSurface.unlockCanvasAndPost(c);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void drawValue(Canvas canvas) {
        mCircleLine.setColor(ContextCompat.getColor(context, R.color.white));
        canvas.drawLine(0,100, mTouchX,100, mCircleLine);
        canvas.drawLine(mTouchX,0, mTouchX, 500, mCircleLine);
        drawValueInfos(mTouchX);
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_REFRESH:
                refreshLineView();
                break;
            default:
                break;
        }
        return true;
    }

    private boolean readyToDraw() {
        return mRequestRender;
    }

}
