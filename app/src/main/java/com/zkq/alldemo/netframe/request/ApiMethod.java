package com.zkq.alldemo.netframe.request;

import com.zkq.weapon.networkframe.response.BaseResponseBodyBean;
import com.zkq.weapon.networkframe.response.BaseBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by zkq
 * on 2018/8/24.
 */

public interface ApiMethod {
    @POST("/home/get/v2")
    Call<BaseResponseBodyBean> getMain();

    @POST("/home/get/v2")
    Observable<BaseBean> getM();
}
