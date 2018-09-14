package com.zkq.alldemo.fortest.rxjava_retrofit;

import java.util.HashMap;

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

    //post请求测试
    @POST("mzstore/home/get/v2")
    Call<ResponseBody> getMain();
}
