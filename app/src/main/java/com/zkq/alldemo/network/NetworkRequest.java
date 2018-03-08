package com.zkq.alldemo.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

class NetworkRequest extends StringRequest {
    private Map<String, String> mParam;
    private Map<String, String> mHeader;
    private Priority mPriority = Priority.NORMAL;

    NetworkRequest(int method, String url,
                   Map<String, String> postParams,
                   Map<String, String> header,
                   Listener<String> listener,
                   ErrorListener errorListener) {
        super(method, Method.GET == method ? urlBuilder(url, postParams) : url, listener, errorListener);
        mParam = Method.GET == method ? null : postParams;
        mHeader = header;
//        setTag(this);
        setRetryPolicy(new DefaultRetryPolicy(3000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    NetworkRequest(String url, Map<String, String> params,
                   Listener<String> listener, ErrorListener errorListener, Map<String, String> header) {
        this(Method.GET, url, params, header, listener, errorListener);
    }

    private static String paramsToString(Map<String, String> params) {
        if (params != null && params.size() > 0) {
            String paramsEncoding = "UTF-8";
            StringBuilder encodedParams = new StringBuilder();
            try {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(),
                            paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(),
                            paramsEncoding));
                    encodedParams.append('&');

                }
                return encodedParams.toString();
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: "
                        + paramsEncoding, uee);
            }
        }
        return null;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mParam != null) {
            return mParam;
        } else {
            return super.getParams();
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return HttpTools.addCommonHeaders(mHeader);
    }

    private static String urlBuilder(String url, Map<String, String> params) {
        if (null != params && params.size() > 0) {
            return url + "?" + paramsToString(params);
        } else {
            return url;
        }
    }

    @Override
    public Priority getPriority() {
        return mPriority;
    }

    void setPriority(Priority priority) {
        mPriority = priority;
    }
}