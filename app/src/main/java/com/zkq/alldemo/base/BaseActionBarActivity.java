package com.zkq.alldemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;


/**
 * @author yc
 * @since 2017/11/2
 */
public class BaseActionBarActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
    }

    /**
     * 是否显示返回按钮
     */
    protected boolean showBack() {
        return false;
    }

    protected void initActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(showBack());
        }
    }

}
