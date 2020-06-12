package com.zkq.weapon.market.tools;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author zkq
 * create:2018/12/11 10:00 AM
 * email:zkq815@126.com
 * desc: 网络工具类
 */
public interface ToolNet {

    /** Unknown network class. {@hide} */
    int NETWORK_CLASS_UNKNOWN = 0;
    /** Class of broadly defined "2G" networks. {@hide} */
    int NETWORK_CLASS_2_G = 2;
    /** Class of broadly defined "3G" networks. {@hide} */
    int NETWORK_CLASS_3_G = 3;
    /** Class of broadly defined "4G" networks. {@hide} */
    int NETWORK_CLASS_4_G = 4;
    /** Network type is wifi */
    int NETWORK_CLASS_WIFI = 1;

    /** Network type is wifi */
    int NETWORK_TYPE_WIFI = -1;

    /** Network type is unknown */
    int NETWORK_TYPE_UNKNOWN = 0;
    /** Current network is GPRS */
    int NETWORK_TYPE_GPRS = 1;
    /** Current network is EDGE */
    int NETWORK_TYPE_EDGE = 2;
    /** Current network is UMTS */
    int NETWORK_TYPE_UMTS = 3;
    /** Current network is CDMA: Either IS95A or IS95B*/
    int NETWORK_TYPE_CDMA = 4;
    /** Current network is EVDO revision 0*/
    int NETWORK_TYPE_EVDO_0 = 5;
    /** Current network is EVDO revision A*/
    int NETWORK_TYPE_EVDO_A = 6;
    /** Current network is 1xRTT*/
    int NETWORK_TYPE_1xRTT = 7;
    /** Current network is HSDPA */
    int NETWORK_TYPE_HSDPA = 8;
    /** Current network is HSUPA */
    int NETWORK_TYPE_HSUPA = 9;
    /** Current network is HSPA */
    int NETWORK_TYPE_HSPA = 10;
    /** Current network is iDen */
    int NETWORK_TYPE_IDEN = 11;
    /** Current network is EVDO revision B*/
    int NETWORK_TYPE_EVDO_B = 12;
    /** Current network is LTE */
    int NETWORK_TYPE_LTE = 13;
    /** Current network is eHRPD */
    int NETWORK_TYPE_EHRPD = 14;
    /** Current network is HSPA+ */
    int NETWORK_TYPE_HSPAP = 15;
    /** Current network is GSM {@hide} */
    int NETWORK_TYPE_GSM = 16;
    /** Current network is TD_SCDMA {@hide} */
    int NETWORK_TYPE_TD_SCDMA = 17;
    /** Current network is IWLAN {@hide} */
    int NETWORK_TYPE_IWLAN = 18;



    /**
     * 网络是否可用
     * (开启飞行模式或未开启移动网络和wifi等情况不可用)
     *
     * @param ctx 上下文
     * @return true 可用
     */
    static boolean isAvailable(@NonNull Context ctx) {
        NetworkInfo netWorkInfo = getActiveNetWorkInfo(ctx);
        return netWorkInfo != null && netWorkInfo.isAvailable();
    }

    /**
     * 网络是否连接
     *
     * @param ctx 上下文
     * @return true 已连接
     */
    static boolean isConnected(@NonNull Context ctx) {
        NetworkInfo netWorkInfo = getActiveNetWorkInfo(ctx);
        return netWorkInfo != null && netWorkInfo.isConnected();
    }

    /**
     * Wifi是否连接
     *
     * @param ctx 上下文
     * @return true 已连接
     */
    static boolean isWifiConnected(@NonNull Context ctx) {
        NetworkInfo netWorkInfo = getActiveNetWorkInfo(ctx);
        return netWorkInfo != null && netWorkInfo.isConnected() && netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 移动网络是否连接
     *
     * @param ctx 上下文
     * @return true 已连接
     */
    static boolean isMobileConnected(@NonNull Context ctx) {
        NetworkInfo netWorkInfo = getActiveNetWorkInfo(ctx);
        return netWorkInfo != null && netWorkInfo.isConnected() && netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 获取移动网路运营商名称
     *
     * @param ctx 上下文
     * @return 运营商名称
     */
    @Nullable
    static String getNetWorkOperatorName(@NonNull Context ctx) {
        TelephonyManager telephonyManager = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager == null ? "" : telephonyManager.getNetworkOperatorName();
    }

    /**
     * 打开网络设置界面
     * 打开设置界面
     *
     * @param ctx 上下文
     */
    static void openWirelessSettings(@NonNull Context ctx) {
        try {
            ctx.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取网络信息
     * 需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @param ctx 上下文
     * @return 网络信息类
     */
    @SuppressLint("MissingPermission")
    @Nullable
    static NetworkInfo getActiveNetWorkInfo(@NonNull Context ctx) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                return connectivityManager.getActiveNetworkInfo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    static int getNetworkType(Context context) {
        if (isWIFI(context)) {
            return NETWORK_TYPE_WIFI;
        }
        PackageManager pm = context.getPackageManager();
        if ((pm.checkPermission(permission.READ_PHONE_STATE,
                context.getPackageName())) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager mTelephonyMgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return mTelephonyMgr.getNetworkType();
        }
        return NETWORK_TYPE_UNKNOWN;
    }

    /**
     * get Network Class.
     */
    static int getNetworkClass(Context context) {
        if (isWIFI(context)) {
            return NETWORK_CLASS_WIFI;
        }
        return getNetworkClass(getNetworkType(context));
    }

    /**
     * Return general class of network type, such as "3G" or "4G". In cases
     * where classification is contentious, this method is conservative.
     */
    static int getNetworkClass( int networkType) {
        switch (networkType) {

            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_GSM:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_1xRTT:
            case NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
            case NETWORK_TYPE_TD_SCDMA:
                return NETWORK_CLASS_3_G;
            case NETWORK_TYPE_LTE:
            case NETWORK_TYPE_IWLAN:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    static boolean is3G4GWiFi(Context context){
        if(getNetworkClass(context)>=2){
            return true;
        }else{
            return false;
        }
    }

    static boolean isWIFI(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == State.CONNECTED) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Convert byte array to hex string
     */
    static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for(int idx=0; idx < bytes.length; idx++) {
            int intVal = bytes[idx] & 0xff;
            if (intVal < 0x10) {
                sbuf.append("0");
            }
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    /**
     * Returns MAC address of the given interface name.
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return  mac address or empty string
     */
    static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)){
                        continue;
                    }
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac==null){
                    return "";
                }
                StringBuilder buf = new StringBuilder();
                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
    }

    /**
     * Get IP address from first non-localhost interface
     * @param useIPv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4) {
                                return sAddr;
                            }
                        } else {
                            if (!isIPv4) {
                                // drop ip6 zone suffix
                                int delim = sAddr.indexOf('%');
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
    }

    /**
     * 检查当前网络是否可用
     *
     * @param activity
     * @return
     */
    static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
