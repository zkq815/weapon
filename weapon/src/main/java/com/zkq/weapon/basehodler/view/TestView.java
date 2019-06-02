package com.zkq.weapon.basehodler.view;

import android.content.Context;
import android.view.ViewGroup;

import com.tmall.wireless.tangram.structure.BaseCell;
import com.zkq.weapon.R;
import com.zkq.weapon.basehodler.adapter.AdapterPagerSlideInside;
import com.zkq.weapon.basehodler.datamodel.EdtionImageDataModel;
import com.zkq.weapon.basehodler.module.BaseEdtionModuleView;
import com.zkq.weapon.basehodler.module.SlideShowEdtionModule;
import com.zkq.weapon.basehodler.operation.BaseEdtionOperationModel;
import com.zkq.weapon.market.tools.ToolAndroid;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author zkq
 * time: 2019/5/30 1:51 AM
 * email: zkq815@126.com
 * desc:
 */
public class TestView extends BaseEdtionModuleView {

    private AdapterPagerSlideInside mAdapterPager;

    private SlideShowInsideEdtionView mEdtionView;

    private Context context;

    public TestView(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    private void getTest() {
        List<BaseEdtionOperationModel> list = new ArrayList<BaseEdtionOperationModel>(1);
        SlideShowEdtionModule module = new SlideShowEdtionModule();
        ArrayList<EdtionImageDataModel> templist = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            EdtionImageDataModel modelTemp = new EdtionImageDataModel();
            modelTemp.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559068148060&di=78736a70f2410d555522f19b138f46bc&imgtype=0&src=http%3A%2F%2Fp2.ssl.cdn.btime.com%2Ft0182ef20bbc3d3aec9.jpg%3Fsize%3D640x849");
            templist.add(modelTemp);
        }
        module.setDataList(templist);
        mEdtionView = new SlideShowInsideEdtionView(context, module);
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
            mAdapterPager = new AdapterPagerSlideInside(getContext(), mEdtionView.getViewPager()
                    , null, 1);
            mAdapterPager.init(module.getDataList(), mEdtionView.getCircleContainerView()
                    , R.drawable.ic_home_banner_point_sel
                    , R.drawable.ic_home_banner_point_default);
            mEdtionView.getViewPager().startAutoScroll();
        }
    }

    @Override
    public void cellInited(BaseCell cell) {
        super.cellInited(cell);
    }

    @Override
    public void postBindView(BaseCell cell) {
        super.postBindView(cell);
        getTest();
    }

    @Override
    public void postUnBindView(BaseCell cell) {
        super.postUnBindView(cell);
    }
}
