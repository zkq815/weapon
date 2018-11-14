package com.zkq.weapon.networkframe.request;


import com.zkq.weapon.networkframe.response.BaseResponse;
import com.zkq.weapon.networkframe.response.BaseResponseBodyBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author  zkq
 * on 2018/8/24.
 * desc:retrofit 不同参数使用说明
 * https://www.jianshu.com/p/bf884248cb37
 */

public interface ApiMethod {
    @POST("/home/get/v2")
    Call<BaseResponseBodyBean> getMainRetrofit();

    @POST("/home/get/v2")
    Observable<BaseResponse> getMainRx();

    @GET("path")
    Observable<BaseResponse> getOneParmas(@Query("param") String paramValue);

    @GET("path")
    Observable<BaseResponse> getMoreParmas(@Query("param_one") String paramValueOne
            , @Query("param_two") String paramValueTwo);

    @GET("path")
    Observable<BaseResponse> getMoreParams(@QueryMap Map<String,String> paramsMap);

    @GET("path/{id}")
    Observable<BaseResponse> getAddOneParmas(@Path("id") int paramValue);

    @GET("path/{id}/{name}")
    Observable<BaseResponse> getAddMoreParmas(@Path("id") int paramValue, @Path("name") String name);

    @GET("path/{id}/{name}")
    Observable<BaseResponse> getAddMoreParmas(@Path("id") int paramValue
            , @Path("name") String name
            , @QueryMap Map<String, String> paramsMap);


    @POST("path")
    Observable<BaseResponse> postRx();

    @POST("path")
    Observable<BaseResponse> postRxOneParam(@Field("param") String param);

    @POST("path")
    Observable<BaseResponse> postRxMoreParams(@Field("param_one") String param_one
            , @Field("param_two") String param_two);

    @POST("path")
    Observable<BaseResponse> postRxMoreParams(@FieldMap Map<String,String> paramsMap);

    @POST("path/{id}")
    Observable<BaseResponse> postRxMoreParams(@Path("id") String id
            , @Field("param") String param_one
            , @FieldMap Map<String, String> paramsMap);

    @POST("path/{id}")
    Observable<BaseResponse> postRxMoreParams(@Path("id") String id
            , @Query("param_one") String param_one
            , @QueryMap Map<String, String> paramsMapGet
            , @Field("param_two") String param_two
            , @FieldMap Map<String, String> paramsMapPost);
}
