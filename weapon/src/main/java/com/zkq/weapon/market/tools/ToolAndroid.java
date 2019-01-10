package com.zkq.weapon.market.tools;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * @author zkq
 * time: 2018/12/11:14:54
 * email: zkq815@126.com
 * desc: android 系统工具类
 */
public interface ToolAndroid {

    /**
     *rom是否是MIUI的标示
     */
    String IS_MIUI_ROM_FLAG = "IS_MIUI_ROM_FLAG";

    /**
     *rom是否是FLYME的标示
     */
    String IS_FLYME_ROM_FLAG = "IS_FLYME_ROM_FLAG";

    static boolean isMeizuFlymeOS() {
        /* 获取魅族系统操作版本标识*/
        String meizuFlymeOSFlag = getSystemProperty("ro.build.display.id", "");
        if (null == meizuFlymeOSFlag) {
            return false;
        } else if (meizuFlymeOSFlag.contains("flyme") || meizuFlymeOSFlag.toLowerCase().contains("flyme")) {
            return true;
        } else {
            return false;
        }
    }

    static boolean isMIUIOS() {
        /* 获版MIUI标识*/
        String meizuFlymeOSFlag = getSystemProperty("ro.miui.ui.version.name", "");
        if (!TextUtils.isEmpty(meizuFlymeOSFlag)) {
            return true;
        }
        return false;

    }

    /**
     * 获取系统属性
     *
     * @param key          ro.build.display.id
     * @param defaultValue 默认值
     * @return 系统操作版本标识
     */
    static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前版本号
     */
    static String getVersion(Activity activity) {
        try {
            PackageManager manager = activity.getPackageManager();
            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 从资源文件id获取像素值
     *
     * @param context context
     * @param id      dimen文件id  R.dimen.resourceId
     * @return R.dimen.resourceId对应的尺寸具体的像素值
     */
    static int getDimension(@NonNull Context context, @DimenRes int id) {
        return (int) context.getResources().getDimension(id);
    }

    /**
     * 获取手机的屏幕宽度
     */
    static int getScreenWidth(Activity activity) {
        int screenWidth;

        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();

        return screenWidth;
    }

    /**
     * 获取手机的屏幕宽度
     */
    static int getScreenHeight(Activity activity) {
        int screenHeight;
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenHeight = display.getHeight();

        return screenHeight;
    }

    static int getPhoneWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    /**
     * 拷贝到剪贴板
     *
     * @param context context
     * @param label   用户可见标签
     * @param content 实际剪贴的文本内容
     */
    static void copyToClipboard(final Context context, final String label, final String content) {
        final ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData data = ClipData.newPlainText(label, content);
        manager.setPrimaryClip(data);
    }

}
