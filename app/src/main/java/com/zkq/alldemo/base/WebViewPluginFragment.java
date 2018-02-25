package com.zkq.alldemo.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;


import com.zkq.alldemo.common.Constants;
import com.zkq.alldemo.costomview.LoadingView;
import com.zkq.weapon.util.MobileNetworkUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author huxiaoyuan
 * @since 16/4/21
 */
public class WebViewPluginFragment extends BaseWebPluginFragment {

    private boolean mIsLoaded = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setData(getArguments());
    }

    public void setData(final Bundle bundle) {
        if (bundle != null) {
            final String title = bundle.getString(Constants.INTENT_DATA_TITLE);
            if (!TextUtils.isEmpty(title)) {
                setTitle(title);
            }

            final boolean connected = MobileNetworkUtils.isNetAvailable(getContext());
            if (connected) {
                load(bundle);
            } else {
                mLoadingLayout.fail(LoadingView.Type.NO_NETWORK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean connected = MobileNetworkUtils.isNetAvailable(getContext());
                        if (connected) {
                            load(bundle);
                        } else {
                            getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    }
                });
            }
        }
    }

    void load(@NonNull final Bundle bundle) {
//        loading.setVisibility(View.VISIBLE);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(isAdded())
//                    loading.setVisibility(View.GONE);
//            }
//        },5000);


//        mLoadingLayout.load();
        final boolean post = bundle.getBoolean(Constants.INTENT_POST, false);

        String url = bundle.getString(Constants.INTENT_DATA_URL, "");
        if (post) {
            final String params = bundle.getString(Constants.INTENT_PARAMS, "");
            try {
                postUrl(url, params.getBytes(Constants.ENCODING_UTF8));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            loadUrl(url);
        }
    }


    public void postUrl(final String url, final byte[] postData) {
        super.postUrl(url, postData);
        mIsLoaded = true;
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
        mIsLoaded = true;
    }

    @Override
    public void onNetChange(boolean connected, boolean isInitialSticky) {
        if (!mIsLoaded) {
            if (connected) {
                if (getArguments() != null) {
                    load(getArguments());
                }
            }
        }
    }


}
