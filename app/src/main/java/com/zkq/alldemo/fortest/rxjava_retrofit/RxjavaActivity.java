package com.zkq.alldemo.fortest.rxjava_retrofit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityRxjavaBinding;
import com.zkq.weapon.base.BaseActivity;
import com.zkq.weapon.market.util.ZLog;
import com.zkq.weapon.networkframe.netbase.RetrofitUtil;
import com.zkq.weapon.networkframe.response.BaseResponse;
import com.zkq.weapon.networkframe.response.ResponseTransformer;
import com.zkq.weapon.networkframe.scheduler.SchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RxjavaActivity extends BaseActivity {

    private ActivityRxjavaBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_rxjava);
        mBinding.btnRxGet.setOnClickListener(v-> rxTest());
        mBinding.btnRxGetTest.setOnClickListener(v -> rxgetList());
        mBinding.btnRxPostTest.setOnClickListener(v-> rxPost());
    }

    private void rxTest(){
        Observable.create(new ObservableOnSubscribe<Integer>(){//第一步：初始化Observable
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                ZLog.e("zkq","observable 1");
                emitter.onNext(1);
                ZLog.e("zkq","observable 2");
                emitter.onNext(2);
                ZLog.e("zkq","observable 3");
                emitter.onNext(3);
                ZLog.e("zkq","observable 4");
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
                    ZLog.e("ZKQ","观察接收中断");
                }
            }

            @Override
            public void onError(Throwable e) {
                ZLog.e("ZKQ","错误消息=="+e.getMessage());
            }

            @Override
            public void onComplete() {
                ZLog.e("ZKQ","观察完成");
            }
        });
    }

    //get请求
    private void rxGet(){

        RetrofitRequest retrofitRequest = RetrofitUtil.getInstance().createApi(RetrofitRequest.class);

        Call<ResponseBody> call = retrofitRequest.getList("1");
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try{
                    ZLog.e("ZKQ","list请求结果==="+response.body().string());

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

        RetrofitUtil.getInstance().createApi(RetrofitRequest.class)
                .rxPostMain()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse responseBody) {
                        ZLog.e("subscribe请求成功-----"+"response=="+responseBody.getCode());
                    }

                    @Override
                    public void onError(Throwable e) {
                        ZLog.e("onError请求失败");
                    }

                    @Override
                    public void onComplete() {
                        ZLog.e("onComplete请求完成");
                    }
                });
    }

    private void rxgetList(){
        RetrofitUtil.getInstance().createApi(RetrofitRequest.class).rxPostMain()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse responseBody) {
                        ZLog.e("subscribe请求成功-----"+"response=="+responseBody.getCode());
                    }

                    @Override
                    public void onError(Throwable e) {
                        ZLog.e("onError请求失败");
                    }

                    @Override
                    public void onComplete() {
                        ZLog.e("onComplete请求完成");
                    }
                });

//        RetrofitUtil.getInstance().createApi(RetrofitRequest.class).rxPostMain()
//                .compose(SchedulerProvider.getInstance().applySchedulers())
//                .compose(ResponseTransformer.handleResult())
//                .doOnNext(baseResponse -> getTest())
//                .subscribe(baseResponse -> getTest(), throwable -> getTest());


    }


    private Observable<BaseResponse> getTest(){
        return null;
    }

}
