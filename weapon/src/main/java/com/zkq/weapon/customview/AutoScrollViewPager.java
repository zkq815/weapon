package com.zkq.weapon.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;


import com.zkq.weapon.constants.WeaponConstants;

import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author zkq
 * create:2019/5/28 11:56 PM
 * email:zkq815@126.com
 * desc: 自动滚动的ViewPager, 默认不会自动滚动,滚动需要调用{@link #startAutoScroll()}
 */
public class AutoScrollViewPager extends ViewPager {

    public static final int DEFAULT_INTERVAL = WeaponConstants.VIEW_PAGER_AUTO_SCROLL_INTERVAL;

    private static final int SCROLL_WHAT = 0;

    private boolean mIsAutoScroll = false;
    private AutoScrollHandler mHandler;
    private boolean mIsStopByTouch = false;

    public AutoScrollViewPager(Context context) {
        super(context);
        init();
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void startAutoScroll() {
        startAutoScroll(DEFAULT_INTERVAL);
    }

    public void startAutoScroll(long delayTimeInMills) {
        mIsAutoScroll = true;
        sendScrollMessage(delayTimeInMills);
    }

    public void stopAutoScroll() {
        mIsAutoScroll = false;
        mHandler.removeMessages(SCROLL_WHAT);
    }

    public boolean isAutoScrollRunning() {
        return mIsAutoScroll;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_DOWN && mIsAutoScroll) {
            mIsStopByTouch = true;
            stopAutoScroll();
        } else if (action == MotionEvent.ACTION_UP && mIsStopByTouch) {
            mIsStopByTouch = false;
            startAutoScroll();
        }
        return super.dispatchTouchEvent(ev);
    }

    private void init() {
        mHandler = new AutoScrollHandler();
    }

    private void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        if (adapter == null || adapter.getCount() <= 1) {
            return;
        }
        setCurrentItem(++currentItem, true);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        mHandler.removeMessages(SCROLL_WHAT);
        mHandler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    private class AutoScrollHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mIsAutoScroll) {
                switch (msg.what) {
                    case SCROLL_WHAT:
                        //自动滑动
                        scrollOnce();
                        mHandler.sendEmptyMessageDelayed(SCROLL_WHAT, DEFAULT_INTERVAL);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
