package com.zkq.weapon.networkframe.response;

import com.google.gson.annotations.SerializedName;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * @author zkq
 * create:2018/11/16 10:42 AM
 * email:zkq815@126.com
 * desc: 全局响应返回数据基类
 */
public class BaseResponse<T extends BaseBean> {

    /**
     * 响应状态码
     */
    @SerializedName("code")
    private int code;

    /**
     * 响应消息信息
     */
    @SerializedName("message")
    private String msg;

    private T data;

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

    public T getData() {
        return data;
    }

//    @Override
//    public MediaType contentType() {
//        return null;
//    }
//
//    @Override
//    public long contentLength() {
//        return 0;
//    }
//
//    @Override
//    public BufferedSource source() {
//        return null;
//    }
}
