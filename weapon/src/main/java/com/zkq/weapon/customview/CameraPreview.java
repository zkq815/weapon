package com.zkq.weapon.customview;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zkq.weapon.market.tools.ToolScreen;

import java.io.IOException;
import java.util.ArrayList;
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
    private Camera.Parameters params;
    private boolean isFocusing;
    private Handler mHandler = new Handler();
    private final Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //一秒之后才能再次对焦
                    isFocusing = false;
                    cameraFocusController.unlockFocus();
                }
            }, 1000);
        }
    };
    private CameraFocusController cameraFocusController;

    public CameraPreview(Context context) {
        this(context, null);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化Camera对象
        mCamera = Camera.open();
        params = mCamera.getParameters();
        //设置自动对焦
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
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
        cameraFocusController = CameraFocusController.getInstance(context);
        cameraFocusController.setCameraFocusListener(new CameraFocusController.CameraFocusListener() {
            @Override
            public void onFocus() {
                if (mCamera != null) {
                    DisplayMetrics mDisplayMetrics = context.getApplicationContext().getResources()
                            .getDisplayMetrics();
                    int mScreenWidth = mDisplayMetrics.widthPixels;
                    if (!cameraFocusController.isFocusLocked()) {
                        if (newFocus(mScreenWidth / 2, mScreenWidth / 2)) {
                            cameraFocusController.lockFocus();
                        }
                    }
                }
            }
        });
        cameraFocusController.start();
    }

    private boolean newFocus(int x, int y) {
        //正在对焦时返回
        if (mCamera == null || isFocusing) {
            return false;
        }
        isFocusing = true;
        setMeteringRect(x, y);
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.cancelAutoFocus(); // 先要取消掉进程中所有的聚焦功能
        try {
            mCamera.setParameters(params);
            mCamera.autoFocus(autoFocusCallback);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 设置感光区域
     * 需要将屏幕坐标映射到Rect对象对应的单元格矩形
     *
     * @param x
     * @param y
     */
    private void setMeteringRect(int x, int y) {
        if (params.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> areas = new ArrayList<Camera.Area>();
            Rect rect = new Rect(x - 100, y - 100, x + 100, y + 100);
            int left = rect.left * 2000 / ToolScreen.getScreenWidth(getContext()) - 1000;
            int top = rect.top * 2000 / ToolScreen.getScreenHeight(getContext()) - 1000;
            int right = rect.right * 2000 / ToolScreen.getScreenWidth(getContext()) - 1000;
            int bottom = rect.bottom * 2000 / ToolScreen.getScreenHeight(getContext()) - 1000;
            // 如果超出了(-1000,1000)到(1000, 1000)的范围，则会导致相机崩溃
            left = left < -1000 ? -1000 : left;
            top = top < -1000 ? -1000 : top;
            right = right > 1000 ? 1000 : right;
            bottom = bottom > 1000 ? 1000 : bottom;
            Rect area1 = new Rect(left, top, right, bottom);
            //只有一个感光区，直接设置权重为1000了
            areas.add(new Camera.Area(area1, 1000));
            params.setMeteringAreas(areas);
        }
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
            mCamera.cancelAutoFocus();
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
        mCamera.cancelAutoFocus();
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
