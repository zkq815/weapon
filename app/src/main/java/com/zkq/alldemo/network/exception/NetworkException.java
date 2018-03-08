package com.zkq.alldemo.network.exception;

/**
 * @author yc
 * @since 2017/3/27
 */
public class NetworkException extends Exception {

    public NetworkException() {
        super();
    }

    public NetworkException(String detailMessage) {
        super(detailMessage);
    }

    public NetworkException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NetworkException(Throwable throwable) {
        super(throwable);
    }
}
