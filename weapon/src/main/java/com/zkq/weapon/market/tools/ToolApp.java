package com.zkq.weapon.market.tools;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;

import com.zkq.weapon.application.BaseApplication;

import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;

/**
 * @author zkq
 * create:2020/6/12 5:27 PM
 * email:zkq815@126.com
 * desc: app工具类
 */
public interface ToolApp {

    /**
     * 获取全局 Application Context
     */
    @NonNull
    static Context getAppContext() {
        return BaseApplication.getInstance();
    }

    /**
     * 获取 App 系统配置
     */
//    @NonNull
//    static ISystemConfig getConfig() {
//        return MyApplication.getInstance().getAppConfig();
//    }

    /**
     * 获取环境配置
     */
//    @NonNull
//    static EnvHome getEnvHome() {
//        return ToolApp.getConfig().getEnvHome();
//    }

    /**
     * 是否KitKat或更高版本
     *
     * @return true:是
     */
    static boolean isKitKatOrLater() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * 是否 LOLLIPOP 或更高版本
     *
     * @return true：是
     */
    static boolean isLollipopOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 是否UI线程
     *
     * @return true:UI线程
     */
    static boolean isUIThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    /**
     * 生成唯一code
     *
     * @return 唯一code
     */
    static String getUniqueCode() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取App名称
     */
//    static String getApplicationLabel() {
//        Context applicationCtx = ApplicationBase.getInstance().getApplicationContext();
//
//        if (applicationCtx != null) {
//            if (applicationCtx.getPackageManager() != null) {
//                CharSequence label = applicationCtx.getPackageManager()
//                        .getApplicationLabel(applicationCtx.getApplicationInfo());
//                return (label == null) ? ISystemConfig.APP_LABEL_JOLLY_CHIC : label.toString();
//            }
//        }
//
//        return ISystemConfig.APP_LABEL_JOLLY_CHIC;
//    }

    /**
     * 根据Pid获取进程名称
     *
     * @param cxt 上下文
     * @param pid 进程id
     * @return 进程名称
     */
    static String getProcessName(Context cxt, int pid) {
        if (cxt == null) {
            return null;
        }

        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return null;
        }

        List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }

        for (int i = 0; i < runningApps.size(); i++) {
            RunningAppProcessInfo procInfo = runningApps.get(i);
            if (procInfo != null && procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 是否App进程启动
     *
     * @param ctx 上下文
     * @return true:是
     */
    static boolean isMyProcessStartApp(Context ctx) {
        if (ctx == null) {
            return false;
        }

        boolean isStartApp = true;
        String myProcessName = ctx.getPackageName();
        String pidProcessName = getProcessName(ctx, Process.myPid());

        if (!TextUtils.isEmpty(pidProcessName) && !pidProcessName.equals(myProcessName)) {
            isStartApp = false;
        }

        return isStartApp;
    }

    /**
     * 在进程中去寻找当前APP的信息，判断是否在前台运行
     *
     * @param ctx 上下文
     * @return true:当前app正在前台；false：当前app在后台
     */
    static boolean isAppOnForeground(final Context ctx) {
        if (ctx == null) {
            return false;
        }

        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);

        if (tasks == null) {
            return false;
        }

        if (!tasks.isEmpty()) {
            if (tasks.get(0) == null) {
                return false;
            }

            ComponentName topActivity = tasks.get(0).topActivity;

            if (topActivity == null) {
                return false;
            }

            if (ctx.getPackageName().equals(topActivity.getPackageName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取当前app版本信息
     *
     * @param context 上下文
     * @return 当前版本名称
     */
    static String getCurrentVerName(Context context) {
        String verName = "";
        PackageInfo packageInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            ToolException.printStackTrace(ToolApp.class, "getCurrentVerName()", e);
        }

        if (packageInfo != null) {
            verName = packageInfo.versionName;
        }

        return verName;
    }

    /**
     * 获取当前app code 信息
     *
     * @param context 上下文
     * @return 当前版本号
     */
    static int getCurrentVerCode(Context context) {
        int verCode = 0;
        PackageInfo packageInfo = null;

        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            ToolException.printStackTrace(ToolApp.class, "getCurrentVerCode()", e);
        }

        if (packageInfo != null) {
            verCode = packageInfo.versionCode;
        }

        return verCode;
    }

    /**
     * 杀死App
     *
     * @param delayMillis 延时时间，0表示立即杀死，无延时。
     */
    static void killApp(long delayMillis) {
        if (delayMillis <= 0) {
            killAppNow();
        } else {
//            MainHandler.getInstance().postDelayed(ToolApp::killAppNow, delayMillis);
        }
    }

    /**
     * 立刻杀死app
     */
    static void killAppNow() {
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    static void killAllOtherProcess(final Context context) {
        final List<ActivityManager.RunningAppProcessInfo> appProcessList = getProcessInfoList(context);

        if (appProcessList == null) {
            return;
        }

        for (ActivityManager.RunningAppProcessInfo ai : appProcessList) {
            if (ai.uid == android.os.Process.myUid() && ai.pid != android.os.Process.myPid()) {
                android.os.Process.killProcess(ai.pid);
            }
        }

    }

    static void killAllProcess(Context context) {
        killAllOtherProcess(context);

        android.os.Process.killProcess(android.os.Process.myPid());
    }

    static List<ActivityManager.RunningAppProcessInfo> getProcessInfoList(final Context context) {
        final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return null;
        }

        return am.getRunningAppProcesses();
    }

    static ActivityManager.RunningAppProcessInfo getProcessInfo(final Context context) {
        final List<ActivityManager.RunningAppProcessInfo> appProcessList = getProcessInfoList(context);

        if (null != appProcessList) {
            for (ActivityManager.RunningAppProcessInfo ai : appProcessList) {
                if (ai.pid == android.os.Process.myPid()) {
                    return ai;
                }
            }
        }

        return null;
    }

    static String getProcessName(final Context context) {
        final ActivityManager.RunningAppProcessInfo info = getProcessInfo(context);
        if (null == info) {
            return null;
        }

        return info.processName;
    }

    static boolean isMainProcess(final Context context) {
        final ActivityManager.RunningAppProcessInfo info = getProcessInfo(context);
        return null != info && info.processName.equals(context.getPackageName());
    }

    static boolean isUpdateProcess(final Context context) {
        final ActivityManager.RunningAppProcessInfo info = getProcessInfo(context);
        return null != info && info.processName.endsWith(":update");
    }
}
