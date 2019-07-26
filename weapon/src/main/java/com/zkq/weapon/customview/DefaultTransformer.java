package com.zkq.weapon.customview;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @author zkq
 * create:2019/7/22 9:53 AM
 * email:zkq815@126.com
 * desc: viewpager切换动画
 */
public class DefaultTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {
        float alpha = 0;
        if (0 <= position && position <= 1) {
            alpha = 1 - position;
        } else if (-1 < position && position < 0) {
            alpha = position + 1;
        }
        view.setAlpha(alpha);
        view.setTranslationX(view.getWidth() * -position);
        float yPosition = position * view.getHeight();
        view.setTranslationY(yPosition);
    }
}
