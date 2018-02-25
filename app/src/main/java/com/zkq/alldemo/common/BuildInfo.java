package com.zkq.alldemo.common;


import com.zkq.alldemo.BuildConfig;

/**
 * @author yc
 * @see <a href="https://github.com/Tencent/tinker/wiki/Tinker-%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98#tinker%E7%9A%84%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5">Tinker的最佳实践</a>
 * @since 2017/6/1
 */
public class BuildInfo {

    public static boolean DEBUG = BuildConfig.DEBUG;
    public static String APPLICATION_ID = BuildConfig.APPLICATION_ID;
    public static String BUILD_TYPE = BuildConfig.BUILD_TYPE;
    public static String FLAVOR = BuildConfig.FLAVOR;
    public static int VERSION_CODE = BuildConfig.VERSION_CODE;
    public static String VERSION_NAME = BuildConfig.VERSION_NAME;
    public static boolean LOG_DEBUG = BuildConfig.LOG_DEBUG;
//    public static String CHANNEL = BuildConfig.CHANNEL;
//
//    public static String TINKER_ID = BuildConfig.TINKER_ID;
//    public static boolean TINKER_ENABLED = BuildConfig.TINKER_ENABLED;
}
