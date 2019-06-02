package com.zkq.weapon.basehodler.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zkq.weapon.R;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

/**
 * desc: ViewPager无限循环适配器，特点：<br/>
 * 1，提供 默认样式、默认当前页数 <br/>
 * 2，自定义 分页样式、首次当前页数 <br/>
 * 3，调用简单，外界只需调用相关{@link #init(List)} ()}即可 <br/>
 * 4，实现了ViewPager.OnPageChangeListener监听 <br/>
 * <p>
 * 注意：<br/>
 * 1，构造函数中的ViewPager对象，通过它的getCurrentItem()获取选中的位置是不正确的，请调用
 * {@link #getCurrPosition()} <br/>
 * 2，指定翻页图片后导致页数样式怪异，请重写 {@link #initPageItemHintView(List, ViewGroup, int)} ()} <br/>
 * 3，销毁时，最好主动调用{@link #clear()}释放内存 <br/>
 * time: 2015-2-3 下午7:39:10 <br/>
 * author: 居廉 <br/>
 * since V 3.1.0 <br/>
 */
public abstract class AdapterPager4GoodsDetailGallery<DataType> extends AdapterPagerBase<DataType> implements
        ViewPager.OnPageChangeListener {
    /**
     * viewPager控件
     */
    private ViewPager mViewPager;

    /**
     * 注意：<br/>
     * ViewPager的getCurrentItem()返回的位置是不准确的<br/>
     * 如果获取当前ViewPager选中的位置，请调用{@link #getCurrPosition()}
     */
    private int currPosition;

    /**
     * 页数提示容器
     */
    private ViewGroup mItemHintContainer;

    /**
     * 页数：选中图片
     */
    private int mSelImgResId = R.drawable.m_base_ic_exchange_select;

    /**
     * 页数：未选中图片
     */

    private int mUnSelImgResId = R.drawable.m_base_ic_exchange_normal;

    /**
     * 页数布局
     */
    private LinearLayout.LayoutParams itmeHintlayoutParams;

    /**
     * 上下文
     */
    protected Context mContext;

    // /////////////////////////////
    public AdapterPager4GoodsDetailGallery(Context context, ViewPager viewPager) {
        this.mContext = context;
        this.mViewPager = viewPager;

        // 设置翻页监听器
        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 获取ViewPager单个View试图
     *
     * @param ctx        上下文
     * @param container  容器
     * @param itemEntity 指定position位置的数据实体类
     * @param position   当前位置
     * @return 单个View视图
     */
    public abstract View getVpItemView(Context ctx, ViewGroup container, DataType itemEntity, int position);

    /**
     * 初始化适配器并连接ViewPager <br/>
     * 无页数提示 <br/>
     *
     * @param dataLists 数据集合
     */
    public void init(List<DataType> dataLists) {
        if (dataLists == null) {
            return;
        }

        this.mDataLists = dataLists;
        initViewPager(0);
    }

    /**
     * 初始化适配器并连接ViewPager<br/>
     * 有页数提示、默认第一页、页数默认圆圈样式<br/>
     *
     * @param dataLists
     * @param itemHintContainer
     */
    public void init(ArrayList<DataType> dataLists, ViewGroup itemHintContainer) {
        init(dataLists, itemHintContainer, 0);
    }

    /**
     * 初始化适配器并连接ViewPager<br/>
     * 有页数提示、默认第一页、自定义页数图片样式<br/>
     *
     * @param dataLists
     * @param itemHintContainer
     * @param selImgResId
     * @param unSelImgResId
     */
    public void init(List<DataType> dataLists, ViewGroup itemHintContainer, int selImgResId, int unSelImgResId) {
        init(dataLists, itemHintContainer, selImgResId, unSelImgResId, 0);
    }

    /**
     * 初始化适配器并连接ViewPager<br/>
     * 有页数提示、自定义显示第几页、自定义页数图片样式<br/>
     *
     * @param dataLists
     * @param itemHintContainer
     * @param selImgResId
     * @param unSelImgResId
     * @param position
     */
    public void init(List<DataType> dataLists, ViewGroup itemHintContainer, int selImgResId, int unSelImgResId,
                     int position) {
        this.mSelImgResId = selImgResId;
        this.mUnSelImgResId = unSelImgResId;
        init(dataLists, itemHintContainer, position);
    }

    /**
     * 初始化适配器并连接ViewPager<br/>
     * 有页数提示、自定义显示第几页、页数默认圆圈样式<br/>
     *
     * @param dataLists
     * @param itemHintContainer
     * @param position
     */
    public void init(List<DataType> dataLists, ViewGroup itemHintContainer, int position) {
        if (dataLists == null || itemHintContainer == null) {
            return;
        }

        this.mDataLists = dataLists;
        this.mItemHintContainer = itemHintContainer;

        initPageItemHintView(dataLists, itemHintContainer, position);
        initViewPager(position);
    }

    /**
     * @return 获取当前选中位置(视图下标)
     */
    public int getCurrPosition() {
        return currPosition;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        if (position == 0) {
            view = initItemViewFacade(container, position, getCount());
        } else {
            view = initItemViewFacade(container, position % mDataLists.size(), getCount());
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        // ToolLog.v("MyShopBag", "=======getCount() -> count:" + count);

        if (count <= 0) {
            return 0;
        }

        if (count == 1) {
            return count;
        }

        return (count + 2);
    }

    /**
     * 向ViewPager中添加View
     *
     * @param container
     * @param position
     * @param size
     * @return
     */
    private View initItemViewFacade(ViewGroup container, int position, int size) {
        View view = null;
        if (size == 1) {
            position = 0;
            view = getVpItemView(mContext, container, mDataLists.get(position), position);
        } else if ((position >= 0) && (size > 1)) {
            DataType itemEntity = mDataLists.get(position);
            view = getVpItemView(mContext, container, itemEntity, position);
        } else if (position == 0) {
            position = mDataLists.size() - 1;
            DataType itemEntity = mDataLists.get(position);
            view = getVpItemView(mContext, container, itemEntity, position);
        }
        return view;
    }

    /**
     * 获取指定下标的数据
     *
     * @param position
     * @return
     */
    public DataType getItemData(int position) {
        if (mDataLists == null || mDataLists.isEmpty()) {
            return null;
        }

        if (position < 0 || (position >= mDataLists.size())) {
            return null;
        }

        return mDataLists.get(position);
    }

    /**
     * 初始化 页数视图
     *
     * @param datasLists
     * @param itemHintContainer
     * @param position
     */
    public void initPageItemHintView(List<DataType> datasLists, ViewGroup itemHintContainer, int position) {
        int size = datasLists.size();
        //只存在一页时，不启动页数提示
        if (size <= 1) {
            return;
        }
        itmeHintlayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        itmeHintlayoutParams.setMargins(10, 0, 0, 0);//间距
        itmeHintlayoutParams.gravity = Gravity.CENTER;

        for (int i = 0; i < size; i++) {
            ImageView ivItemHint = new ImageView(mContext);
            ivItemHint.setLayoutParams(itmeHintlayoutParams);

            if (i == position) {
                ivItemHint.setImageResource(mSelImgResId);
            } else {
                ivItemHint.setImageResource(mUnSelImgResId);
            }

            itemHintContainer.addView(ivItemHint);
        }
    }

    // /// ViewPager.OnPageChangeListener begin////////
    @Override
    public final void onPageSelected(int position) {
        //ToolLog.v("MyShopBag", "=======onPageSelected() -> position:" + position + ", mViewsCacheMapSize:"
        //		+ mViewsCacheMap.size());
        currPosition = position - 1;
        if (getCount() > 1) { // 多于1，才会循环跳转
            if (position < 1) { // 首位之前，跳转到末尾
                position = mDataLists.size();
                mViewPager.setCurrentItem(position, false);
                return;
            } else if (position > mDataLists.size()) { // 末位之后，跳转到首位
                position = 1;
                mViewPager.setCurrentItem(position, false);
                return;
            }

            //ToolLog.v("MyShopBag",
            //		"====onPageSelected() -> currPosition:" + currPosition + ", size:" + mDataLists.size());
            onPageSelected(currPosition, mDataLists);
            setItemViewHint(currPosition);
        }
    }

    public void onPageSelected(int position, List<DataType> mDataLists) {
        // 子类扩展
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 子类扩展
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 子类扩展
    }
    // /// ViewPager.OnPageChangeListener end////////

    /**
     * 释放内存
     */
    public void clear() {
        if (mViewPager != null) {
            mViewPager.removeAllViews();
        }

        itmeHintlayoutParams = null;

        if (mItemHintContainer != null) {
            mItemHintContainer.removeAllViews();
            mItemHintContainer = null;
        }
    }

    /**
     * 将是配置设置到ViewPager中，选中View
     */
    private void initViewPager(int position) {
        mViewPager.setAdapter(this);
        position = position + 1;
        mViewPager.setCurrentItem(position, false);
    }

    /**
     * 选中翻页
     *
     * @param position
     */
    private void setItemViewHint(int position) {
        if (mItemHintContainer == null) {
            return;
        }

        int count = mItemHintContainer.getChildCount();

        for (int i = 0; i < count; i++) {
            ImageView imageView = (ImageView) mItemHintContainer.getChildAt(i);

            if (imageView != null) {
                int resId = (position == i) ? mSelImgResId : mUnSelImgResId;
                imageView.setImageResource(resId);
            }
        }
    }

}
