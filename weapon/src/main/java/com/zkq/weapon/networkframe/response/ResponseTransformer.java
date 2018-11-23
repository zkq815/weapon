package com.zkq.weapon.networkframe.response;

import com.zkq.weapon.networkframe.exception.ApiException;
import com.zkq.weapon.networkframe.exception.CustomException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * @author zkq
 * create:2018/11/16 10:40 AM
 * email:zkq815@126.com
 * desc:
 */
public class ResponseTransformer {
    static final int RESULT_OK = 200;
    public static <T> ObservableTransformer<BaseResponse, BaseResponse> handleResult() {

        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }

    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(Throwable throwable) throws Exception {
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
        public ObservableSource<T> apply(BaseResponse tResponse) throws Exception {
//            int code = tResponse.getCode();
//            String message = tResponse.getMsg();
//            if (code == RESULT_OK ) {
//                return Observable.just(tResponse.getData());
//            } else {
//                return Observable.error(new ApiException(code, message));
//            }
            return Observable.error(new Exception());
        }
    }

}
