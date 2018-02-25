package com.zkq.alldemo.network;

import com.android.volley.toolbox.HurlStack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.OkUrlFactory;

/**
 * @author yc
 * @since 2017/2/7
 */
public class SimpleOkHttpStack extends HurlStack {

    private final OkUrlFactory okUrlFactory;

    public SimpleOkHttpStack() {
        this(new OkHttpClient());
    }

    public SimpleOkHttpStack(final OkHttpClient okHttpClient) {
        if (null == okHttpClient) {
            throw new NullPointerException("OkHttpClient must not be null");
        }

        this.okUrlFactory = new OkUrlFactory(okHttpClient);
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return okUrlFactory.open(url);
    }
}
