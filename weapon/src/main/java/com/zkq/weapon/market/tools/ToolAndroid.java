package com.zkq.weapon.market.tools;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.zkq.weapon.market.util.ZLog;

import java.io.File;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * @author zkq
 * time: 2018/12/11:14:54
 * email: zkq815@126.com
 * desc: android 系统工具类
 */
public interface ToolAndroid {

    int TEMP_LEN = 32;

    String EMPTY = "111111111111111";

    /**
     * 获取魅族系统操作版本标识
     */
    static boolean isMeizuFlymeOS() {
        String meizuFlymeOSFlag = getSystemProperty("ro.build.display.id", "");
        if (null == meizuFlymeOSFlag) {
            return false;
        } else if (meizuFlymeOSFlag.contains("flyme") || meizuFlymeOSFlag.toLowerCase().contains("flyme")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获版MIUI标识
     */
    static boolean isMIUIOS() {
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

    static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
    }

    static WifiManager getWifiManager(@NonNull final Context context) {
        return (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    static PackageManager getPackageManager(Context context) {
        try {
            return context.getPackageManager();
        } catch (Exception ignored) {

        }
        return null;
    }

    static NetworkInfo getActiveNetworkType(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return null;
        }

        NetworkInfo activeInfo = connectivity.getActiveNetworkInfo();
        if (activeInfo == null) {
            return null;
        }
        return activeInfo;
    }

    static String getBrand() {
        try {
            return (Build.BRAND.length() > TEMP_LEN ? Build.BRAND.substring(0,
                    TEMP_LEN) : Build.BRAND).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    static String getModel() {
        try {
            return (Build.MODEL.length() > TEMP_LEN ? Build.MODEL.substring(0,
                    TEMP_LEN) : Build.MODEL).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    static int getSdkVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    static String getIMSI(Context context) {
        String imsi = null;

        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                imsi = getTelephonyManager(context).getSubscriberId();
                return imsi;
            }
        } catch (Exception e) {
            ZLog.e("getIMSI", e);
        }

        if (TextUtils.isEmpty(imsi)) {
            return EMPTY;
        }
        return imsi;
    }

    static String getIMEI(Context context) {
        String imei = null;

        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                imei = getTelephonyManager(context).getDeviceId();
                return imei;
            }
        } catch (Exception e) {
            ZLog.e("", e);
        }

        if (TextUtils.isEmpty(imei)) {
            return EMPTY;
        }
        if (imei.length() > 15) {
            return imei.substring(imei.length() - 15);
        }
        return imei;
    }

    static GsmCellLocation getGsmCellLocation() {
        CellLocation cell = CellLocation.getEmpty();
        return (GsmCellLocation) cell;
    }

    static char getLac() {
        try {
            return (char) getGsmCellLocation().getLac();
        } catch (Exception e) {
            return '0';
        }

    }

    static char getCellID() {
        try {
            return (char) getGsmCellLocation().getCid();
        } catch (Exception e) {
            return 1234;
        }

    }

    static String getMacAddress(Context context) {
        WifiManager wifiManager = getWifiManager(context);
        if (wifiManager != null) {
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                return info.getMacAddress();
            }
        }
        return "";

    }

    static int getMcc(Context context) {
        return context.getResources().getConfiguration().mcc;
    }

    static int getMnc(Context context) {
        return context.getResources().getConfiguration().mnc;
    }

    static int getWidthPixels(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    static int getHeightPixels(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    static String getPhoneId(Context context) {
        try {
            return Settings.Secure
                    .getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            return "";
        }
    }

    static int getMemSize() {
        try {
            File root = Environment.getDataDirectory();
            StatFs sf = new StatFs(root.getPath());
            return (int) ((long) sf.getBlockCount() * (long) sf.getBlockSize() / (1024 * 1024));

        } catch (Exception ignored) {

        }
        return -1;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context 上下文
     * @return 是否有虚拟按键
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sNavBarOverride;
    }

    /**
     * 判断手机上有没有安装某一应用
     *
     * @param packageName 应用包名
     * @return true已安装   false未安装
     */

    static boolean isAppInstalled(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 隐藏软键盘
     */
    static void hideSoftKeyboard(Activity activity) {
        ((InputMethodManager) activity.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取手机号码
     * @param context
     * @return
     */
    static String getPhoneNumber(Context context) {
        TelephonyManager phoneManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        if (phoneManager != null) {
            String ret = phoneManager.getLine1Number();
            if (ret != null && ret.length() > 0) {
                return ret;
            }
        }

        return null;
    }

}
