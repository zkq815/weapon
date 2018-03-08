package com.zkq.alldemo.network.exception;

/**
 * @author yc
 * @since 2017/3/28
 */
public class UnknownException extends NetworkException {

    public UnknownException() {
    }

    public UnknownException(String detailMessage) {
        super(detailMessage);
    }

    public UnknownException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UnknownException(Throwable throwable) {
        super(throwable);
    }
}
