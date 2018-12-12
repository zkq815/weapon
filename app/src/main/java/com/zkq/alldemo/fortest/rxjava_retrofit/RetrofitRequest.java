package com.zkq.alldemo.fortest.rxjava_retrofit;

import com.zkq.weapon.networkframe.response.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by zkq
 * on 2018/7/24.
 */

public interface RetrofitRequest {
    //get请求测试
    @GET("exempt/list")
    Call<ResponseBody> getList(@Query("sortDirection") String sortDirection);

    //get请求测试
    @GET("exempt/list")
    Call<ResponseBody> getList(@QueryMap HashMap<String,String> map);

    //get请求测试
    @GET("exempt/list")
    Observable<ResponseBody> rxgetList(@Query("sortDirection") String sortDirection);

    //post请求测试
    @POST("mzstore/home/get/v2")
    Call<ResponseBody> postMain();

    //post请求测试
    @POST("mzstore/home/get/v2")
    Observable<BaseResponse> rxPostMain();
}
