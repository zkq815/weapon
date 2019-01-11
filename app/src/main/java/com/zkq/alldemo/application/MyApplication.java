package com.zkq.alldemo.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.zkq.alldemo.BuildConfig;
import com.zkq.weapon.application.BaseApplication;
//import com.zkq.weapon.market.util.ImgUtil;

import okhttp3.OkHttpClient;

/**
 * @author zkq
 * on 2018/2/24.
 */
public class MyApplication extends BaseApplication {
    @SuppressLint("StaticFieldLeak")
    private static MyApplication instance;
    @NonNull
    private final Application application;

    private static final int KILL_MSG = 211;
    private static final int KILL_MSG_WAIT_TIME = 5 * 55 * 1000;
    private Handler sKillerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            ImgUtil.destroy();
        }
    };

    public MyApplication(){
        super();
        this.application = getBaseApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (ActivityStack.size() == 0) {
                    // 第一个创建的activity
//                    OplogHelper.onStart();
                }
                ActivityStack.push(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (BuildConfig.LOG_DEBUG) {
                    if (ActivityStack.atTop(activity)) {
                        ActivityStack.printStack();
                    }
                }
                sKillerHandler.removeMessages(KILL_MSG);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                sKillerHandler.sendEmptyMessageDelayed(KILL_MSG, KILL_MSG_WAIT_TIME);
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityStack.pop(activity);
            }
        });
    }

    @NonNull
    public Application getApplication() {
        return application;
    }

    public static Application getInstance() {
        return instance.getApplication();
    }

    protected OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }

    public static Application getApp() {
        if (instance != null) {
            return instance.application;
        }
        return null;
    }

}
