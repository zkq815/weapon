package com.zkq.alldemo.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.zkq.alldemo.BuildConfig;
import com.zkq.alldemo.R;
import com.zkq.alldemo.common.AppBaseInfo;
import com.zkq.alldemo.common.AppConstantsUrl;
import com.zkq.alldemo.util.ZKQLog;
import com.zkq.alldemo.util.ZKQToast;
import com.zkq.weapon.customview.LoadingView;

import java.lang.ref.WeakReference;

/**
 * @author huxiaoyuan
 * @since 16/3/23
 */
public abstract class BaseWebPluginFragment extends BaseFragment {

    //服务端标识H5页面来源的UA
    //公开版本UA  PUBLIC_UA_TAG = "  MzmApp";
    //Flyme预装版本UA FLYME_PRE_TAG ="FlymePreApp";
    private static final String PUBLIC_UA_TAG = "MzmApp";
    private static final String FLYME_PRE_TAG = "FlymePreApp";
    private static final String APP_VERSION_CODE = "AppVersionCode";
    private static final String SEPARATOR = " ";

    private FrameLayout mWebViewContainer;
    private WebView mWebView;
    protected LoadingView mLoadingLayout;

    private static final int WEB_FLYME_LOGIN = 6;

    private boolean mIsPayment = false;

    private PayTask mTask;

    private MyHandler mHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new MyHandler(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_plugin_web, container, false);
        mTask = new PayTask(getActivity());
        setupView(root);
        return root;
    }

    @Override
    public void onDestroy() {
        mWebViewContainer.removeAllViews();
        if (null != mWebView) {
            mWebView.removeAllViews();
            mWebView.setTag(null);
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
        }

        if (mHandler != null) {
            mHandler.removeMessages(WEB_FLYME_LOGIN);
        }

        super.onDestroy();
    }

    public boolean webCanGoBack() {
        return null != mWebView && mWebView.canGoBack();
    }

    public boolean webIsPayment() {
        return mIsPayment;
    }

    public void webGoBack() {
        if (null != mWebView) {
            if (mWebView.getUrl().contains(AppConstantsUrl.APP_PAY_SUCCESS.getUrl())) {
                getActivity().finish();
            } else {
                mWebView.goBack();
            }
        }
    }

    private void setupView(View v) {
        mLoadingLayout = (LoadingView) v.findViewById(R.id.loading_layout);
        mWebViewContainer = (FrameLayout) v.findViewById(R.id.web_view_container);
        mWebView = new WebView(getContext());
        mWebViewContainer.addView(mWebView);
        initWebSetting();
        mWebView.computeScroll();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSetting() {
        final WebSettings ws = mWebView.getSettings();
        ws.setAppCacheEnabled(true);
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scale = dm.densityDpi;
        if (scale == 240) {
            mWebView.setInitialScale(94);
        } else if (scale == 160) {
            mWebView.setInitialScale(68);
        } else {
            mWebView.setInitialScale(80);
        }
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);// 设置缓存模式
        // 设置支持Javascript
        ws.setJavaScriptEnabled(true);
        String appVer = BuildConfig.VERSION_NAME;
        StringBuilder tag = new StringBuilder();
        tag.append(PUBLIC_UA_TAG).append("/").append(appVer);
        if (AppBaseInfo.getInstance().isMzPlatform) {//flyme系统
            tag.append(SEPARATOR).append(FLYME_PRE_TAG).append("/").append(appVer);
        }
        tag.append(SEPARATOR).append(APP_VERSION_CODE).append("/").append(BuildConfig.VERSION_CODE)//需要展示微信支付，传递appversioncode参数;
                .append(" MzChannel/").append("渠道");
        final String ua = ws.getUserAgentString() + SEPARATOR + tag.toString();
        ws.setUserAgentString(ua);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setSaveFormData(false);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        ws.setAllowFileAccess(true);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setDisplayZoomControls(false);
        ws.setTextZoom(100);
        mWebView.addJavascriptInterface(new FlymePay(), "FlymePay");
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                ZKQLog.e("zkq", "购物车 jsAlert  url==" + url);
                return super.onJsAlert(view, url, message, result);
            }


        });
//        mWebView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
//                                        long contentLength) {
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setTitle(view.getTitle());
                mLoadingLayout.success();
            }

            /**
             * 支持api 19 20
             * */
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                //判断是否是H5购物车页面，若是，跳转到原生购物车
                if (url.contains(AppConstantsUrl.APP_H5_CART.getUrl())) {
//                    getActivity().startActivity(new Intent(getActivity(), ShoppingCartActivity.class));
//                    getActivity().finish();
                }
                return super.shouldInterceptRequest(view, url);
            }

            /**
             * api 21 之后启用
             * */
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                //判断是否是H5购物车页面，若是，跳转到原生购物车
//                if(request.getUrl().toString().contains(AppConstantsUrl.APP_H5_CART.getUrl())){
//                    getActivity().startActivity(new Intent(getActivity(), ShoppingCartActivity.class));
//                    getActivity().finish();
//                }
//
//                return super.shouldInterceptRequest(view, request);
//            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //微信支付链接拦截
                if (url.startsWith("weixin://wap/pay?")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(url);
                    intent.setData(uri);
                    try {
                        getActivity().startActivity(intent);
                    } catch (Exception e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ZKQToast.show("检测到你的手机没有安装微信");
                            }
                        });
                    }
                    return true;
                }
                //判断是否是H5购物车页面，若是，跳转到原生购物车
                if (url.contains(AppConstantsUrl.APP_H5_CART.getUrl())) {
//                    getActivity().startActivity(new Intent(getActivity(), ShoppingCartActivity.class));
                    getActivity().finish();
                    return true;
                }

                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else
//                    if (WebViewLoginHelper.isLoginLink(url)/*url.startsWith(AppConstantsUrl.APP_H5_LOGIN_URL.getUrl())*/) {
//                    mHandler.sendEmptyMessage(WEB_FLYME_LOGIN);
//                    return true;
//                } else
                    if (url.contains(AppConstantsUrl.APP_H5_DETAIL_HTML_KEY.getUrl())) {
//                    Intent it = new Intent(getContext(), DetailActivity.class);
//                    it.putExtra(Constants.INTENT_DATA_FROM_PAGE, PageIndex.appweb.toString());
//                    it.putExtra(Constants.INTENT_DATA_URL, AppRequestUrl.APP_GET_DETAIL_DATA_URL.getUrl() + url);
//                    getActivity().startActivity(it);
                    return true;
                }

                final String ex = mTask.fetchOrderInfoFromH5PayUrl(url);
                if (!TextUtils.isEmpty(ex)) {
                    mIsPayment = true;
                    new Thread(new Runnable() {
                        public void run() {
                            if (getActivity() != null) {
                                final H5PayResultModel result = mTask.h5Pay(ex, true);
                                mIsPayment = false;
                                if (!TextUtils.isEmpty(result.getReturnUrl())) {
                                    final Activity activity = getActivity();
                                    if (null != activity && !activity.isFinishing()) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                loadUrl(result.getReturnUrl());
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }).start();
                    return true;
                }
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLoadingLayout.success();

                if (BuildConfig.LOG_DEBUG) {
                    ZKQLog.e(BaseWebPluginFragment.this, "onReceivedError: " + request + " " + error);
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                if (BuildConfig.LOG_DEBUG) {
                    ZKQLog.e(BaseWebPluginFragment.this, "onReceivedSslError: " + view.getUrl() + " " + error);
                }
            }
        });

    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case WEB_FLYME_LOGIN:
                if (getActivity() != null) {
                    mLoadingLayout.load();

//                    final LoginHelper.ILoginListener loginListener = new LoginHelper.ILoginListener() {
//                        @Override
//                        public void result(@NonNull LoginHelper.Code code, String msg) {
//                            if (isAdded()) {
//                                if (LoginHelper.Code.SUCCESS == code) {
//                                    if (null != mWebView) {
//                                        mWebView.reload();
//                                    }
//                                    mLoadingLayout.success();
//                                } else {
//                                    mLoadingLayout.fail(LoadingView.Type.LOADING_FAIL, new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            mHandler.sendEmptyMessage(WEB_FLYME_LOGIN);
//                                        }
//                                    });
//                                }
//                            }
//                        }
//                    };
//
//                    LoginHelper.startLogin(getActivity(), true, loginListener);
                }
                break;
            default:
                break;
        }
    }

    private static class MyHandler extends Handler {

        private final WeakReference<BaseWebPluginFragment> ref;

        private MyHandler(BaseWebPluginFragment f) {
            this.ref = new WeakReference<>(f);
        }

        @Override
        public void handleMessage(Message msg) {
            if (null != ref.get() && ref.get().isAdded()) {
                ref.get().handleMessage(msg);
            }
        }
    }

    public void postUrl(final String url, final byte[] postData) {
        if (null != mWebView) {
            mWebView.postUrl(url, postData);
        }
    }

    public void loadUrl(final String url) {
        if (null != mWebView) {
            mWebView.loadUrl(url);
        }
    }

    public void reload() {
        if (mWebView != null) {
            mWebView.reload();
        }
    }

    //flyme支付
    class FlymePay {
        @JavascriptInterface
        public boolean pay(int code, String param) {
//            if (getActivity() != null && code == 0 && !TextUtils.isEmpty(LoginHelper.getToken())) {
//                MzOpenPayPlatform.extOpenPay(getActivity(), param, LoginHelper.getToken(), new ExtPayListener() {
//                    @Override
//                    public void onPayResult(int code, String order, String errorMsg) {
//                        switch (code) {
//                            case PayResultCode.PAY_SUCCESS:
//                                String url = getReturnUrl(order);
//                                if (!TextUtils.isEmpty(url)) {
//                                    mWebView.loadUrl(url);
//                                }
//                                break;
//                            case PayResultCode.PAY_ERROR_CODE_DUPLICATE_PAY:
//                                break;
//                            case PayResultCode.PAY_ERROR_CANCEL:
//                                // 用户取消
//                                break;
//                            case PayResultCode.PAY_ERROR_INVALID_TOKEN:
//                                mHandler.sendEmptyMessage(WEB_FLYME_LOGIN);
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                });
//            } else if (TextUtils.isEmpty(LoginHelper.getToken())) {
//                XLog.e("Flyme Error", "Token is null");
//                mHandler.sendEmptyMessage(WEB_FLYME_LOGIN);
//            }
            return true;
        }

        /**
         * orderInfo:xxxxxx&return_url=http://testpay.meizu.com:8080/wap/success&xxxxx&xxxxxx
         */
        private static final String RETURN_URL = "return_url";

        private String getReturnUrl(String orderInfo) {
            if (TextUtils.isEmpty(orderInfo)) {
                return null;
            }
            String[] keyValues = orderInfo.split("&");
            for (String keyValue : keyValues) {
                if (keyValue.startsWith(RETURN_URL)) {
                    return keyValue.substring(RETURN_URL.length() + 1);
                }
            }
            return null;
        }
    }
}
