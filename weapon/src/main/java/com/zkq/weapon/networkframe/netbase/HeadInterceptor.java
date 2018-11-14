package com.zkq.weapon.networkframe.netbase;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zkq
 * on 2018/8/27.
 * 网络请求头部拦截
 */

public class HeadInterceptor implements Interceptor {

    private static final String TAG = "HeadInterceptor";

    private static final String AUTHORIZATION = "Authorization";
    private static final String PLATFORM_TYPE = "platform_type";


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if(request.url().toString().contains("http://111.231.63.163/interface.php")){
            Request tempRequest = request.newBuilder()
                    .method(request.method(),request.body())
                    .build();
            return chain.proceed(tempRequest);
        }else if(request.url().toString().contains("public")){
            Request tempRequest = request.newBuilder()
                    .method(request.method(),request.body())
                    .addHeader(PLATFORM_TYPE,"android")
                    .build();
            return chain.proceed(tempRequest);
        }else{
            Request tempRequest = request.newBuilder()
                    .method(request.method(),request.body())
                    .addHeader(PLATFORM_TYPE,"android")
//                    .addHeader("token")
                    .build();
            return chain.proceed(tempRequest);
        }
    }
}
