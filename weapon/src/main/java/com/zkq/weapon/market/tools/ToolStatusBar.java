package com.zkq.weapon.market.tools;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zkq.weapon.market.util.ZLog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author:zkq
 * create:2018/10/24 上午11:49
 * email:zkq815@126.com
 * desc: http://redmine.meizu.com/documents/1537
 */
public class ToolStatusBar {
    private final static String TAG = "StatusBarColorUtils";

    private static Method mSetStatusBarColorIcon;
    private static Method mSetStatusBarDarkIcon;
    private static Field mStatusBarColorFiled;

    private static boolean mStatusBarDark = true;

    static {
        try {
            mSetStatusBarColorIcon = Activity.class.getMethod("setStatusBarDarkIcon", int.class);
        } catch (Exception e) {
            ZLog.i(TAG, "printStackTrace:" + e.getMessage());
        }
        try {
            mSetStatusBarDarkIcon = Activity.class.getMethod("setStatusBarDarkIcon", boolean.class);
        } catch (Exception e) {
            ZLog.i(TAG, "printStackTrace:" + e.getMessage());
        }
        try {
            mStatusBarColorFiled = WindowManager.LayoutParams.class.getField("statusBarColor");
        } catch (Exception e) {
            ZLog.i(TAG, "printStackTrace:" + e.getMessage());
        }
    }

    /**
     * 判断颜色是否偏黑色
     *
     * @param color 颜色
     * @param level 级别
     * @return
     */
    public static boolean isBlackColor(int color, int level) {
        int grey = toGrey(color);
        return grey < level;
    }

    /**
     * 颜色转换成灰度值
     *
     * @param rgb 颜色
     * @return　灰度值
     */
    public static int toGrey(int rgb) {
        int blue = rgb & 0x000000FF;
        int green = (rgb & 0x0000FF00) >> 8;
        int red = (rgb & 0x00FF0000) >> 16;
        return (red * 38 + green * 75 + blue * 15) >> 7;
    }

    /**
     * 设置状态栏字体图标颜色
     *
     * @param activity 当前activity
     * @param color    颜色
     */
    public static void setStatusBarDarkIcon(Activity activity, int color) {
        if (mSetStatusBarColorIcon != null) {
            try {
                mSetStatusBarColorIcon.invoke(activity, color);
            } catch (Exception e) {
                ZLog.i(TAG, "printStackTrace:" + e.getMessage());
            }
        } else {
            boolean whiteColor = isBlackColor(color, 50);
            if (mStatusBarColorFiled != null) {
                setStatusBarDarkIcon(activity, whiteColor, whiteColor);
                setStatusBarDarkIcon(activity.getWindow(), color);
            } else {
                setStatusBarDarkIcon(activity, whiteColor);
            }
        }
    }

    /**
     * 设置状态栏字体图标颜色(只限全屏非activity情况)
     *
     * @param window 当前窗口
     * @param color  颜色
     */
    public static void setStatusBarDarkIcon(Window window, int color) {
        try {
            setStatusBarColor(window, color);
            if (Build.VERSION.SDK_INT > 22) {
                setStatusBarDarkIcon(window.getDecorView(), true);
            }
        } catch (Exception e) {
            ZLog.i(TAG, "printStackTrace:" + e.getMessage());
        }
    }

    /**
     * 设置状态栏字体图标颜色
     *
     * @param activity 当前activity
     * @param dark     是否深色 true为深色 false 为白色
     */
    public static void setStatusBarDarkIcon(Activity activity, boolean dark) {
        setStatusBarDarkIcon(activity, dark, false);
    }

    /**
     * 设置状态栏字体图标颜色
     *
     * @param activity 当前activity
     * @param dark     是否深色 true为深色 false 为白色
     * @param forceUpdate 是否强制刷新
     */
    public static void setStatusBarDarkIcon(Activity activity, boolean dark, boolean forceUpdate) {
        setStatusBarDarkIcon(activity, dark, forceUpdate, true);
    }


    private static boolean changeMeizuFlag(WindowManager.LayoutParams winParams, String flagName, boolean on) {
        try {
            Field f = winParams.getClass().getDeclaredField(flagName);
            f.setAccessible(true);
            int bits = f.getInt(winParams);
            Field f2 = winParams.getClass().getDeclaredField("meizuFlags");
            f2.setAccessible(true);
            int meizuFlags = f2.getInt(winParams);
            int oldFlags = meizuFlags;
            if (on) {
                meizuFlags |= bits;
            } else {
                meizuFlags &= ~bits;
            }
            if (oldFlags != meizuFlags) {
                f2.setInt(winParams, meizuFlags);
                return true;
            }
        } catch (Exception e) {
            ZLog.i(TAG, "printStackTrace:" + e.getMessage());
        }
        return false;
    }

    /**
     * 设置状态栏颜色
     *
     * @param view
     * @param dark
     */
    private static void setStatusBarDarkIcon(View view, boolean dark) {
        int oldVis = view.getSystemUiVisibility();
        int newVis = oldVis;
        if (dark) {
            newVis |= 0x00002000;
        } else {
            newVis &= 0x00002000;
        }
        if (newVis != oldVis) {
            view.setSystemUiVisibility(newVis);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param window
     * @param color
     */
    private static void setStatusBarColor(Window window, int color) {
        WindowManager.LayoutParams winParams = window.getAttributes();
        if (mStatusBarColorFiled != null) {
            try {
                int oldColor = mStatusBarColorFiled.getInt(winParams);
                if (oldColor != color) {
                    mStatusBarColorFiled.set(winParams, color);
                    window.setAttributes(winParams);
                }
            } catch (Exception e) {
                ZLog.i(TAG, "printStackTrace:" + e.getMessage());
            }
        }
    }

    /**
     * 设置状态栏字体图标颜色(只限全屏非activity情况)
     *
     * @param window 当前窗口
     * @param dark   是否深色 true为深色 false 为白色
     */
    public static void setStatusBarDarkIcon(Window window, boolean dark) {
        if (Build.VERSION.SDK_INT < 23) {
            changeMeizuFlag(window.getAttributes(), "MEIZU_FLAG_DARK_STATUS_BAR_ICON", dark);
        } else {
            View decorView = window.getDecorView();
            if (decorView != null) {
                setStatusBarDarkIcon(decorView, dark);
                setStatusBarColor(window, 0);
            }
        }
    }

    private static void setStatusBarDarkIcon(Activity activity, boolean dark, boolean forceUpdate, boolean flag) {
        if ((!dark ^ mStatusBarDark) && !forceUpdate) {
            return;
        }
        mStatusBarDark = dark;
        if (mSetStatusBarDarkIcon != null) {
            try {
                mSetStatusBarDarkIcon.invoke(activity, dark);
            } catch (Exception e) {
                ZLog.i(TAG, "printStackTrace:" + e.getMessage());
            }
        } else {
            if (flag) {
                setStatusBarDarkIcon(activity.getWindow(), dark);
            }
        }
    }
}
