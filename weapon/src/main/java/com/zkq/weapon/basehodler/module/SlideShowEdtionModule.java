package com.zkq.weapon.basehodler.module;


import com.zkq.weapon.basehodler.datamodel.EdtionImageDataModel;

import java.util.ArrayList;

/**
 * @author zkq
 * create:2019/5/29 12:05 AM
 * email:zkq815@126.com
 * desc: 轮播图模块数据
 */
public class SlideShowEdtionModule extends BaseNativeEdtionModule {

    /**
     * 宽高比(2:3)
     */
    private String whRatio;
    /**
     * 模块内数据列表
     */
    private ArrayList<EdtionImageDataModel> dataList;
    /**
     * 上留白高度
     */
    private int marginTop;
    /**
     * 下留白高度
     */
    private int marginBottom;
    /**
     * 图片宽高比
     */
    private String imgWhRatio;

    public String getWhRatio() {
        return whRatio;
    }

    public void setWhRatio(String whRatio) {
        this.whRatio = whRatio;
    }

    public ArrayList<EdtionImageDataModel> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<EdtionImageDataModel> dataList) {
        this.dataList = dataList;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public String getImgWhRatio() {
        return imgWhRatio;
    }

    public void setImgWhRatio(String imgWhRatio) {
        this.imgWhRatio = imgWhRatio;
    }

    @Override
    public int getViewType() {
        StringBuilder sb = new StringBuilder();
        //例如：slideShow_2;
//        String code = getCode();
//        sb.append(ADAPTER_VIEW_TYPE_SLIDE_SHOW).append(code.charAt(code.length() - 1));
//        return Integer.valueOf(sb.append(getId()).toString());
        return 1;
    }
}
