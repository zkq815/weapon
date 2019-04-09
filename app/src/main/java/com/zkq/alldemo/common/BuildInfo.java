package com.zkq.alldemo.common;

import com.zkq.alldemo.BuildConfig;

/**
 * @author zkq
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
    public static String CHANNEL = BuildConfig.CHANNEL;
}
