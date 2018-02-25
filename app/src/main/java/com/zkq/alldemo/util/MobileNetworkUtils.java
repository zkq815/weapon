package com.zkq.alldemo.util;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * 获取网络状态工具类
 */
public final class MobileNetworkUtils {

	/** Unknown network class. {@hide} */
	public static final int NETWORK_CLASS_UNKNOWN = 0;
	/** Class of broadly defined "2G" networks. {@hide} */
	public static final int NETWORK_CLASS_2_G = 2;
	/** Class of broadly defined "3G" networks. {@hide} */
	public static final int NETWORK_CLASS_3_G = 3;
	/** Class of broadly defined "4G" networks. {@hide} */
	public static final int NETWORK_CLASS_4_G = 4;
	/** Network type is wifi */
	public static final int NETWORK_CLASS_WIFI = 1;

	/** Network type is wifi */
	public static final int NETWORK_TYPE_WIFI = -1;

	/** Network type is unknown */
	public static final int NETWORK_TYPE_UNKNOWN = 0;
	/** Current network is GPRS */
	public static final int NETWORK_TYPE_GPRS = 1;
	/** Current network is EDGE */
	public static final int NETWORK_TYPE_EDGE = 2;
	/** Current network is UMTS */
	public static final int NETWORK_TYPE_UMTS = 3;
	/** Current network is CDMA: Either IS95A or IS95B*/
	public static final int NETWORK_TYPE_CDMA = 4;
	/** Current network is EVDO revision 0*/
	public static final int NETWORK_TYPE_EVDO_0 = 5;
	/** Current network is EVDO revision A*/
	public static final int NETWORK_TYPE_EVDO_A = 6;
	/** Current network is 1xRTT*/
	public static final int NETWORK_TYPE_1xRTT = 7;
	/** Current network is HSDPA */
	public static final int NETWORK_TYPE_HSDPA = 8;
	/** Current network is HSUPA */
	public static final int NETWORK_TYPE_HSUPA = 9;
	/** Current network is HSPA */
	public static final int NETWORK_TYPE_HSPA = 10;
	/** Current network is iDen */
	public static final int NETWORK_TYPE_IDEN = 11;
	/** Current network is EVDO revision B*/
	public static final int NETWORK_TYPE_EVDO_B = 12;
	/** Current network is LTE */
	public static final int NETWORK_TYPE_LTE = 13;
	/** Current network is eHRPD */
	public static final int NETWORK_TYPE_EHRPD = 14;
	/** Current network is HSPA+ */
	public static final int NETWORK_TYPE_HSPAP = 15;
	/** Current network is GSM {@hide} */
	public static final int NETWORK_TYPE_GSM = 16;
	/** Current network is TD_SCDMA {@hide} */
	public static final int NETWORK_TYPE_TD_SCDMA = 17;
	/** Current network is IWLAN {@hide} */
	public static final int NETWORK_TYPE_IWLAN = 18;


	public static boolean isNetAvailable(Context context) {
		Object connManager = context == null ? null : context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager == null ? null
				: ((ConnectivityManager) connManager).getActiveNetworkInfo();
		if (networkInfo == null || networkInfo.getState() != State.CONNECTED)
			return false;
		return true;
	}

	public static int getNetworkType(Context context) {
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
	public static int getNetworkClass(Context context) {
		if (isWIFI(context)) {
			return NETWORK_CLASS_WIFI;
		}
		return getNetworkClass(getNetworkType(context));
	}

	/**
	 * Return general class of network type, such as "3G" or "4G". In cases
	 * where classification is contentious, this method is conservative.
	 */
	private static int getNetworkClass( int networkType) {
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

	public static boolean is3G4GWiFi(Context context){
		 if(getNetworkClass(context)>=2){
			 return true;
		 }else{
			 return false;
		 }
	}

	public static boolean isWIFI(Context context) {
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

	public static String getPhoneNumber(Context context) {
		TelephonyManager phoneManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		if (phoneManager != null) {
			String ret = phoneManager.getLine1Number();
			if (ret != null && ret.length() > 0)
				return ret;
		}

		return null;
	}
	/**
	 * Convert byte array to hex string
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuilder sbuf = new StringBuilder();
		for(int idx=0; idx < bytes.length; idx++) {
			int intVal = bytes[idx] & 0xff;
			if (intVal < 0x10) sbuf.append("0");
			sbuf.append(Integer.toHexString(intVal).toUpperCase());
		}
		return sbuf.toString();
	}





	/**
	 * Returns MAC address of the given interface name.
	 * @param interfaceName eth0, wlan0 or NULL=use first interface
	 * @return  mac address or empty string
	 */
	public static String getMACAddress(String interfaceName) {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				if (interfaceName != null) {
					if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
				}
				byte[] mac = intf.getHardwareAddress();
				if (mac==null) return "";
				StringBuilder buf = new StringBuilder();
				for (int idx=0; idx<mac.length; idx++)
					buf.append(String.format("%02X:", mac[idx]));
				if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
				return buf.toString();
			}
		} catch (Exception ignored) { } // for now eat exceptions
		return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
	}

	/**
	 * Get IP address from first non-localhost interface
	 * @param useIPv4  true=return ipv4, false=return ipv6
	 * @return  address or empty string
	 */
	public static String getIPAddress(boolean useIPv4) {
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
							if (isIPv4)
								return sAddr;
						} else {
							if (!isIPv4) {
								int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
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
	public static boolean isNetworkAvailable(Activity activity) {
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
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}


}
