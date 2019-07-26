package com.zkq.weapon.customview;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @author zkq
 * create:2019/7/22 9:53 AM
 * email:zkq815@126.com
 * desc: viewpager切换动画
 */
public class StackTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setTranslationX(page.getWidth() * -position);
        page.setTranslationY(position < 0 ? position * page.getHeight() : 0f);
    }
}
