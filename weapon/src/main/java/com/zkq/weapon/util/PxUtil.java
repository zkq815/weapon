package com.zkq.weapon.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by huxiaoyuan on 16/3/21.
 */
public class PxUtil {
    public static int dp2px(final float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static float sp2px(int size, Context context) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, size, context.getResources().getDisplayMetrics());
    }

}
