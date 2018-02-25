package com.zkq.alldemo.network.okhttp;

import okhttp3.OkHttpClient;

/**
 * @author yc
 * @since 2017/9/7
 */
public interface IOkHttpClientFactory {

    OkHttpClient generate();
}
