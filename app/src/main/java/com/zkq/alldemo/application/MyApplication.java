package com.zkq.alldemo.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zkq.alldemo.BuildConfig;
import com.zkq.alldemo.network.SimpleOkHttpStack;
import com.zkq.alldemo.util.ImgUtil;

import okhttp3.OkHttpClient;

/**
 * Created by zkq
 * on 2018/2/24.
 */

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static MyApplication instance;
    @NonNull
    private final Application application;
    protected RequestQueue mRequestQueue;
    protected RequestQueue mLogRequestQueue;
    private static RefWatcher sRefWatcher;
    private static final int KILL_MSG = 211;
    private static final int KILL_MSG_WAIT_TIME = 5 * 55 * 1000;
    private Handler sKillerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ImgUtil.destroy();
        }
    };

    public MyApplication(){
        super();
        this.application = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(application)) {
//            // LeakCanary会创建单独的进程用于内存堆分析，这里不应该调用我们的初始化代码，直接返回即可
//            return;
//        }
        sRefWatcher = LeakCanary.install(application);
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

    public static RequestQueue getRequestQueue() {
//        if(getApp()){
//
//        }
        if(null != instance){
            if (null == instance.mRequestQueue ) {
                instance.mRequestQueue = Volley.newRequestQueue(instance.getApplication(), new SimpleOkHttpStack(instance.getOkHttpClient()));
            }
        }
        return instance.mRequestQueue;
    }

    public static RequestQueue getLogRequestQueue() {
        if (instance.mLogRequestQueue == null) {
            instance.mLogRequestQueue = Volley.newRequestQueue(instance.getApplication(), new SimpleOkHttpStack(instance.getOkHttpClient()));
        }
        return instance.mLogRequestQueue;
    }

    public static Application getApp() {
        if (instance != null) {
            return instance.application;
        }
        return null;
    }

    public static void watch(final Object watchedReference) {
        if (null != sRefWatcher) {
            sRefWatcher.watch(watchedReference);
        }
    }

}
