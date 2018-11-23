package com.zkq.weapon.market.util;

import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * @author zkq
 * create:2018/11/16 10:28 AM
 * email:zkq815@126.com
 * desc:Rom判断
 */
public class RomJustUtil {

    /**
     *rom是否是MIUI的标示
     */
    public static final String IS_MIUI_ROM_FLAG = "IS_MIUI_ROM_FLAG";

    /**
     *rom是否是FLYME的标示
     */
    public static final String IS_FLYME_ROM_FLAG = "IS_FLYME_ROM_FLAG";

    public static boolean isMeizuFlymeOS() {
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

    public static boolean isMIUIOS() {
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
    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
            return null;
        }
    }
}
