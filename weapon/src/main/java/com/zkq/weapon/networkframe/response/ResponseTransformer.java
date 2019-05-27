package com.zkq.weapon.networkframe.response;

import com.zkq.weapon.entity.BaseBean;
import com.zkq.weapon.entity.response.BaseResponse;
import com.zkq.weapon.networkframe.exception.ApiException;
import com.zkq.weapon.networkframe.exception.CustomException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * @author zkq
 * create:2018/11/16 10:40 AM
 * email:zkq815@126.com
 * desc: Rxjava自定义基类
 */
public class ResponseTransformer {

    public static <T> ObservableTransformer<BaseResponse, T> handleResult() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends BaseResponse>> {

        @Override
        public ObservableSource<? extends BaseResponse> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResponse, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseResponse response) throws Exception {
            int code = response.getCode();
            String message = response.getMsg();
            if (code == 6000) {
                return (ObservableSource<T>) Observable.just(response.getData());
            } else {
                return Observable.error(new ApiException(code, message));
            }
        }
    }
}





//public class ResponseTransformer {
//    static final int RESULT_OK = 200;
//    public static <T> ObservableTransformer<BaseResponse, BaseResponse> handleResult() {
//
//        return upstream -> upstream
//                .onErrorResumeNext(new ErrorResumeFunction<>())
//                .flatMap(new ResponseFunction<>());
//    }
//
//    /**
//     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
//     *
//     * @param <T>
//     */
//    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<T>> {
//
//        @Override
//        public ObservableSource<T> apply(Throwable throwable) throws Exception {
//            return Observable.error(CustomException.handleException(throwable));
//        }
//    }
//
//    /**
//     * 服务其返回的数据解析
//     * 正常服务器返回数据和服务器可能返回的exception
//     *
//     * @param <T>
//     */
//    private static class ResponseFunction<T> implements Function<BaseResponse, ObservableSource<T>> {
//
//        @Override
//        public ObservableSource<T> apply(BaseResponse tResponse) throws Exception {
//            int code = tResponse.getCode();
//            String message = tResponse.getMsg();
//            if (code == RESULT_OK) {
//                return (ObservableSource<T>) Observable.just(tResponse.getData());
//            } else {
//                return Observable.error(new ApiException(code, message));
//            }
//        }
//    }
//
//}
