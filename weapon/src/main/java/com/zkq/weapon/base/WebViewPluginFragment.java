package com.zkq.weapon.base;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.zkq.weapon.customview.LoadingView;
import com.zkq.weapon.constants.Constants;
import com.zkq.weapon.market.tools.ToolNet;

/**
 * @author zkq
 * create:2018/11/16 10:17 AM
 * email:zkq815@126.com
 * desc:
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
            final String title = bundle.getString(Constants.WEB_TITLE);
            if (!TextUtils.isEmpty(title)) {
                setTitle(title);
            }

            final boolean connected = ToolNet.isAvailable(getContext());
            if (connected) {
                load(bundle);
            } else {
                mLoadingLayout.fail(LoadingView.Type.NO_NETWORK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean connected = ToolNet.isAvailable(getContext());
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
//        final boolean post = bundle.getBoolean(Constants.INTENT_POST, false);
//        String url = bundle.getString(Constants.WEB_URL, "");
//        if (post) {
//            final String params = bundle.getString(Constants.INTENT_PARAMS, "");
//            try {
//                postUrl(url, params.getBytes(Constants.ENCODING_UTF8));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        } else {
//            loadUrl(url);
//        }
    }

    @Override
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
