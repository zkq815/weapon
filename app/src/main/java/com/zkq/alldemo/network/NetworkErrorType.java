package com.zkq.alldemo.network;

/**
 * Created by small_plane on 16/7/22.
 */
public enum NetworkErrorType {
    RESPONSE_JSON_NULL("1100", "Response Null error"),
    RESPONSE_FORMAT_ERROR("1101", "Response format error"),
    RESPONSE_CODE_ERROR("1102", "Response code error"),
    RESPONSE_HTTP_ERROR("1103", "Http_response_error");

    final private String message;
    final private String code;

    NetworkErrorType(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
