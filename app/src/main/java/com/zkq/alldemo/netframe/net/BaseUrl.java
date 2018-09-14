package com.zkq.alldemo.netframe.net;

/**
 * Created by zkq
 * on 2018/8/27.
 * 配置不同环境下的域名或者ip地址
 */

public class BaseUrl {

    public static final boolean IS_DEV = false; // 开发环境
    public static final boolean IS_QA = false;   // 测试环境
    public static final boolean IS_PRE = false; // 预发环境

    public static final String DEV_URL = "http://lists.meizu.com/";            //list
    public static final String QA_URL = "http://app.store.res.meizu.com/";     //官网
    public static final String PRE_URL = "http://app.store.res.meizu.com/";    //预发url
    public static final String RELEASE_URL = "http://app.store.res.meizu.com/";//正式url


    public static String getBaseUrl() {
        if (IS_DEV) {
            return DEV_URL;
        } else if (IS_QA) {
            return QA_URL;
        } else if (IS_PRE) {
            return PRE_URL;
        } else {
            return RELEASE_URL;
        }
    }

}
