package com.zkq.weapon.basehodler.module;

import android.content.Context;
import android.widget.FrameLayout;


import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;
import com.tmall.wireless.tangram.support.ExposureSupport;
import com.zkq.weapon.R;

import java.util.Locale;

import androidx.annotation.NonNull;

/**
 * @author zkq
 * create:2019/5/29 12:05 AM
 * email:zkq815@126.com
 * desc: 模块视图基类
 */
public class BaseEdtionModuleView<T extends BaseNativeEdtionModule> extends FrameLayout implements ITangramViewLifeCycle {

    private T mEdtionModule;

    public BaseEdtionModuleView(@NonNull Context context) {
        super(context);
    }

    public BaseEdtionModuleView(@NonNull Context context, @NonNull T edtionModule) {
        super(context);
        this.mEdtionModule = edtionModule;
        this.setBackgroundColor(getContext().getResources().getColor(R.color.white));
    }

    public T getEdtionModule(){
        return mEdtionModule;
    }


    @Override
    public void cellInited(BaseCell cell) {
        setOnClickListener(cell);
        if (cell.serviceManager != null) {
            ExposureSupport exposureSupport = cell.serviceManager.getService(ExposureSupport.class);
            if (exposureSupport != null) {
                exposureSupport.onTrace(this, cell, cell.type);
            }
        }
        //子类实现
    }

    @Override
    public void postBindView(BaseCell cell) {
        //子类实现
    }

    @Override
    public void postUnBindView(BaseCell cell) {
        //子类实现
    }

}
