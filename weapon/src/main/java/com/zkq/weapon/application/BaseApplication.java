package com.zkq.weapon.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.tencent.mmkv.MMKV;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/**
 * @author zkq
 * time: 2018/11/15:20:54
 * email: zkq815@126.com
 * desc: 基类Application
 */
public class BaseApplication extends MultiDexApplication {
    @SuppressLint("StaticFieldLeak")
    private static BaseApplication instance;
    @NonNull
    private final Application application;

    public static Application getInstance() {
        return instance.getBaseApplication();
    }

    public BaseApplication() {
        super();
        instance = this;
        this.application = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //MMKV 存储方案
        MMKV.initialize(this);
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
}
