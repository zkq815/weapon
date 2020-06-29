package com.zkq.weapon.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.zkq.weapon.R;
import com.zkq.weapon.R2;
import com.zkq.weapon.market.zxing.view.QRCodeScannerView;
import com.zkq.weapon.market.zxing.view.QRCoverView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zkq
 * create:2019/6/2 2:40 PM
 * email:zkq815@126.com
 * desc: 二维码扫描
 */
public class ScanActivity extends BaseActivity implements View.OnClickListener {

    private QRCodeScannerView mScannerView;
    private QRCoverView mCoverView;

    private static final String TAG = "ScanActivity";

    private final int PERMISSION_REQUEST_CAMERA = 0;
    @BindView(R2.id.zxing_btn_test1)
    Button btnOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        initView();
        hideActionBar();
    }

    /**
     * 隐藏状态栏
     */
    private void hideActionBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getSupportActionBar().hide();
    }

    private void initView() {
        btnOne.setOnClickListener(this);
        findViewById(R.id.zxing_btn_test2).setOnClickListener(this);
        findViewById(R.id.zxing_btn_test3).setOnClickListener(this);
        findViewById(R.id.zxing_btn_test4).setOnClickListener(this);
        findViewById(R.id.zxing_btn_test5).setOnClickListener(this);
        findViewById(R.id.zxing_btn_test6).setOnClickListener(this);
        findViewById(R.id.zxing_btn_test7).setOnClickListener(this);
        findViewById(R.id.zxing_btn_test8).setOnClickListener(this);
        findViewById(R.id.zxing_btn_test9).setOnClickListener(this);

        mScannerView = (QRCodeScannerView) findViewById(R.id.scanner_view);
        mCoverView = (QRCoverView) findViewById(R.id.cover_view);

        //自动聚焦间隔2s
        mScannerView.setAutofocusInterval(2000L);
        //扫描结果监听处理
        mScannerView.setOnQRCodeReadListener((text, points) -> {
                    Log.d(TAG, "扫描结果 result -> " + text);
                    //扫描到的内容
                    //【可选】判断二维码是否在扫描框中
                    judgeResult(text, points);
                }
        );
        //相机权限监听(如果你有相关的权限类，可以不实现该接口)
        mScannerView.setOnCheckCameraPermissionListener(() -> {
                    if (ContextCompat.checkSelfPermission(ScanActivity.this
                            , Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        return true;
                    } else {
                        ActivityCompat.requestPermissions(ScanActivity.this
                                , new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                        return false;
                    }
                }
        );
        //开启后置摄像头
        mScannerView.setBackCamera();
    }

    private void judgeResult(String result, PointF[] points) {
        //接下来是处理二维码是否在扫描框中的逻辑
        RectF finderRect = mCoverView.getViewFinderRect();
        Log.d("tag", "points.length = " + points.length);
        boolean isContain = true;
        //依次判断扫描结果的每个point是否都在扫描框内
        for (int i = 0, length = points.length; i < length; i++) {
            if (!finderRect.contains(points[i].x, points[i].y)) {
                //只要有一个不在，说明二维码不完全在扫描框中
                isContain = false;
                break;
            }
        }
        if (isContain) {
            Intent intent = new Intent();
            intent.putExtra("result", result);
            setResult(2, intent);
            finish();
        } else {
            Log.d(TAG, "扫描失败！请将二维码图片摆放在正确的扫描区域中...");
        }
    }

    /**
     * 权限请求回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mScannerView.grantCameraPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.zxing_btn_test1) {
            //扫描框的宽度和高度，提交修改UI
            mCoverView.setCoverViewScanner(280, 280);
        } else if (i == R.id.zxing_btn_test2) {
            //切换摄像头
            mScannerView.switchCameraFace();
        } else if (i == R.id.zxing_btn_test3) {
            //修改扫描框外背景色，提交修改UI
            mCoverView.setCoverViewOutsideColor(R.color.cover_bg2);
        } else if (i == R.id.zxing_btn_test4) {
            //扫描框边角在外
            mCoverView.setCoverViewConnerFace(true);
        } else if (i == R.id.zxing_btn_test5) {
            //不显示扫描线
            mCoverView.setShowLaser(false);
        } else if (i == R.id.zxing_btn_test6) {
            //显示扫描线
            mCoverView.setShowLaser(true);
        } else if (i == R.id.zxing_btn_test7) {
            //停止扫描
            mScannerView.setQRDecodingEnabled(false);
            //隐藏扫描线
            mCoverView.setShowLaser(false);
        } else if (i == R.id.zxing_btn_test8) {
            //startActivity(new Intent(this, SecondActivity.class));
        } else  if(i == R.id.zxing_btn_test9){
//            mScannerView.get
        }
    }
}
