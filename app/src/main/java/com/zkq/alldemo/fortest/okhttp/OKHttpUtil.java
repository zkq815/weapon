package com.zkq.alldemo.fortest.okhttp;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by zkq on 2017/3/28.
 */

public class OKHttpUtil {
    private static OkHttpClient instance = null;
    private static Request.Builder builder = null;
    private static Call call = null;
    /**
     * 拿到okhttpClient对象
     * 构造request
     * */
    public static OkHttpClient getInstance(){
        if(instance == null){
            //拿到OKHttpClient对象
            instance = new OkHttpClient();
            //构造Request
            builder = new Request.Builder();
        }
        return instance;
    }

    public static void get(String url, String params, Callback callback){
        getInstance();
        //将request封装为call
        Request request = builder.get().url(url).build();
        call = instance.newCall(request);
        //执行call
        call.enqueue(callback);
    }

}
