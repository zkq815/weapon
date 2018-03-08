package com.zkq.alldemo.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author small_plane
 * @since 16/8/5
 */
class NetworkGsonRequest extends JsonRequest<String> {
    private Priority mPriority = Priority.NORMAL;

    NetworkGsonRequest(int method, String url, String requestBody, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return HttpTools.addCommonHeaders(null);
    }

    @Override
    public Priority getPriority() {
        return mPriority;
    }

    void setPriority(Priority priority) {
        mPriority = priority;
    }
}
