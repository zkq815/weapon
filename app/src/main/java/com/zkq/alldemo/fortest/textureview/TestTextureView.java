package com.zkq.alldemo.fortest.textureview;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;

/**
 * @author zkq
 * time: 2019/9/4 4:43 PM
 * email: zkq815@126.com
 * desc:
 */
public class TestTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    private GestureDetector mGesture;
    private DrawThread mThread;

    public TestTextureView(Context context) {
        this(context, null);
    }

    public TestTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGesture = new GestureDetector(getContext(), mOnGesture);
        setSurfaceTextureListener(this);
    }

    private GestureDetector.OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("zkq", "onFling");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(MotionEvent event) {
            try {
                Log.e("zkq", "onLongPress" + "---X==" + event.getX() + "---Y==" + event.getY());

                if (getTextureListener() == null) {
                    return;
                }
                setTouchX(event.getX());
                setParentScrollable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            Log.e("zkq", "onDoubleTap");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e("zkq", "onSingleTapConfirmed");
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("zkq", "onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("zkq", "onDown");
            return super.onDown(e);
        }
    };

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        final Surface drawingSurface = new Surface(surface);
        mThread = new DrawThread(this, getContext());
        mThread.init(drawingSurface, width, height, true);
        mThread.start();
        mThread.initHandler();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    public void setTouchX(float touchX) {
        if (mThread != null) {
            mThread.setTouchX(touchX);
            mThread.requestRender();
        }
    }

    private TextureView.SurfaceTextureListener getTextureListener() {
        return getSurfaceTextureListener();
    }

    private void setParentScrollable(boolean scrollable) {
        boolean disallowParent = !scrollable;
        getParent().requestDisallowInterceptTouchEvent(disallowParent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.e("zkq","touch move");
                setTouchX(event.getX());
                break;
            default:
                break;
        }
        mGesture.onTouchEvent(event);
        return true;
    }
}
