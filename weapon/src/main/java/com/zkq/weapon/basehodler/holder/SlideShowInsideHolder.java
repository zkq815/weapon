package com.zkq.weapon.basehodler.holder;

import android.content.Context;
import android.view.View;

import com.zkq.weapon.R;
import com.zkq.weapon.basehodler.adapter.AdapterPagerSlideInside;
import com.zkq.weapon.basehodler.adapter.IViewHolder4Impress;
import com.zkq.weapon.basehodler.module.BaseNativeEdtionModule;
import com.zkq.weapon.basehodler.module.SlideShowEdtionModule;
import com.zkq.weapon.basehodler.view.SlideShowInsideEdtionView;

/**
 * @author zkq
 * create:2019/5/28 11:54 PM
 * email:zkq815@126.com
 * desc: 轮播内部滑动模块Holder
 */
public class SlideShowInsideHolder extends BaseNativeEdtionHolder implements IViewHolder4Impress {

    private AdapterPagerSlideInside mAdapterPager;

    private SlideShowInsideEdtionView mEdtionView;

    public SlideShowInsideHolder(Context ctx, View itemView) {
        super(ctx, itemView);
        mEdtionView = (SlideShowInsideEdtionView) itemView;
    }

    @Override
    public void showViews(BaseNativeEdtionModule edtionModule) {
        if (edtionModule instanceof SlideShowEdtionModule) {
            SlideShowEdtionModule module = (SlideShowEdtionModule) edtionModule;
            initViewLayoutParams(mEdtionView, module.getWhRatio());
            initViewPager(module);
        }
    }

    @Override
    public void refreshViews(BaseNativeEdtionModule edtionModule) {
        super.refreshViews(edtionModule);
        if (mAdapterPager != null) {
            mEdtionView.getViewPager().stopAutoScroll();
            mAdapterPager.clear();
            mAdapterPager.clearData();
            mAdapterPager.notifyDataSetChanged();
            mAdapterPager = null;
        }
    }

    @Override
    public void onPauseActive() {
        mEdtionView.getViewPager().stopAutoScroll();
    }

    @Override
    public void onResume() {
        if (!mEdtionView.getViewPager().isAutoScrollRunning()) {
            mEdtionView.getViewPager().startAutoScroll();
        }
    }

    @Override
    public void onViewDetachedFromWindow() {
        onPauseActive();
    }

    @Override
    public void onViewAttachedToWindow() {
        onResume();
    }

    /**
     * 初始化ViewPager
     *
     * @param module 模块数据
     */
    private void initViewPager(SlideShowEdtionModule module) {
        if (mAdapterPager == null) {
            setDataPosition(module.getDataList(), module);
            mAdapterPager = new AdapterPagerSlideInside(getContext(), mEdtionView.getViewPager()
                    , getOnClickListener(), getEdtionId());
            mAdapterPager.init(module.getDataList(), mEdtionView.getCircleContainerView()
                    , R.drawable.ic_home_banner_point_sel
                    , R.drawable.ic_home_banner_point_default);
            mEdtionView.getViewPager().startAutoScroll();
        }
    }
}
