package com.zkq.weapon.base;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.zkq.weapon.R;
import com.zkq.weapon.constants.WeaponConstants;
import com.zkq.weapon.market.tools.ToolString;

/**
 * @author zkq
 * create:2018/11/15 4:06 PM
 * email:zkq815@126.com
 * desc: 网页显示Activity
 */
public class WebViewPluginActivity extends BaseActivity {

    private WebViewPluginFragment mWebFragment;
    private boolean mShowRefresh = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebFragment = new WebViewPluginFragment();
        mShowRefresh = getIntent().getBooleanExtra(WeaponConstants.SHOW_REFRESH, false);
        final Bundle data = new Bundle();
        String targetUrl = getIntent().getStringExtra(WeaponConstants.WEB_URL);
        String pushMsgUrl = getIntent().getStringExtra(WeaponConstants.WEB_PUSH_URL);
        if (ToolString.isNotEmpty(pushMsgUrl)) {
            targetUrl = pushMsgUrl;
        }

        data.putString(WeaponConstants.WEB_URL, targetUrl);
        data.putString(WeaponConstants.WEB_TITLE, getIntent().getStringExtra(WeaponConstants.WEB_TITLE));
        mWebFragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, mWebFragment).commit();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && mWebFragment.webCanGoBack()){
            mWebFragment.webGoBack();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

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
//            case R.id.refresh:
//                if (mWebFragment != null && mWebFragment.isAdded()) {
//                    mWebFragment.reload();
//                }
//                return true;
            case android.R.id.home:
                super.onBackPressed();
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mWebFragment.webCanGoBack()) {
            mWebFragment.webGoBack();
            return;
        }
        super.onBackPressed();
    }
//
//    @Override
//    protected boolean showBack() {
//        return true;
//    }
}
