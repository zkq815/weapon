package com.zkq.alldemo.network.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author yc
 * @since 2017/9/7
 */
public class DefaultOkHttpClientFactory implements IOkHttpClientFactory {

    private volatile static DefaultOkHttpClientFactory sInstance;

    public static DefaultOkHttpClientFactory getInstance() {
        if (sInstance == null) {
            synchronized (DefaultOkHttpClientFactory.class) {
                if (sInstance == null) {
                    sInstance = new DefaultOkHttpClientFactory();
                }
            }
        }

        return sInstance;
    }

    @Override
    public OkHttpClient generate() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request request = chain.request();
                final Request newRequest = request.newBuilder()
//                        .addHeader("mz_version", String.valueOf(BuildConfig.VERSION_CODE))
//                        .addHeader("mz_channel", BuildConfig.CHANNEL)
                        .build();

                return chain.proceed(newRequest);
            }
        });
        return builder.build();
    }
}
