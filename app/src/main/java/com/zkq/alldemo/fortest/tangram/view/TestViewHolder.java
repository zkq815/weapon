/*
 * MIT License
 *
 * Copyright (c) 2018 Alibaba Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zkq.alldemo.fortest.tangram.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmall.wireless.tangram.structure.viewcreator.ViewHolderCreator;
import com.zkq.alldemo.R;
import com.zkq.weapon.basehodler.adapter.AdapterPagerSlideInside;
import com.zkq.weapon.basehodler.datamodel.EdtionImageDataModel;
import com.zkq.weapon.basehodler.module.BaseNativeEdtionModule;
import com.zkq.weapon.basehodler.module.SlideShowEdtionModule;
import com.zkq.weapon.basehodler.operation.BaseEdtionOperationModel;
import com.zkq.weapon.basehodler.view.SlideShowInsideEdtionView;
import com.zkq.weapon.market.tools.ToolAndroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SunQiang
 */
public class TestViewHolder extends ViewHolderCreator.ViewHolder {
    private AdapterPagerSlideInside mAdapterPager;

    private SlideShowInsideEdtionView mEdtionView;
    private Context ctx;

    public TestViewHolder(Context ctx) {
        super(ctx);
        this.ctx = ctx;
    }

    private void getTest() {
        SlideShowEdtionModule module = new SlideShowEdtionModule();
        ArrayList<EdtionImageDataModel> templist = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            EdtionImageDataModel modelTemp = new EdtionImageDataModel();
            modelTemp.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559068148060&di=78736a70f2410d555522f19b138f46bc&imgtype=0&src=http%3A%2F%2Fp2.ssl.cdn.btime.com%2Ft0182ef20bbc3d3aec9.jpg%3Fsize%3D640x849");
            templist.add(modelTemp);
        }
        module.setDataList(templist);
        mEdtionView = new SlideShowInsideEdtionView(mContext, module);
        show();
        initViewPager(module);
    }

    private void show() {
        ViewGroup.LayoutParams containerLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ToolAndroid.getPhoneWidth(mEdtionView.getContext()));
        mEdtionView.setLayoutParams(containerLp);
    }

    /**
     * 初始化ViewPager
     *
     * @param module 模块数据
     */
    private void initViewPager(SlideShowEdtionModule module) {
        if (mAdapterPager == null) {
//            setDataPosition(module.getDataList(), module);
//            mAdapterPager = new AdapterPagerSlideInside(getContext(), mEdtionView.getViewPager()
//                    , getOnClickListener(), getEdtionId());
            mAdapterPager = new AdapterPagerSlideInside(ctx, mEdtionView.getViewPager()
                    , null, 1);
            mAdapterPager.init(module.getDataList(), mEdtionView.getCircleContainerView()
                    , com.zkq.weapon.R.drawable.ic_home_banner_point_sel
                    , com.zkq.weapon.R.drawable.ic_home_banner_point_default);
            mEdtionView.getViewPager().startAutoScroll();
        }
    }

    @Override
    protected void onRootViewCreated(View view) {
//        textView = (TextView) view;
        getTest();
    }
}
