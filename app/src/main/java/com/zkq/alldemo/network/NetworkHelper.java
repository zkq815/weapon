package com.zkq.alldemo.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zkq.alldemo.BuildConfig;
import com.zkq.alldemo.application.MyApplication;
import com.zkq.alldemo.network.exception.EmptyBeanException;
import com.zkq.alldemo.network.exception.EmptyResponseException;
import com.zkq.alldemo.network.exception.NetworkException;
import com.zkq.alldemo.network.exception.UnknownException;
import com.zkq.alldemo.network.exception.VolleyException;
import com.zkq.alldemo.util.ZKQLog;

import java.util.Map;

/**
 * @author small_plane
 * @since 16/7/22
 */
public abstract class NetworkHelper<T> implements Response.Listener<String>, Response.ErrorListener {

    private RequestQueue mRequestQueue;
    protected Gson mGson;
    private Request.Priority mPriority = Request.Priority.NORMAL;

    public NetworkHelper() {
        mRequestQueue = MyApplication.getRequestQueue();
        mGson = new Gson();
    }

    public NetworkHelper(Request.Priority priority) {
        this();
        mPriority = priority;
    }

    private NetworkRequest getRequestForGet(String url, Map<String, String> params, Map<String, String> header) {
        NetworkRequest request = new NetworkRequest(url, params, this, this, header);
        request.setTag(this.toString());
        request.setPriority(mPriority);
        return request;
    }

    private NetworkRequest getRequestForPost(String url, Map<String, String> params) {
        NetworkRequest request = new NetworkRequest(Request.Method.POST, url, params, null, this, this);
        request.setTag(this.toString());
        request.setPriority(mPriority);
        return request;
    }

    private NetworkRequest getRequestForPost(String url, Map<String, String> params, Map<String, String> header) {
        NetworkRequest request = new NetworkRequest(Request.Method.POST, url, params, header, this, this);
        request.setTag(this.toString());
        request.setPriority(mPriority);
        return request;
    }

    private NetworkGsonRequest getGsonRequestForPost(String url, String gson) {
        NetworkGsonRequest request = new NetworkGsonRequest(Request.Method.POST, url, gson, this, this);
        request.setTag(this.toString());
        request.setPriority(mPriority);
        return request;
    }

    /**
     * @see #get(String, Map, DataListener)
     */
    @Deprecated
    public void sendGETRequest(String url, Map<String, String> params) {
        mRequestQueue.add(getRequestForGet(url, params, null));
    }

    /**
     * @see #get(String, Map, Map, DataListener)
     */
    @Deprecated
    public void sendGETRequest(String url, Map<String, String> params, Map<String, String> header) {
        mRequestQueue.add(getRequestForGet(url, params, header));
    }

    /**
     * @see #post(String, Map, DataListener)
     */
    @Deprecated
    public void sendPostRequest(String url, Map<String, String> params) {
        mRequestQueue.add(getRequestForPost(url, params));
    }

    /**
     * @see #post(String, Map, Map, DataListener)
     */
    @Deprecated
    public void sendPostRequest(String url, Map<String, String> params, Map<String, String> header) {
        mRequestQueue.add(getRequestForPost(url, params, header));
    }

    /**
     * @see #postJson(String, Object, DataListener)
     */
    @Deprecated
    public void sendGsonPostRequest(String url, Object bean) {
        mRequestQueue.add(getGsonRequestForPost(url, mGson.toJson(bean)));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (BuildConfig.LOG_DEBUG) {
            ZKQLog.t(this, "onErrorResponse", error);
        }
        disposeVolleyError(error);
    }

    protected void disposeVolleyError(VolleyError error) {
        notifyErrorHappened(new VolleyException(error));
    }

    @Override
    public void onResponse(String response) {
        if (BuildConfig.LOG_DEBUG) {
            ZKQLog.e(this, "onResponse: " + response);
        }

        try {
            deal(response);
        } catch (Throwable t) {
            notifyErrorHappened(t);
        }
    }

    private void deal(final String response) throws Throwable {
        if (TextUtils.isEmpty(response)) {
            throw new EmptyResponseException();
        }

        final T data = disposeResponse(response);

        if (null == data) {
            throw new EmptyBeanException();
        }

        if (null != mDataListener) {
            mDataListener.onDataReceive(data);
        }
    }

    protected abstract T disposeResponse(@NonNull String response) throws Exception;

    private DataListener<T> mDataListener;

    /**
     * 容易造成内存泄漏，使用新接口
     *
     * @see #get(String, DataListener)
     * @see #get(String, Map, DataListener)
     * @see #get(String, Map, Map, DataListener)
     * @see #post(String, DataListener)
     * @see #post(String, Map, DataListener)
     * @see #post(String, Map, Map, DataListener)
     * @see #postJson(String, Object, DataListener)
     */
    @Deprecated
    public void setDataListener(DataListener<T> dataListener) {
        mDataListener = dataListener;
    }

    private void notifyErrorHappened(@NonNull Throwable t) {
        if (BuildConfig.LOG_DEBUG) {
            ZKQLog.t(this, "notifyErrorHappened", t);
        }

        final NetworkException error = (t instanceof NetworkException) ? (NetworkException) t : new UnknownException(t);
        if (mDataListener != null) {
            mDataListener.onErrorHappened(error);
        }
    }

    public void stopRequest() {
        if (BuildConfig.LOG_DEBUG) {
            ZKQLog.e(this, "cancel");
        }
        mRequestQueue.cancelAll(this.toString());
    }

    public void exitRequest(){
        if (BuildConfig.LOG_DEBUG) {
            ZKQLog.e(this, "cancel");
        }
        mRequestQueue.cancelAll(this.toString());
        mDataListener = null;
    }

    protected void setRequestQueue(RequestQueue requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public Request<String> get(final String url, final DataListener<T> dataListener) {
        return get(url, null, null, dataListener);
    }

    public Request<String> get(final String url, final Map<String, String> params, final DataListener<T> dataListener) {
        return get(url, null, params, dataListener);
    }

    public Request<String> get(final String url, final Map<String, String> headers, final Map<String, String> params, final DataListener<T> dataListener) {
        return request(Request.Method.GET, url, headers, params, dataListener);
    }

    public Request<String> post(final String url, final DataListener<T> dataListener) {
        return post(url, null, null, dataListener);
    }

    public Request<String> post(final String url, final Map<String, String> params, final DataListener<T> dataListener) {
        return post(url, null, params, dataListener);
    }

    public Request<String> post(final String url, final Map<String, String> headers, final Map<String, String> params, final DataListener<T> dataListener) {
        return request(Request.Method.POST, url, headers, params, dataListener);
    }

    public Request<String> postJson(final String url, final Object obj, final DataListener<T> dataListener) {
        final NetworkGsonRequest request = new NetworkGsonRequest(Request.Method.POST, url, mGson.toJson(obj), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response(response, dataListener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (BuildConfig.LOG_DEBUG) {
                    ZKQLog.t(this, "onErrorResponse", error);
                }

                notifyErrorHappened(new VolleyException(error), dataListener);
            }
        });

        request.setTag(this.toString());
        request.setPriority(mPriority);

        mRequestQueue.add(request);

        return request;
    }

    private Request<String> request(final int method, final String url, final Map<String, String> headers, final Map<String, String> params, final DataListener<T> dataListener) {
        final NetworkRequest request = new NetworkRequest(method, url, params, headers, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response(response, dataListener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (BuildConfig.LOG_DEBUG) {
                    ZKQLog.t(this, "onErrorResponse", error);
                }

                notifyErrorHappened(new VolleyException(error), dataListener);
            }
        });

        request.setTag(this.toString());
        request.setPriority(mPriority);

        mRequestQueue.add(request);

        return request;
    }

    private void response(final String response, final DataListener<T> dataListener) {
        if (BuildConfig.LOG_DEBUG) {
            ZKQLog.e(this, "onResponse: " + response);
        }

        try {
            if (TextUtils.isEmpty(response)) {
                throw new EmptyResponseException();
            }

            final T data = disposeResponse(response);

            if (null == data) {
                throw new EmptyBeanException();
            }

            if (null != dataListener) {
                dataListener.onDataReceive(data);
            }
        } catch (Throwable t) {
            notifyErrorHappened(t, dataListener);
        }
    }

    private void notifyErrorHappened(@NonNull Throwable t, final DataListener<T> dataListener) {
        if (BuildConfig.LOG_DEBUG) {
            ZKQLog.t(this, "notifyErrorHappened", t);
        }

        final NetworkException error = (t instanceof NetworkException) ? (NetworkException) t : new UnknownException(t);
        if (dataListener != null) {
            dataListener.onErrorHappened(error);
        }
    }

}
