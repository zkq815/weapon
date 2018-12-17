package com.zkq.weapon.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.zkq.weapon.customview.LoadingView;
import com.zkq.weapon.market.toast.ZToast;
import com.zkq.weapon.market.util.ZLog;
import com.zkq.weapon.R;

import java.lang.ref.WeakReference;

/**
 * @author zkq
 * create:2018/11/15 3:51 PM
 * email:zkq815@126.com
 * desc: webviewFragment基类
 */
public abstract class BaseWebPluginFragment extends BaseFragment {

    private static final String SEPARATOR = " ";
    private FrameLayout mWebViewContainer;
    private WebView mWebView;
    protected LoadingView mLoadingLayout;
    private static final int WEB_FLYME_LOGIN = 6;

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

    public void webGoBack() {
        if (null != mWebView) {
            if (!mWebView.canGoBack()) {
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
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 设置支持Javascript
        ws.setJavaScriptEnabled(true);
        StringBuilder tag = new StringBuilder();
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
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
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
                return super.shouldInterceptRequest(view, url);
            }

            /**
             * api 21 之后启用
             * */
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

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
                        getActivity().runOnUiThread(() -> ZToast.show("检测到你的手机没有安装微信"));
                    }
                    return true;
                }

                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLoadingLayout.success();
                ZLog.e(BaseWebPluginFragment.this, "onReceivedError: " + request + " " + error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
                ZLog.e(BaseWebPluginFragment.this, "onReceivedSslError: " + view.getUrl() + " " + error);
            }
        });
    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case WEB_FLYME_LOGIN:
                if (getActivity() != null) {
                    mLoadingLayout.load();
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
}
