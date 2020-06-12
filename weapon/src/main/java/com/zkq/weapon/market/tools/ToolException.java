package com.zkq.weapon.market.tools;

import com.zkq.weapon.market.util.ZLog;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import androidx.annotation.NonNull;

/**
 * @author zkq
 * create:2020/6/12 5:29 PM
 * email:zkq815@126.com
 * desc: 异常工具类。此类目的：<br/>
 * 1，规范异常处理。<br/>
 * 2，收集错误日志信息。<br/>
 * 3，在开发期抛出任何可能的异常，便于开发者定位问题。<br/>
 * 4，在正式版本中，会禁止任何异常抛出，减少crash率。<br/>
 */
public interface ToolException {

    // --------------------- print Stack Trace---------------------------

    /**
     * 错误打印
     *
     * @param tag 错误信息标识
     * @param e   异常Exception
     */
    static void printStackTrace(String tag, Throwable e) {
        printStackTraceBase(tag, "", e);
    }

    /**
     * 错误打印
     *
     * @param cls class
     * @param e   异常Exception
     */
    static void printStackTrace(@NonNull Class<?> cls, Throwable e) {
        printStackTraceBase(cls.getSimpleName(), "", e);
    }

    /**
     * 错误打印(默认抛出异常)
     *
     * @param tag 错误信息标识
     * @param msg 错误信息
     * @param e   异常Exception
     */
    static void printStackTrace(String tag, String msg, Throwable e) {
        printStackTraceBase(tag, msg, e);
    }

    /**
     * 错误打印(默认抛出异常)
     *
     * @param cls class
     * @param msg 错误信息
     * @param e   异常Exception
     */
    static void printStackTrace(Class<?> cls, String msg, Throwable e) {
        if (cls != null) {
            printStackTraceBase(cls.getSimpleName(), msg, e);
        }
    }

    /**
     * 错误打印
     *
     * @param tag 错误信息标识
     * @param msg 错误信息
     * @param e   异常Exception
     */
    static void printStackTraceBase(String tag, String msg, Throwable e) {
        if (e != null) {
            e.printStackTrace();
            ZLog.e(tag, "printStackTrace()-> " + ("Msg:" + msg + ", Cause:" + getErrLogMsg(e)));
            throwExceptionOnUIThread("========== 发生异常，终断程序运行! ==========");
        }
    }

    // --------------------- throw Error---------------------------

    /**
     * 非法访问错误
     *
     * @param tag 错误信息标识
     * @param msg 错误信息
     */
    static void throwIllegalAccessError(String tag, String msg) {
        if (msg != null) {
            ZLog.e(tag, "throwIllegalAccessError()-> Msg:" + msg);
            throwExceptionOnUIThread(msg);
        }
    }

    /**
     * 非法状态错误
     *
     * @param tag 错误信息标识
     * @param msg 错误信息
     */
    static void throwIllegalStateExceptionError(String tag, String msg) {
        if (msg != null) {
            ZLog.e(tag, "throwIllegalStateExceptionError()-> Msg:" + msg);
            throwExceptionOnUIThread(msg);
        }
    }

    /**
     * 空错误
     *
     * @param tag 错误信息标识
     * @param msg 错误信息
     */
    static void throwNullPointerExceptionError(String tag, String msg) {
        if (msg != null) {
            ZLog.e(tag, "throwNullPointerExceptionError()-> Msg:" + msg);
            throwExceptionOnUIThread(msg);
        }
    }

    /**
     * 收集Throwable错误日志信息
     *
     * @param e Throwable
     * @return errorMsg
     */
    static String getErrLogMsg(Throwable e) {
        if (e == null) {
            return "";
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();

        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        String crashInfo = writer.toString();
        try {
            printWriter.close();
            writer.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        return crashInfo;
    }

    /**
     * 研发环境，在主线程抛出异常
     *
     * @param errorMsg 错误信息
     */
    static void throwExceptionOnUIThread(@NonNull final String errorMsg) {
//        if (ToolApp.getEnvHome().isSandBoxEnvironment()) {
//            if (ToolApp.isUIThread()) {
//                throw new IllegalAccessError(errorMsg);
//            } else {
//                MainHandler.getInstance().post(() -> {
//                    throw new IllegalAccessError(errorMsg);
//                });
//            }
//        }
    }

    // --------------------- Volly Error---------------------------

    /**
     * 保存Volley通信错误日志(只有在网络正常情况下记录错误信息)
     *
     * @param tag   错误信息标识
     * @param url   通信链接
     * @param error 错误对象
     */
//    static void saveVolleyErrorLog(String tag, String url, VolleyError error) {
//        if (ToolNet.isConnected(ApplicationBase.getInstance())) {
//            String errMsg = getVolleyErrMsg(error);
//
//            if ((error != null) && (error.networkResponse != null)) {
//                // 写入本地文件
//                ZLog.e(tag, "地址通信错误 -> URL:" + url + " , errMsg: " + errMsg);
//                ToolGA.sendCaughtException(tag + "-> " + errMsg);
//            }
//        }
//    }

    /**
     * 获取Volley错误信息
     *
     * @param error volley通信错误对象
     * @return 错误信息
     */
//    static String getVolleyErrMsg(VolleyError error) {
//        if (error == null) {
//            return "VolleyError is null";
//        }
//
//        if (error.networkResponse == null) {
//            return "VolleyErr.netResp Null！, msg:" + error.getMessage();
//        }
//
//        return " modified:" + error.networkResponse.notModified + ", code:" + error.networkResponse.statusCode
//                + ", msg:" + VolleyErrorHelper.getMessage(error, ApplicationBase.getInstance());
//    }

}
