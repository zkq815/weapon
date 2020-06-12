package com.zkq.weapon.base;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import com.zkq.weapon.constants.WeaponConstants;
import com.zkq.weapon.customview.LoadingView;
import com.zkq.weapon.market.tools.ToolNet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author zkq
 * create:2018/11/16 10:17 AM
 * email:zkq815@126.com
 * desc: webviewFragment类逻辑处理
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
            final String title = bundle.getString(WeaponConstants.WEB_TITLE);
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
//        final boolean post = bundle.getBoolean(WeaponConstants.INTENT_POST, false);
        String url = bundle.getString(WeaponConstants.WEB_URL, "");
//        if (post) {
//            final String params = bundle.getString(WeaponConstants.INTENT_PARAMS, "");
//            try {
//                postUrl(url, params.getBytes(WeaponConstants.ENCODING_UTF8));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        } else {
            loadUrl(url);
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
