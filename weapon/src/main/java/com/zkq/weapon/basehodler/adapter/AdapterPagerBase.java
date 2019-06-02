package com.zkq.weapon.basehodler.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.zkq.weapon.market.tools.ToolList;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author zkq
 * create:2019/5/28 11:45 PM
 * email:zkq815@126.com
 * desc: PagerAdapter基类
 */
public class AdapterPagerBase<DataType> extends PagerAdapter {
    /**
     * 数据源
     */
    protected List<DataType> mDataLists;


    @Override
    public int getCount() {
        if (mDataLists == null) {
            return 0;
        }

        return mDataLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    /**
     * 清空数据
     */
    public void clearData() {
        if (ToolList.isNullOrEmpty(mDataLists)) {
            return;
        }
        mDataLists.clear();
    }

}
