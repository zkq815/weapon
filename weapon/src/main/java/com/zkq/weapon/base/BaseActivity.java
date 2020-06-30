package com.zkq.weapon.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tmall.wireless.tangram.TangramEngine;
import com.zkq.weapon.R;
import com.zkq.weapon.constants.WeaponConstants;
import com.zkq.weapon.market.tools.ToolActivity;
import com.zkq.weapon.market.tools.ToolAndroid;
import com.zkq.weapon.market.tools.ToolNet;
import com.zkq.weapon.market.tools.ToolString;
import com.zkq.weapon.market.util.ZLog;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @author zkq
 * create:2018/11/16 10:10 AM
 * email:zkq815@126.com
 * desc: Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    static final int STATEBAR_HIDE = 0;
    static final int STATEBAR_TRANSPARENT = 1;
    static final int STATEBAR_SHOW = 2;
    public TangramEngine mEngine;
    private NetReceiver mNetReceiver;
    private boolean mConnected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
        setStatusBarDark();
        setStateBar(2, R.color.blue_beika);
        showActionBar(true, true,"");
        mConnected = ToolNet.isAvailable(this);
    }

    protected void showActionBar(boolean isShow, boolean isShowBack,String title) {
        if (isShow) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBack);
            title = ToolString.isEmptyOrNull(title) ? getClass().getSimpleName() : title;
            getSupportActionBar().setTitle(title);
            getSupportActionBar().show();
        } else {
            getSupportActionBar().hide();
        }
    }

    protected void isShowBack(boolean isShowBack){
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBack);
    }

    protected void setStateBar(int flagCode, int color) {

        //View.SYSTEM_UI_FLAG_VISIBLE：显示状态栏，Activity不全屏显示(恢复到有状态的正常情况)。
        //View.INVISIBLE：隐藏状态栏，同时Activity会伸展全屏显示。
        //View.SYSTEM_UI_FLAG_FULLSCREEN：Activity全屏显示，且状态栏被隐藏覆盖掉。
        //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
        //View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        //View.SYSTEM_UI_LAYOUT_FLAGS：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        //View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：隐藏虚拟按键(导航栏)。有些手机会用虚拟按键来代替物理按键。
        //View.SYSTEM_UI_FLAG_LOW_PROFILE：状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。

        switch (flagCode) {
            //状态栏隐藏
            case STATEBAR_HIDE:
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                        , WindowManager.LayoutParams.FLAG_FULLSCREEN);
                color = color == 0 ? R.color.blue_beika : color;
                getWindow().setStatusBarColor(getResources().getColor(color));
                break;
            //状态栏透明
            case STATEBAR_TRANSPARENT:
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                break;
            //状态栏正常显示
            case STATEBAR_SHOW:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().setStatusBarColor(getResources().getColor(R.color.blue_beika));
                break;
            default:
                break;
        }

    }

    protected boolean isActivityForeground(){
        return ToolActivity.isForeground(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ensureNetReceiver();

        final boolean connected = ToolNet.isAvailable(this);
        if (mConnected != connected) {
            notifyNetChange(connected, false);
        }
    }

    @Override
    protected void onPause() {
        if (null != mNetReceiver) {
            unregisterReceiver(mNetReceiver);
        }
        mNetReceiver = null;

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(null != mEngine){
            // 退出的时候销毁 engine
            mEngine.destroy();
        }
        mNetChangeListeners.clear();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * flyme系统中关闭沉浸式通知栏，通知栏字体图标、背景都为白色
     */
    private void setStatusBarDark() {
        Window window = getWindow();
        //魅族系统
        if (ToolAndroid.isMeizuFlymeOS()) {
            meizuChangeStatusBar(window);
            //[4.4--6.0)系统
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M &&
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                meizuChangeStatusBar(window);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //6.0以后的系统
                androidLolChangeStatusBar(window);
            }
        } else if (ToolAndroid.isMIUIOS()) {
            //MIUI系统
            xiaomiChangeStatusBar(window);
        } else {//其他系统
            androidMChangeStatusBar(window);
        }
    }

    /**
     * 魅族系统更新状态栏
     */
    private void meizuChangeStatusBar(Window window) {
        try {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField(WeaponConstants.MEIZU_FLAG_DARK_STATUS_BAR_ICON);
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField(WeaponConstants.MEIZU_FLAGS);
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            value |= bit;
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            ZLog.e(e.getStackTrace());
        }
    }

    /**
     * 小米系统更新状态栏
     */
    private void xiaomiChangeStatusBar(Window window) {
        Class<? extends Window> clazz = getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName(WeaponConstants.XIAOMI_LAYOUTPARAMS);
            Field field = layoutParams.getField(WeaponConstants.XIAOMI_FLAGS_DARK);
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
        } catch (Exception e) {
            ZLog.e(e.getStackTrace());
        }
    }

    /**
     * androidLol更新状态栏
     */
    private void androidLolChangeStatusBar(Window window) {
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.grey_e5));
        //全屏显示
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //设置系统状态栏处于可见状态，android6.0以后可以对状态栏文字颜色和图标进行修改,设置状态栏文字为深色
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //设置状态栏文字为浅色
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    /**
     * androidM更新状态栏
     */
    private void androidMChangeStatusBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//[4.4--5.0)系统
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//[5.0--)系统
            androidLolChangeStatusBar(window);
        }
    }

    /**
     * 接收网络变化广播
     */
    private class NetReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final boolean connected = ToolNet.isAvailable(context);
            notifyNetChange(connected, isInitialStickyBroadcast());
        }
    }

    public interface INetChange {
        void onNetChange(final boolean connected, boolean isInitialSticky);
    }

    private final List<INetChange> mNetChangeListeners = new ArrayList<>();

    private void notifyNetChange(final boolean connected, final boolean isInitialSticky) {
        if (mConnected != connected) {
            mConnected = connected;
            for (INetChange listener : mNetChangeListeners) {
                listener.onNetChange(mConnected, isInitialSticky);
            }
        }
    }

    private void ensureNetReceiver() {
        if (mNetReceiver == null && !mNetChangeListeners.isEmpty()) {
            mNetReceiver = new NetReceiver();
            final IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(mNetReceiver, filter);
        }
    }

    public void addNetChangeListener(@NonNull final INetChange listener) {
        mNetChangeListeners.add(listener);
        ensureNetReceiver();
    }

    public void removeNetChangeListener(@NonNull final INetChange listener) {
        mNetChangeListeners.remove(listener);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        checkRequestCode(requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        checkRequestCode(requestCode);
        super.startActivityForResult(intent, requestCode, options);
    }

    private void checkRequestCode(int requestCode) {
        if (requestCode >= REQUEST_CODE_PERMISSION_BASE && requestCode <= REQUEST_CODE_PERMISSION_MAX) {
            throw new IllegalArgumentException("requestCode 20000~29999 用来请求权限，请用其他值");
        }
    }

    public enum Permissions {
        /***/
        EXTERNAL_STORAGE(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
        ACCESS_COARSE_LOCATION(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        final String[] permissions;

        Permissions(final String... permissions) {
            this.permissions = permissions;
        }

        public static Permissions from(final int index) {
            if (index < 0 || index >= values().length) {
                ZLog.e("index out of range");
                return null;
            }
            return Permissions.values()[index];
        }
    }

    public static final int REQUEST_CODE_PERMISSION_BASE = 20000;
    public static final int REQUEST_CODE_PERMISSION_MAX = 29999;

    public interface IRequestPermissions {
        /**
         * onPermissionsResult
         *
         * @param permissions 权限请求
         * @param result      返回值
         */
        void onPermissionsResult(@NonNull final Permissions permissions, final boolean result);
    }

    private final SparseArray<WeakReference<IRequestPermissions>> mRequestPermissionsCallbackArray = new SparseArray<>();

    public boolean hasPermissions(final String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void requestPermissions(@NonNull final Permissions permissions, @NonNull final IRequestPermissions callback) {
        if (!hasPermissions(permissions.permissions)) {
            mRequestPermissionsCallbackArray.put(permissions.ordinal(), new WeakReference<>(callback));
            ActivityCompat.requestPermissions(this, permissions.permissions, REQUEST_CODE_PERMISSION_BASE + permissions.ordinal());
        } else {
            callback.onPermissionsResult(permissions, true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode >= REQUEST_CODE_PERMISSION_BASE && requestCode <= REQUEST_CODE_PERMISSION_MAX) {
            final int index = requestCode - REQUEST_CODE_PERMISSION_BASE;
            final Permissions p = Permissions.from(index);
            final WeakReference<IRequestPermissions> ref = mRequestPermissionsCallbackArray.get(index);
            mRequestPermissionsCallbackArray.remove(index);

            final IRequestPermissions callback = ref != null ? ref.get() : null;
            if (p != null && callback != null) {
                boolean ret = true;
                for (int grant : grantResults) {
                    ret &= grant == PackageManager.PERMISSION_GRANTED;
                }
                callback.onPermissionsResult(p, ret);
            }
        }
    }

}
