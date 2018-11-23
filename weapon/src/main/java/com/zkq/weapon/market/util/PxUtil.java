package com.zkq.weapon.market.util;

import android.util.TypedValue;

import com.zkq.weapon.application.BaseApplication;

/**
 * @author:zkq
 * create:2018/11/14 10:09 AM
 * email:zkq815@126.com
 * desc: dp、px、sp转换工具
 */
public class PxUtil {
    public static int dp2px(final float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                BaseApplication.getInstance().getResources().getDisplayMetrics());
    }

    public static float sp2px(int size) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size,
                BaseApplication.getInstance().getResources().getDisplayMetrics());
    }

}
