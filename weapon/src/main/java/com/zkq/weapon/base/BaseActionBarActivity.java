package com.zkq.weapon.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

/**
 * @author zkq
 * create:2018/11/15 3:49 PM
 * email:zkq815@126.com
 * desc:
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
