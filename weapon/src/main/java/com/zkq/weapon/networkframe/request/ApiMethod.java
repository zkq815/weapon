package com.zkq.weapon.networkframe.request;


import com.zkq.weapon.entity.response.BaseResponse;
import com.zkq.weapon.entity.response.BaseResponseBodyBean;

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
 * @author zkq
 * create:2018/11/16 10:41 AM
 * email:zkq815@126.com
 * desc: retrofit 不同参数使用说明
 * https://www.jianshu.com/p/bf884248cb37
 */
public interface ApiMethod {
    /**
     * call 非RX
     * @return 回调
     * */
    @POST("/home/get/v2")
    Call<BaseResponseBodyBean> getMainRetrofit();

    /**
     * RX get 无参请求
     * @return 回调
     * */
    @GET("/home/get/v2")
    Observable<BaseResponse> getMainRx();

    /**
     * RX get请求 一个参数
     * @param paramValue 参数
     * @return 回调
     * */
    @GET("path")
    Observable<BaseResponse> getOneParmas(@Query("param") String paramValue);

    /**
     * RX get请求 多个参数
     * @param paramValueOne 参数1
     * @param paramValueTwo 参数2
     * @return 回调
     * */
    @GET("path")
    Observable<BaseResponse> getMoreParmas(@Query("param_one") String paramValueOne
            , @Query("param_two") String paramValueTwo);

    /**
     * RX get请求 多个参数
     * @param paramsMap 参数集合
     * @return 回调
     * */
    @GET("path")
    Observable<BaseResponse> getMoreParams(@QueryMap Map<String, String> paramsMap);

    /**
     * RX get请求 添加请求url内容
     * @return 回调
     * */
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
    Observable<BaseResponse> postRxMoreParams(@FieldMap Map<String, String> paramsMap);

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
