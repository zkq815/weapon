package com.zkq.weapon.customview;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.zkq.weapon.market.tools.ToolScreen;

import java.io.IOException;
import java.util.List;

/**
 * @author zkq
 * time: 2020/6/17 2:39 PM
 * email: zkq815@126.com
 * desc: 自定义相机预览
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context) {
        this(context, null);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化Camera对象
        mCamera = Camera.open();
        Camera.Parameters params = mCamera.getParameters();
        //设置自动对焦
//        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        int screenWidth = ToolScreen.getScreenWidth(getContext());
        int screenHeight = ToolScreen.getScreenHeight(getContext());
        List<Camera.Size> pictureSizes = params.getSupportedPictureSizes();
        int length = pictureSizes.size();
        for (int i = 0; i < length; i++) {
            if (pictureSizes.get(i).width < screenWidth && pictureSizes.get(i).height < screenHeight) {
                //如果不设置会按照系统默认配置最低160x120分辨率
                params.setPictureSize(pictureSizes.get(i).width, pictureSizes.get(i).height);
                break;
            }
        }
        mCamera.setParameters(params);
        //得到SurfaceHolder对象
        mHolder = getHolder();
        //添加回调，得到Surface的三个声明周期方法
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_HARDWARE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            //设置预览方向z'z'z
            mCamera.setDisplayOrientation(90);
            //把这个预览效果展示在SurfaceView上面
            mCamera.setPreviewDisplay(holder);
            //开启预览效果
            mCamera.startPreview();
        } catch (IOException e) {
//            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null) {
            return;
        }
        //停止预览效果
        mCamera.stopPreview();
        //重新设置预览效果
        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    public void setCameraPicListener(Camera.PictureCallback pictureCallback) {
        mCamera.takePicture(null, null, pictureCallback);
    }

    public void releaseCamera() {
        if (null != mCamera) {
//            mCamera.stopPreview();
//            mCamera.setPreviewCallback(null);
            mCamera.release();
        }
    }
}
