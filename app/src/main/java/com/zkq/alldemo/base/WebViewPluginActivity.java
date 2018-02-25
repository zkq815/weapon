package com.zkq.alldemo.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.zkq.alldemo.R;
import com.zkq.alldemo.common.Constants;
import com.zkq.alldemo.common.H5CallParam;
import com.zkq.alldemo.util.StringUtil;

public class WebViewPluginActivity extends BaseActionBarActivity {

    private WebViewPluginFragment mWebFragment;
    private boolean mShowRefresh = false;
//    private String pageName = TrackerConstants.PageInfo.WEBVIEW_PLUGIN_ACTIVITY.code;
    private String pageName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPageStart(pageName);
        mWebFragment = new WebViewPluginFragment();
        Intent it = getIntent();

        mShowRefresh = getIntent().getBooleanExtra(Constants.SHOW_REFRESH, false);

        final Bundle data = new Bundle();

        String targetUrl = getIntent().getStringExtra(Constants.INTENT_DATA_URL);
        String pushMsgUrl = getIntent().getStringExtra(Constants.INTENT_PUSH_DATA);
        if (!StringUtil.isEmpty(pushMsgUrl)) {
            targetUrl = pushMsgUrl;
        }

        Uri uri = getIntent().getData();
        if (uri != null && H5CallParam.H5_WEB_PATH.equals(uri.getPath())){
            String adfrom = uri.getQueryParameter(H5CallParam.H5_PARAM_AD_FROM);
            String from = uri.getQueryParameter(H5CallParam.H5_PARAM_FROM);
            targetUrl = uri.getQueryParameter(H5CallParam.H5_PARAM_WEB_URL);
            mShowRefresh = uri.getBooleanQueryParameter(H5CallParam.H5_PARAM_WEB_REFRESH,false);
//            OplogHelper.onH5Start(PageIndex.appweb.toString(), adfrom,from);
        }

        data.putString(Constants.INTENT_DATA_URL, targetUrl);
        data.putString(Constants.INTENT_DATA_TITLE, getIntent().getStringExtra(Constants.INTENT_DATA_TITLE));
        mWebFragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, mWebFragment).commit();
    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//         /*
//        调用支付宝sdk的时候，会弹出支付界面，按返回，会取消支付，同时返回上一级页面,
//        所以需要屏蔽返回上一级页面操作。
//         */
//        if(keyCode == KeyEvent.KEYCODE_BACK && mWebFragment.webCanGoBack() && !mWebFragment.webIsPayment()){
//            mWebFragment.webGoBack();
//            return true;
//        }
//        return super.onKeyUp(keyCode, event);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        final MenuItem menuItem = menu.findItem(R.id.refresh);
        menuItem.setVisible(mShowRefresh);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;

            case R.id.refresh:
                if (mWebFragment != null && mWebFragment.isAdded()) {
                    mWebFragment.reload();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mWebFragment.webCanGoBack() && !mWebFragment.webIsPayment()) {
            mWebFragment.webGoBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected boolean showBack() {
        return true;
    }

    @Override
    protected void onDestroy() {
        onPageStop(pageName);
        super.onDestroy();
    }
}
