package com.zkq.weapon.basehodler.operation;

import android.content.Context;
import android.view.View;

import com.zkq.weapon.basehodler.holder.BaseNativeEdtionHolder;
import com.zkq.weapon.basehodler.module.BaseNativeEdtionModule;

/**
 * @author zkq
 * create:2019/5/29 12:08 AM
 * email:zkq815@126.com
 * desc: 操作基础类
 */
public abstract class BaseEdtionOperationModel<T extends BaseNativeEdtionModule> {

    private Context mContext;

    private T mEdtionModule;

    BaseEdtionOperationModel(Context context, T edtionModule) {
        this.mContext = context;
        this.mEdtionModule = edtionModule;
    }

    /**
     * @return 创建模块ViewHolder
     */
    public abstract BaseNativeEdtionHolder createEdtionHolder();

    public Context getContext() {
        return mContext;
    }

    public T getEdtionModule() {
        return mEdtionModule;
    }

    public void setEdtionModule(T edtionModule) {
        this.mEdtionModule = edtionModule;
    }

    /**
     * @return 创建ViewHolder需要显示的视图
     */
    abstract View createShowView();
}
