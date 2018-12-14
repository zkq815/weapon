package com.zkq.alldemo.fortest.fingertest;

import android.content.Context;
import android.content.Intent;

/**
 * @author zkq
 * create:2018/12/12 3:48 PM
 * email:zkq815@126.com
 * desc: 跳转到设置页面，设置指纹
 */
public class FingerprintUtil {

    private static final String ACTION_SETTING = "android.settings.SETTINGS";

    public static void openFingerPrintSettingPage(Context context) {
        Intent intent = new Intent(ACTION_SETTING);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }
}
