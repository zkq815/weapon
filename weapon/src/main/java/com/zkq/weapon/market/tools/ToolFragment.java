package com.zkq.weapon.market.tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.zkq.weapon.market.util.ZLog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author:zkq
 * create:2018/10/24 上午11:46
 * email:zkq815@126.com
 * desc: Fragment工具
 */
public interface ToolFragment {

    static Fragment getFragment(@NonNull final FragmentManager fm, @Nullable final Bundle savedInstanceState, @NonNull final Class<? extends Fragment> clz) {
        Fragment f = null;
        if (savedInstanceState != null) {
            final List<Fragment> fragmentList = fm.getFragments();
            if (null != fragmentList) {
                for (Fragment fragment : fragmentList) {
                    if (fragment.getClass() == clz) {
                        f = fragment;
                    }
                }
            }
        }

        return f;
    }

    static Fragment getOrCreateFragment(@NonNull final FragmentManager fm, @Nullable final Bundle savedInstanceState, @NonNull final Class<? extends Fragment> clz) {
        Fragment f = getFragment(fm, savedInstanceState, clz);

        if (f == null) {
            try {
                f = clz.newInstance();
            } catch (Exception e) {
                ZLog.t("getOrCreateFragment: " + savedInstanceState + " " + clz, e);
            }
        }

        return f;
    }

    /**
     * 只适用于设置了{@link FragmentPagerAdapter}的ViewPager
     *
     * @param fm      {@link FragmentManager}
     * @param contain fragment容器
     * @param index   fragment在容器中的序号
     * @return fm中获取已存在的fragment
     */
    static Fragment getFragmentByTag(@NonNull final FragmentManager fm, @NonNull final View contain, final int index) {
        return fm.findFragmentByTag(makeFragmentName(contain.getId(), index));
    }

    /**
     * 根据contain的ID和fragment的序号获取fragment的tag<br/>
     *
     * @param viewId contain的ID
     * @param id     fragment在adapter中的序号
     * @return fragment的TAG
     * @see FragmentPagerAdapter#(int, long)
     */
    static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    static boolean isActivityAlive(Activity activity) {
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    static void startFragment(AppCompatActivity activity, int containerId, @NonNull Fragment fragment, Bundle params) {
        //如果有mainFragment
        if (params != null) {
            fragment.setArguments(params);
        }

        if (isActivityAlive(activity)) {
            activity.getSupportFragmentManager().beginTransaction().add(containerId,
                    fragment, null).
                    commitAllowingStateLoss();
        }
    }

    static void startFragment(AppCompatActivity activity, int containerId,
                              @NonNull Fragment fragment, Bundle params, String tag) {
        //如果有mainFragment
        if (params != null) {
            fragment.setArguments(params);
        }

        if (isActivityAlive(activity)) {
            activity.getSupportFragmentManager().beginTransaction().add(containerId,
                    fragment, tag).
                    commitAllowingStateLoss();
        }
    }

    static void addFragment(Activity activity, int containerId, Fragment fromF, Fragment f, boolean isBack, int anim) {
        if (activity == null) {
            return;
        }

        if (activity instanceof AppCompatActivity) {
            FragmentManager fm = ((AppCompatActivity) activity).getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (fromF != null) {
                ft.hide(fromF);
            }
            ft.add(containerId, f);
            if (anim != FragmentTransaction.TRANSIT_UNSET) {
                ft.setTransition(anim);
            }
            if (isBack) {
                ft.addToBackStack(null);
            }
            try {
                ft.commitAllowingStateLoss();
            } catch (Exception ignore) {
                // 在 activity 已经 destory 时，可能抛出IllegalStateException异常。
                // 由于在这种状态下，已经无法处理界面的切换，也不需要做其他恢复操作，所以这里只捕获异常，避免程序崩溃。
            }
        }
    }
}
