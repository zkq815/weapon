package com.zkq.alldemo.netframe.responsebean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zkq
 * on 2018/8/27.
 * 全局响应基础
 */

public class BaseResponse {

    private static final Object NULL_DATA = new Object();
    /**
     * 响应状态码
     * */
    @SerializedName("code")
    private int code;

    /**
     * 响应消息信息
     * */
    @SerializedName("msg")
    private String msg;

    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return NULL_DATA;
    }
}
