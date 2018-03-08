package com.zkq.alldemo.network.netdemo;

import android.support.annotation.NonNull;

import com.zkq.alldemo.network.NetworkHelper;
import com.zkq.alldemo.util.ZKQLog;

/**
 * Created by zkq
 * on 2018/3/8.
 */

public class TestNetHelper extends NetworkHelper<BaseBean>{

    @Override
    protected BaseBean disposeResponse(@NonNull String response) throws Exception {
        ZKQLog.e("zkq","response"+response);
        BaseBean bean = new BaseBean();
        return bean;
    }
}
