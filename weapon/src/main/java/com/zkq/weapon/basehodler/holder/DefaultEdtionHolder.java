package com.zkq.weapon.basehodler.holder;

import android.content.Context;
import android.view.View;

import com.zkq.weapon.basehodler.module.BaseNativeEdtionModule;

/**
 * @author zkq
 * create:2019/5/29 12:31 AM
 * email:zkq815@126.com
 * desc: 原生专题默认Holder,用于容错处理
 */
public class DefaultEdtionHolder extends BaseNativeEdtionHolder {

    public DefaultEdtionHolder(Context ctx, View itemView) {
        super(ctx, itemView);
    }

    @Override
    public void showViews(BaseNativeEdtionModule edtionModule) {

    }
}
