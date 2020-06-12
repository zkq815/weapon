package com.zkq.alldemo.fortest.fingertest;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zkq.alldemo.R;
import com.zkq.weapon.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zkq
 * create:2018/12/12 3:44 PM
 * email:zkq815@126.com
 * desc: 指纹识别测试
 */
public class FingerprintMainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.fingerprint_guide)
    ImageView mFingerGuideImg;
    @BindView(R.id.fingerprint_guide_tip)
    TextView mFingerGuideTxt;

    private FingerprintCore mFingerprintCore;
    private KeyguardLockScreenManager mKeyguardLockScreenManager;
    private Toast mToast;

    private CheckPopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_main);
        ButterKnife.bind(this);
        popupWindow = new CheckPopupWindow(this);
        initViewListeners();
        initFingerprintCore();
    }

    private void initFingerprintCore() {
        mFingerprintCore = new FingerprintCore(this);
        mFingerprintCore.setFingerprintManager(mResultListener);
        mKeyguardLockScreenManager = new KeyguardLockScreenManager(this);
    }

    private void initViewListeners() {
        findViewById(R.id.fingerprint_recognition_start).setOnClickListener(this);
        findViewById(R.id.fingerprint_recognition_cancel).setOnClickListener(this);
        findViewById(R.id.fingerprint_recognition_sys_unlock).setOnClickListener(this);
        findViewById(R.id.fingerprint_recognition_sys_setting).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fingerprint_recognition_start:
                startFingerprintRecognition();
                break;
            case R.id.fingerprint_recognition_cancel:
                cancelFingerprintRecognition();
                break;
            case R.id.fingerprint_recognition_sys_unlock:
                startFingerprintRecognitionUnlockScreen();
                break;
            case R.id.fingerprint_recognition_sys_setting:
                enterSysFingerprintSettingPage();
                break;
                default:
                    break;
        }
    }

    /**
     * 进入系统设置页面
     * */
    private void enterSysFingerprintSettingPage() {
        FingerprintUtil.openFingerPrintSettingPage(this);
    }

    /**
     * 取消指纹认证
     * */
    private void cancelFingerprintRecognition() {
        if (mFingerprintCore.isAuthenticating()) {
            mFingerprintCore.cancelAuthenticate();
            resetGuideViewState();
        }
    }

    /**
     * 验证手机指纹
     * */
    private void startFingerprintRecognitionUnlockScreen() {
        if (mKeyguardLockScreenManager == null) {
            return;
        }
        if (!mKeyguardLockScreenManager.isOpenLockScreenPwd()) {
            toastTipMsg(R.string.fingerprint_not_set_unlock_screen_pws);
            FingerprintUtil.openFingerPrintSettingPage(this);
            return;
        }
        mKeyguardLockScreenManager.showAuthenticationScreen(this);
    }

    /**
     * 开始指纹识别
     */
    private void startFingerprintRecognition() {
        //判断是否支持指纹识别
        if (mFingerprintCore.isSupport()) {
            //判断是否已经有录入的指纹
            if (!mFingerprintCore.isHasEnrolledFingerprints()) {
                toastTipMsg(R.string.fingerprint_recognition_not_enrolled);
                FingerprintUtil.openFingerPrintSettingPage(this);
                return;
            }
//            toastTipMsg(R.string.fingerprint_recognition_tip);
            mFingerGuideTxt.setText(R.string.fingerprint_recognition_tip);
            mFingerGuideImg.setBackgroundResource(R.drawable.fingerprint_guide);
            showPop();
//            createDialog();
            //指纹验证是否就绪
            if (mFingerprintCore.isAuthenticating()) {
                toastTipMsg(R.string.fingerprint_recognition_authenticating);
            } else {
                //开启指纹验证
                mFingerprintCore.startAuthenticate();
            }
        } else {
            toastTipMsg(R.string.fingerprint_recognition_not_support);
            mFingerGuideTxt.setText(R.string.fingerprint_recognition_tip);
        }
    }

    private void showPop(){
        popupWindow = new CheckPopupWindow(this);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
    }


    private void resetGuideViewState() {
        mFingerGuideTxt.setText(R.string.fingerprint_recognition_guide_tip);
        mFingerGuideImg.setBackgroundResource(R.drawable.fingerprint_normal);
    }

    /**
     * 认证识别回调
     * */
    private FingerprintCore.IFingerprintResultListener mResultListener = new FingerprintCore.IFingerprintResultListener() {
        //认证成功
        @Override
        public void onAuthenticateSuccess() {
            popupWindow.success();
            resetGuideViewState();
        }

        //认证成功
        @Override
        public void onAuthenticateFailed(int helpId) {
            popupWindow.fail();
            mFingerGuideTxt.setText(R.string.fingerprint_recognition_failed);
        }

        //认证错误
        @Override
        public void onAuthenticateError(int errMsgId) {
            resetGuideViewState();
            toastTipMsg(R.string.fingerprint_recognition_error);
        }

        //
        @Override
        public void onStartAuthenticateResult(boolean isSuccess) {

        }
    };

    //验证手机密码界面返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == KeyguardLockScreenManager.REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            // Challenge completed, proceed with using cipher
            if (resultCode == RESULT_OK) {
                toastTipMsg(R.string.sys_pwd_recognition_success);
            } else {
                toastTipMsg(R.string.sys_pwd_recognition_failed);
            }
        }
    }

    private void toastTipMsg(int messageId) {
        if (mToast == null) {
            mToast = Toast.makeText(this, messageId, Toast.LENGTH_SHORT);
        }
        mToast.setText(messageId);
        mToast.show();
    }

    private Runnable mShowToastRunnable = new Runnable() {
        @Override
        public void run() {
            mToast.show();
        }
    };

    @Override
    protected void onDestroy() {
        if (mFingerprintCore != null) {
            mFingerprintCore.onDestroy();
            mFingerprintCore = null;
        }
        if (mKeyguardLockScreenManager != null) {
            mKeyguardLockScreenManager.onDestroy();
            mKeyguardLockScreenManager = null;
        }
        mResultListener = null;
        mShowToastRunnable = null;
        mToast = null;
        super.onDestroy();
    }
}
