package com.zkq.weapon.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zkq.weapon.market.mmkv.MmkvUtils;
import com.zkq.weapon.market.util.ZLog;

/**
 * @author zkq
 * create:2018/11/15 3:50 PM
 * email:zkq815@126.com
 * desc: Fragment基类
 */
public class BaseFragment extends Fragment implements BaseActivity.INetChange {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).addNetChangeListener(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        ZLog.i(this.getClass().toString(), "onAttach");

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        ZLog.i(this.getClass().toString(), "onDetach");

        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        ZLog.i(this.getClass().toString(), "onDestroyView");

        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).removeNetChangeListener(this);
        }

        super.onDestroy();
    }

    protected ActionBar getSupportActionBar() {
        final Activity activity = getActivity();
        if (null != activity && activity instanceof AppCompatActivity) {
            return ((AppCompatActivity) activity).getSupportActionBar();
        }

        return null;
    }

    /**
     * 设置ActionBar标题
     *
     * @param title 标题
     */
    protected void setTitle(final String title) {
        final Activity activity = getActivity();
        if (null != activity && !activity.isFinishing() && activity instanceof AppCompatActivity) {
            final ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (null != actionBar) {
                actionBar.setTitle(title);
            }
        }
    }

    /**
     * 设置ActionBar标题
     *
     * @param res 标题资源ID
     */
    protected void setTitle(final int res) {
        setTitle(getResources().getString(res));
    }

    @Override
    public void onNetChange(boolean connected, boolean isInitialSticky) {

    }
}
