package com.zkq.weapon.networkframe.netbase;

import com.zkq.weapon.networkframe.constants.RequestUrlConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zkq
 * create:2018/11/16 10:39 AM
 * email:zkq815@126.com
 * desc: 网络请求头部拦截
 */

public class HeadInterceptor implements Interceptor {

    private static final String TAG = "HeadInterceptor";

    private static final String AUTHORIZATION = "Authorization";
    private static final String PLATFORM_TYPE = "platform_type";


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if (request.url().toString().contains(RequestUrlConstants.BASE_URL)) {
            Request tempRequest = request.newBuilder()
                    .method(request.method(), request.body())
                    .build();
            return chain.proceed(tempRequest);
        } else {
            Request tempRequest = request.newBuilder()
                    .method(request.method(), request.body())
                    .addHeader(PLATFORM_TYPE, "android")
                    .build();
            return chain.proceed(tempRequest);
        }
    }
}
