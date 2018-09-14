package com.zkq.alldemo.fortest.rxjava_retrofit;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityRxjavaBinding;
import com.zkq.alldemo.netframe.httpservice.ApiMethod;
import com.zkq.alldemo.netframe.net.RetrofitUtil;
import com.zkq.alldemo.netframe.responsebean.BaseResponseBodyBean;
import com.zkq.alldemo.util.ZKQLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxjavaActivity extends AppCompatActivity {

    private ActivityRxjavaBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_rxjava);

        mBinding.btnRxGet.setOnClickListener((v)->rxTest());

        mBinding.btnRxPostTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxPost();
            }
        });

        mBinding.btnRxGetTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxGet();
            }
        });

    }


    private void rxTest(){
        Observable.create(new ObservableOnSubscribe<Integer>(){//第一步：初始化Observable
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                ZKQLog.e("zkq","observable 1");
                emitter.onNext(1);
                ZKQLog.e("zkq","observable 2");
                emitter.onNext(2);
                ZKQLog.e("zkq","observable 3");
                emitter.onNext(3);
                ZKQLog.e("zkq","observable 4");
                emitter.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {//第三步：订阅

            //第二步：初始化Observer
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                i++;
                if(i==2){
                    //在RxJava2.x中，新增的disposable可以做到切断的操作，让Observer观察者不再接收上游事件
//                    mDisposable.dispose();
                    ZKQLog.e("ZKQ","观察接收中断");
                }
            }

            @Override
            public void onError(Throwable e) {
                ZKQLog.e("ZKQ","错误消息=="+e.getMessage());
            }

            @Override
            public void onComplete() {
                ZKQLog.e("ZKQ","观察完成");
            }
        });
    }

    //get请求
    private void rxGet(){
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://lists.meizu.com/")//exempt/list
                                .build();
        RetrofitRequest retrofitRequest = retrofit.create(RetrofitRequest.class);
        Call<ResponseBody> call = retrofitRequest.getList("1");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    ZKQLog.e("ZKQ","list请求结果==="+response.body().string());

                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    //post请求
    private void rxPost(){
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://app.store.res.meizu.com/")//mzstore/home/get/v2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        RetrofitRequest retrofitRequest = retrofit.create(RetrofitRequest.class);
        Call<ResponseBody> call = retrofitRequest.getMain();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String tempString = response.body().string();
                    ZKQLog.e("ZKQ","main请求结果==="+tempString);
                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void postLazzy(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5,TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://app.store.res.meizu.com/")
                .build();

        ApiMethod apiService = retrofit.create(ApiMethod.class);
        Call<BaseResponseBodyBean> call = apiService.getMain();
        call.enqueue(new Callback<BaseResponseBodyBean>() {
            @Override
            public void onResponse(Call<BaseResponseBodyBean> call, Response<BaseResponseBodyBean> response) {

            }

            @Override
            public void onFailure(Call<BaseResponseBodyBean> call, Throwable t) {

            }
        });
    }

    private void test(){
        RetrofitUtil.getInstance().createApi(ApiMethod.class).getM().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe();
    }



}
