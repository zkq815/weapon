package com.zkq.alldemo.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

import com.zkq.alldemo.MApplication.MyApplication;
import com.zkq.alldemo.R;


/**
 * toast，统一规范
 *
 * @author
 * @since 16/9/8
 */
public class ZKQToast {

    public static void show(final String info) {
        final Context context = MyApplication.getInstance();
        final int offset = context.getResources().getDimensionPixelSize(R.dimen.toast_bottom_distance);
        final Toast toast = Toast.makeText(context, info, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, offset);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void show(@StringRes final int resId) {
        final Context context = MyApplication.getInstance();
        final int offset = context.getResources().getDimensionPixelSize(R.dimen.toast_bottom_distance);
        final Toast toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, offset);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showShort(final String info) {
        final Context context = MyApplication.getInstance();
        final int offset = context.getResources().getDimensionPixelSize(R.dimen.toast_bottom_distance);
        final Toast toast = Toast.makeText(context, info, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, offset);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showShort(@StringRes final int resId) {
        final Context context = MyApplication.getInstance();
        final int offset = context.getResources().getDimensionPixelSize(R.dimen.toast_bottom_distance);
        final Toast toast = Toast.makeText(context, resId,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, offset);
        toast.setDuration(Toast.LENGTH_LONG);

        toast.show();
    }

    public static void show(final String info, @DrawableRes final int resId) {
        final Context context = MyApplication.getInstance();
        final int offset = context.getResources().getDimensionPixelSize(R.dimen.toast_bottom_distance);
        final Toast toast = Toast.makeText(context, info, resId);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, offset);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void show(@StringRes final int strId, @DrawableRes final int resId) {
        final Context context = MyApplication.getInstance();
        final int offset = context.getResources().getDimensionPixelSize(R.dimen.toast_bottom_distance);
        final Toast toast = Toast.makeText(context, strId, resId);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, offset);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showShort(final String info, @DrawableRes final int resId) {
        final Context context = MyApplication.getInstance();
        final int offset = context.getResources().getDimensionPixelSize(R.dimen.toast_bottom_distance);
        final Toast toast = Toast.makeText(context, info,resId);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, offset);
        toast.show();
    }

    public static void showShort(@StringRes final int strId, @DrawableRes final int resId) {
        final Context context = MyApplication.getInstance();
        final int offset = context.getResources().getDimensionPixelSize(R.dimen.toast_bottom_distance);
        final Toast toast = Toast.makeText(context, strId, resId);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, offset);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 普通toast
     * */
    public static void toast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * toast显示在界面中间
     * @param activity  上下文
     * @param str       显示信息
     * @author zkq
     * create at 2016/11/15 16:20
     */
    public static void toastInCenterWithActvity(final Activity activity, final String str) {
        if(activity == null)
            return;
        final Toast toast = Toast.makeText(activity,str,Toast.LENGTH_LONG);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast.setGravity(Gravity.CENTER, 0, 0);
                } else {
                    toast.setText(str);
                }
                toast.show();
            }
        });
    }

    /**
     * toast显示在界面中间
     * @param context   上下文
     * @param str       显示信息
     * @author zkq
     * create at 2016/11/15 16:23
     */
    public static void toastInCenterWithContext(final Context context, final String str) {
        if(context == null)
            return;
        final Toast toast = Toast.makeText(context,str,Toast.LENGTH_LONG);

        if (toast == null) {
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

}
