package com.zkq.weapon.util;

import android.util.Log;

import com.zkq.weapon.BuildConfig;


public class ZKQLog {
    /**
     * 统一的标签
     */
    private final static String TAG = "ZKQDemo";

    private static final boolean isLog = BuildConfig.LOG_DEBUG;

    public static void d(Object tag, Object msg) {
        if (isLog)
            Log.d(tag.toString(), msg.toString());
    }

    public static void d(Object msg) {
        if (isLog)
            Log.d(getClassName(), msg.toString());
    }

    public static void v(Object tag, Object msg) {
        if (isLog)
            Log.v(tag.toString(), msg.toString());
    }

    public static void v(Object msg) {
        if (isLog)
            Log.v(getClassName(), msg.toString());
    }

    public static void i(Object tag, Object msg) {
        if (isLog)
            Log.i(tag.toString(), msg.toString());
    }

    public static void i(Object msg) {
        if (isLog)
            Log.i(getClassName(), msg.toString());
    }

    public static void w(Object tag, Object msg) {
        if (isLog)
            Log.w(tag.toString(), msg.toString());
    }

    public static void w(Object msg) {
        if (isLog)
            Log.w(getClassName(), msg.toString());
    }

    public static void e(Object tag, Object msg) {
        if (isLog)
            Log.e(tag.toString(), msg.toString());
    }

    public static void e(Object msg) {
        if (isLog)
            Log.e(getClassName(), msg.toString());
    }

    public static void t(Object msg, Throwable t) {
        if (isLog) {
            Log.e(getClassName(), msg.toString(), t);
        }
    }

    public static void t(Object tag, Object msg, Throwable throwable) {
        if (isLog) {
            Log.e(tag.toString(), msg.toString(), throwable);
        }
    }

    private static String getClassName() {
        String result;
        // 这里的数组的index2是根据你工具类的层级做不同的定义，这里仅仅是关键代码
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }

    /**
     * WARNING日志，显示为橘色
     *
     * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
     * @param content 日志内容
     */
    public static void w(String tag, String content) {
        if (content == null)
            content = "null";

        if (isLog)
            Log.w(tag, content);
    }

    /**
     * ERROR日志，显示为红色
     *
     * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
     * @param content 日志内容
     */
    public static void e(String tag, String content) {
        if (content == null)
            content = "null";

        if (isLog)
            Log.e(tag, content);
    }

    /**
     * VERBOSE日志，显示为黑色
     *
     * @param content 日志内容
     */
    public static void v(String content) {
        v(TAG, content);
    }

    /**
     * INFO日志，显示为绿色
     *
     * @param content 日志内容
     */
    public static void i(String content) {
        i(TAG, content);
    }

    /**
     * DEBUG日志，显示为蓝色
     *
     * @param content 日志内容
     */
    public static void d(String content) {
        d(TAG, content);
    }

    /**
     * WARNING日志，显示为橘色
     *
     * @param content 日志内容
     */
    public static void w(String content) {
        w(TAG, content);
    }

    /**
     * ERROR日志，显示为红色
     *
     * @param content 日志内容
     */
    public static void e(String content) {
        e(TAG, content);
    }

    /**
     * INFO日志，显示为绿色
     *
     * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
     * @param content 日志内容
     */
    public static void i(String tag, String content) {
        if (content == null)
            content = "null";

        if (isLog)
            Log.i(tag, content);
    }

    /**
     * DEBUG日志，显示为蓝色
     *
     * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
     * @param content 日志内容
     */
    public static void d(String tag, String content) {
        if (content == null)
            content = "null";

        if (isLog)
            Log.d(tag, content);
    }



}
