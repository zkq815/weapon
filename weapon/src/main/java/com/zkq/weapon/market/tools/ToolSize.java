package com.zkq.weapon.market.tools;

import android.content.Context;
import androidx.annotation.NonNull;

/**
 * @author zkq
 * create:2018/12/11 10:01 AM
 * email:zkq815@126.com
 * desc: 尺寸工具类
 */
public interface ToolSize {

    /**
     * 将dp值转换为px值
     *
     * @param context context
     * @param dpValue dp值
     * @return 转化后的px值
     */
    static int dp2Px(@NonNull Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px值转换为dp值
     *
     * @param context context
     * @param pxValue px值
     * @return 转化后的dp值
     */
    static int px2dp(@NonNull Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值
     *
     * @param context context
     * @param pxValue px值
     * @return 转化后的sp值
     */
    static int px2sp(@NonNull Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     *
     * @param context context
     * @param spValue sp值
     * @return 转化后的px值
     */
    static int sp2px(@NonNull Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    static int dip2px(@NonNull Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
