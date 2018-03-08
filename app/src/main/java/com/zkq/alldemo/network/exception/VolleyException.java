package com.zkq.alldemo.network.exception;

/**
 * @author yc
 * @since 2017/3/28
 */
public class VolleyException extends NetworkException {

    public VolleyException() {
    }

    public VolleyException(String detailMessage) {
        super(detailMessage);
    }

    public VolleyException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public VolleyException(Throwable throwable) {
        super(throwable);
    }
}
