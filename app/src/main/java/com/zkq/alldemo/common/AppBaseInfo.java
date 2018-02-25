package com.zkq.alldemo.common;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import com.zkq.alldemo.BuildConfig;
import com.zkq.alldemo.MApplication.MyApplication;
import com.zkq.alldemo.util.ZKQLog;

import java.lang.reflect.Method;

/**
 * 应用基本信息<br/>
 * Created by huxiaoyuan on 16/4/11.
 */
public class AppBaseInfo {
    private static AppBaseInfo instance;
    private static final String MZ_PLATFORM_APP_PACKAGE_NAME = "com.meizu.account";

    private AppBaseInfo() {
        init(MyApplication.getInstance());
    }

    public static AppBaseInfo getInstance() {
        if (instance == null) {
            synchronized (AppBaseInfo.class) {
                if (instance == null) {
                    instance = new AppBaseInfo();
                }
            }
        }
        return instance;
    }

    public static final String serialNumber;

    static {
        serialNumber = Build.SERIAL;
    }

    //    public static int versionCode; // 版本号
//    public static String versionName;// 版本名称
//    public static String channelId;// 渠道号
    // 三种主要的基于位置服务(LBS)技术之一。小区识别码通过识别网络中哪一个小区传输用户呼叫并将该信息翻译成纬度和经度来确定用户位置。
    public static float density; // 屏幕的密度
    public static int height; // 屏幕高
    public static int width;// 屏幕宽度
    public static boolean isMzPlatform = false;

    private void init(Context context) {
        try {
            //判断是否是魅族手机
            context.getPackageManager().getApplicationInfo(MZ_PLATFORM_APP_PACKAGE_NAME, 0);
            isMzPlatform = true;
        } catch (Exception e) {
            if (BuildConfig.LOG_DEBUG) {
                ZKQLog.t("init", e);
            }
        }

        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        this.width = dm.widthPixels;
        this.height = dm.heightPixels;
        this.density = dm.density;
    }

    public static String getRomVersion() {
        return getSystemProperty("ro.build.display.id", "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            final Class<?> clz = Class.forName("android.os.SystemProperties");
            final Method get = clz.getMethod("get", String.class, String.class);
            get.setAccessible(true);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
            return null;
        }
    }

//    private static String getSystemVersion() {
//        String version = null;
//
//        try {
//            version = getSystemProperties("ro.build.mask.id", "");
//        } catch (Exception var3) {
//            var3.printStackTrace();
//        }
//
//        if (TextUtils.isEmpty(version)) {
//            version = Build.DISPLAY;
//        }
//
//        return version;
//    }
//
//    private static String getSystemProperties(String key, String defaultValue) {
//        try {
//            return (String) ReflectHelper.invokeStatic("android.os.SystemProperties", "get", new Object[]{key});
//        } catch (Exception var3) {
//            var3.printStackTrace();
//            return defaultValue;
//        }
//    }
}
