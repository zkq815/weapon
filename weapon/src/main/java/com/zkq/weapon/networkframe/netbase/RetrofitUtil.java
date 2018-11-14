package com.zkq.weapon.networkframe.netbase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zkq.weapon.BuildConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.concurrent.ThreadSafe;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author:zkq time:2018/11/14:10:27
 * email:zkq815@126.com
 */
@ThreadSafe
public class RetrofitUtil {
    private static RetrofitUtil mInstance;
    private Retrofit mRetrofit;
    private static int TIME_OUT = 5;

    public static RetrofitUtil getInstance() {

        if (mInstance == null) {
            mInstance = new RetrofitUtil();
        }

        return mInstance;
    }

    public RetrofitUtil() {
        this(true);
    }

    public <T> T createApi(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

    /**
     * 初始化retrofit
     */
    private RetrofitUtil(boolean useRxJava) {
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(RequestUrlConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHttp());
        if (useRxJava) {
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }

        mRetrofit = builder.build();
    }

    /**
     * 初始化okhttp请求
     */
    private OkHttpClient getOkHttp() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        return new OkHttpClient.Builder()
                .addInterceptor(new HeadInterceptor())//添加头部拦截器 head
                .addInterceptor(logging)//log
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(HttpUrl.parse(url.host()), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse(url.host()));
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
    }
}

