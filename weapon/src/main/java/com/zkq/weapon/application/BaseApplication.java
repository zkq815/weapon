package com.zkq.weapon.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author zkq
 * time: 2018/11/15:20:54
 * email: zkq815@126.com
 * desc:
 */
public class BaseApplication extends MultiDexApplication {
    @SuppressLint("StaticFieldLeak")
    private static BaseApplication instance;
    @NonNull
    private final Application application;
    private static RefWatcher sRefWatcher;

    public static Application getInstance() {
        return instance.getBaseApplication();
    }

    public BaseApplication() {
        super();
        this.application = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // LeakCanary会创建单独的进程用于内存堆分析，这里不应该调用我们的初始化代码，直接返回即可
            return;
        }
        sRefWatcher = LeakCanary.install(application);
    }

    @NonNull
    public Application getBaseApplication() {
        return application;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void watch(final Object watchedReference) {
        if (null != sRefWatcher) {
            sRefWatcher.watch(watchedReference);
        }
    }
}
