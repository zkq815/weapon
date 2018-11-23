package com.zkq.weapon.market.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
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
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * @author zkq
 * create:2018/11/16 10:25 AM
 * email:zkq815@126.com
 * desc:Activity栈
 */
public final class AndrUtils {

    private final static int tempLen = 32;

    private final static String EMPTY = "111111111111111";

    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static WifiManager getWifiManager(@NonNull final Context context) {
        return (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public static PackageManager getPackageManager(Context context) {
        try {
            return context.getPackageManager();
        } catch (Exception ignored) {

        }
        return null;
    }

    public static NetworkInfo getActiveNetworkType(Context context) {
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

    public static int getVersion(Context ctx) {
        try {
            return getPackageManager(ctx).getPackageInfo(ctx.getPackageName(),
                    0).versionCode;

        } catch (Exception ignored) {

        }
        return -1;
    }

    public static String getVersionName(Context ctx) {
        try {
            return getPackageManager(ctx).getPackageInfo(ctx.getPackageName(),
                    0).versionName;

        } catch (Exception ignored) {

        }
        return null;
    }

    public static String getBrand() {
        try {
            return (Build.BRAND.length() > tempLen ? Build.BRAND.substring(0,
                    tempLen) : Build.BRAND).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String getModel() {
        try {
            return (Build.MODEL.length() > tempLen ? Build.MODEL.substring(0,
                    tempLen) : Build.MODEL).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getSdkVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    public static String getIMSI(Context context) {
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

    public static String getIMEI(Context context) {
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

    private static GsmCellLocation getGsmCellLocation() {
        CellLocation cell = CellLocation.getEmpty();
        return (GsmCellLocation) cell;
    }

    public static char getLac() {
        try {
            return (char) getGsmCellLocation().getLac();
        } catch (Exception e) {
            return '0';
        }

    }

    public static char getCellID() {
        try {
            return (char) getGsmCellLocation().getCid();
        } catch (Exception e) {
            return 1234;
        }

    }

    public static String getMacAddress(Context context) {
        WifiManager wifiManager = getWifiManager(context);
        if (wifiManager != null) {
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                return info.getMacAddress();
            }
        }
        return "";

    }

    public static int getMcc(Context context) {
        return context.getResources().getConfiguration().mcc;
    }

    public static int getMnc(Context context) {
        return context.getResources().getConfiguration().mnc;
    }

    public static int getWidthPixels(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeightPixels(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static String getPhoneId(Context context) {
        try {
            return Secure
                    .getString(context.getContentResolver(), Secure.ANDROID_ID);
        } catch (Exception e) {
            return "";
        }
    }

//    public static int getRamSize() {
//        BufferedReader br = null;
//        try {
//            FileReader fr = new FileReader("/proc/meminfo");
//            br = new BufferedReader(fr);
//            String line = br.readLine();
//            String[] arrayOfString = line.split("\\s+");
//            return Integer.valueOf(arrayOfString[1]) / 1024;
//        } catch (Exception ignored) {
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException ignored) {
//                }
//            }
//        }
//        return -1;
//    }

    public static int getMemSize() {
        try {
            File root = Environment.getDataDirectory();
            StatFs sf = new StatFs(root.getPath());
            return (int) ((long) sf.getBlockCount() * (long) sf.getBlockSize() / (1024 * 1024));

        } catch (Exception ignored) {

        }
        return -1;
    }

    //获取虚拟按键的高度
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
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
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStateBarHeight(Context context) {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    /**
     * 判断手机上有没有安装某一应用
     *
     * @param packageName 应用包名
     * @return true已安装   false未安装
     * */

    public static boolean isAppInstalled(Context context,String packageName){
        PackageInfo packageInfo;
        try{
            packageInfo = context.getPackageManager().getPackageInfo(packageName,0);
        }catch (PackageManager.NameNotFoundException e){
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo!=null;
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1){
                    hexString.append("0");
                }
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取手机的屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取手机的屏幕宽度
     */
    public static int getPhoneWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 像素转dp
     */
    public static int pxToDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp转像素
     */
    public static int dpToPx(int dp, Context context) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyboard(Activity activity) {
        ((InputMethodManager) activity.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取当前版本号
     */
    public static String getVersion(Activity activity) {
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

}
