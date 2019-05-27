package com.zkq.alldemo.fortest.fingertest;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;
import android.util.Log;

import com.zkq.weapon.market.util.ZLog;

import java.lang.ref.WeakReference;

/**
 * @author zkq
 * create:2018/12/12 3:47 PM
 * email:zkq815@126.com
 * desc:
 */
@TargetApi(Build.VERSION_CODES.M)
public class FingerprintCore {

    private static final int NONE = 0;
    private static final int CANCEL = 1;
    private static final int AUTHENTICATING = 2;
    private int mState = NONE;

    private FingerprintManagerCompat mFingerprintManager;
    private WeakReference<IFingerprintResultListener> mFpResultListener;
    private CancellationSignal mCancellationSignal;
    private CryptoObjectCreator mCryptoObjectCreator;
    private FingerprintManagerCompat.AuthenticationCallback mAuthCallback;

    private int mFailedTimes = 0;
    private boolean isSupport = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 指纹识别回调接口
     */
    public interface IFingerprintResultListener {
        /** 指纹识别成功 */
        void onAuthenticateSuccess();

        /** 指纹识别失败 */
        void onAuthenticateFailed(int helpId);

        /** 指纹识别发生错误-不可短暂恢复 */
        void onAuthenticateError(int errMsgId);

        /** 开始指纹识别监听成功 */
        void onStartAuthenticateResult(boolean isSuccess);
    }

    public FingerprintCore(Context context) {
        mFingerprintManager = getFingerprintManager(context);
        isSupport = (mFingerprintManager != null && isHardwareDetected());
        ZLog.e("fingerprint isSupport: " + isSupport);
        initCryptoObject();
    }

    private void initCryptoObject() {
        try {
            mCryptoObjectCreator = new CryptoObjectCreator(new CryptoObjectCreator.ICryptoObjectCreateListener() {
                @Override
                public void onDataPrepared(FingerprintManagerCompat.CryptoObject cryptoObject) {
                    // startAuthenticate(cryptoObject);
                    // 如果需要一开始就进行指纹识别，可以在秘钥数据创建之后就启动指纹认证
                }
            });
        } catch (Throwable throwable) {
            ZLog.e("create cryptoObject failed!");
        }
    }

    public void setFingerprintManager(IFingerprintResultListener fingerprintResultListener) {
        mFpResultListener = new WeakReference<IFingerprintResultListener>(fingerprintResultListener);
    }

    public void startAuthenticate() {
        startAuthenticate(mCryptoObjectCreator.getCryptoObject());
    }

    public boolean isAuthenticating() {
        return mState == AUTHENTICATING;
    }

    private void startAuthenticate(FingerprintManagerCompat.CryptoObject cryptoObject) {
        prepareData();
        mState = AUTHENTICATING;
        try {
            mFingerprintManager.authenticate(null, 0, mCancellationSignal,  mAuthCallback, null);
            notifyStartAuthenticateResult(true, "");
        } catch (SecurityException e) {
            try {
                mFingerprintManager.authenticate(null, 0, mCancellationSignal,  mAuthCallback, null);
                notifyStartAuthenticateResult(true, "");
            } catch (SecurityException e2) {
                notifyStartAuthenticateResult(false, Log.getStackTraceString(e2));
            } catch (Throwable throwable) {

            }
        } catch (Throwable throwable) {

        }
    }

    private void notifyStartAuthenticateResult(boolean isSuccess, String exceptionMsg) {
        if (isSuccess) {
            ZLog.e("start authenticate...");
            if (mFpResultListener.get() != null) {
                mFpResultListener.get().onStartAuthenticateResult(true);
            }
        } else {
            ZLog.e("startListening, Exception" + exceptionMsg);
            if (mFpResultListener.get() != null) {
                mFpResultListener.get().onStartAuthenticateResult(false);
            }
        }
    }

    private void notifyAuthenticationSucceeded() {
        ZLog.e("onAuthenticationSucceeded");
        mFailedTimes = 0;
        if (null != mFpResultListener && null != mFpResultListener.get()) {
            mFpResultListener.get().onAuthenticateSuccess();
        }
    }

    private void notifyAuthenticationError(int errMsgId, CharSequence errString) {
        ZLog.e("onAuthenticationError, errId:" + errMsgId + ", err:" + errString + ", retry after 30 seconds");
        if (null != mFpResultListener && null != mFpResultListener.get()) {
            mFpResultListener.get().onAuthenticateError(errMsgId);
        }
    }

    private void notifyAuthenticationFailed(int msgId, String errString) {
        ZLog.e("onAuthenticationFailed, msdId: " +  msgId + " errString: " + errString);
        if (null != mFpResultListener && null != mFpResultListener.get()) {
            mFpResultListener.get().onAuthenticateFailed(msgId);
        }
    }

    private void prepareData() {
        if (mCancellationSignal == null) {
            mCancellationSignal = new CancellationSignal();
        }
        if (mAuthCallback == null) {
            mAuthCallback = new FingerprintManagerCompat.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errMsgId, CharSequence errString) {
                    // 多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证,一般间隔从几秒到几十秒不等
                    // 这种情况不建议重试，建议提示用户用其他的方式解锁或者认证
                    mState = NONE;
                    notifyAuthenticationError(errMsgId, errString);
                }

                @Override
                public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                    mState = NONE;
                    // 建议根据参数helpString返回值
                    notifyAuthenticationFailed(helpMsgId , helpString.toString());
                    onFailedRetry(helpMsgId, helpString.toString());
                }

                @Override
                public void onAuthenticationFailed() {
                    mState = NONE;
                    notifyAuthenticationFailed(0 , "onAuthenticationFailed");
                    onFailedRetry(-1, "onAuthenticationFailed");
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                    mState = NONE;
                    notifyAuthenticationSucceeded();
                }
            };
        }
    }

    public void cancelAuthenticate() {
        if (mCancellationSignal != null && mState != CANCEL) {
            ZLog.e("cancelAuthenticate...");
            mState = CANCEL;
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }

    private void onFailedRetry(int msgId, String helpString) {
        mFailedTimes++;
        ZLog.e("on failed retry time " + mFailedTimes);
        // 每个验证流程最多重试5次，可以指定，验证成功时清0
        if (mFailedTimes > 5) {
            ZLog.e("on failed retry time more than 5 times");
            return;
        }
        ZLog.e("onFailedRetry: msgId " + msgId + " helpString: " + helpString);
        cancelAuthenticate();
        mHandler.removeCallbacks(mFailedRetryRunnable);
        mHandler.postDelayed(mFailedRetryRunnable, 300); // 每次重试间隔一会儿再启动
    }

    private Runnable mFailedRetryRunnable = new Runnable() {
        @Override
        public void run() {
            startAuthenticate(mCryptoObjectCreator.getCryptoObject());
        }
    };

    public boolean isSupport() {
        return isSupport;
    }

    /**
     * 是否有指纹识别硬件支持
     * @return
     */
    public boolean isHardwareDetected() {
        try {
            return mFingerprintManager.isHardwareDetected();
        } catch (SecurityException e) {
        } catch (Throwable e) {}
        return false;
    }

    /**
     * 是否录入指纹，有些设备上即使录入了指纹，但是没有开启锁屏密码的话此方法还是返回false
     * @return
     */
    public boolean isHasEnrolledFingerprints() {
        try {
            return mFingerprintManager.hasEnrolledFingerprints();
        } catch (SecurityException e) {
        } catch (Throwable e) {
        }
        return false;
    }

    public static FingerprintManagerCompat getFingerprintManager(Context context) {
        FingerprintManagerCompat fingerprintManager = null;
        try {
//            fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            fingerprintManager = FingerprintManagerCompat.from(context);
        } catch (Throwable e) {
            ZLog.e("have not class FingerprintManager");
        }
        return fingerprintManager;
    }

    public void onDestroy() {
        cancelAuthenticate();
        mHandler = null;
        mAuthCallback = null;
        mFpResultListener = null;
        mCancellationSignal = null;
        mFingerprintManager = null;
        if (mCryptoObjectCreator != null) {
            mCryptoObjectCreator.onDestroy();
            mCryptoObjectCreator = null;
        }
    }
}
