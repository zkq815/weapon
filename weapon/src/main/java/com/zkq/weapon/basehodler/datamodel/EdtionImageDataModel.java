package com.zkq.weapon.basehodler.datamodel;

/**
 * @author zkq
 * create:2019/5/29 2:58 PM
 * email:zkq815@126.com
 * desc: 纯图片数据
 */
public class EdtionImageDataModel {
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 标题
     */
    private String title;
    /**
     * 广告跟踪码
     */
    private String adCode;
    /**
     * 模块内排序
     */
    private int sort;
    /**
     * 广告位类型，1表示原生专题，2表示网页(m站，或者h5个性化专题)，3表示h5(活动)，4跳转到原生单品，5 深度链接,10 类目页，11 特殊码
     */
    private int type;
    /**
     * type=1时广告跳转专题ID
     */
    private int edtionId;
    /**
     * type=2，3，5 时广告跳转地址
     */
    private String adUrl;
    /**
     * 商品ID,广告跳转到商品详情，类型4 必定返回
     */
    private int goodsId;
    /**
     * 商品Id拼接,用于图文二等分曝光事件用
     *
     * @since V6.13
     */
    private String goodsIdStr;
    /**
     * 模块内部数据位置,从0开始
     */
    private int dataPosition;
    /**
     * 所在的模块位置
     */
    private int modulePosition;
    /**
     * 模块id
     */
    private int moduleId;
    /**
     * Bi算法
     */
    private String biTrackingCode;
    /**
     * 分类ID(type=10时)
     */
    private int catId;
    /**
     * 分类名称(type=10时)
     */
    private String catName;
    /**
     * 特殊码(type=11时)
     */
    private String specCode;

    /**
     * 个性化banner类型（0表示对照组；1表示未开启个性化banner；2表示开启个性化banner但是BI未返回；3表示BI有返回，但无banner图；4表示BI有返回，但是商品被过滤；5表示BI有返回并显示个性化banner图
     */
    private String bannerBIType;

    /**
     * bi返回的goodsId，用于个性化banner
     */
    private String bannerGoodsId;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEdtionId() {
        return edtionId;
    }

    public void setEdtionId(int edtionId) {
        this.edtionId = edtionId;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getDataPosition() {
        return dataPosition;
    }

    public void setDataPosition(int dataPosition) {
        this.dataPosition = dataPosition;
    }

    public int getModulePosition() {
        return modulePosition;
    }

    public void setModulePosition(int modulePosition) {
        this.modulePosition = modulePosition;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getBiTrackingCode() {
        return biTrackingCode;
    }

    public void setBiTrackingCode(String biTrackingCode) {
        this.biTrackingCode = biTrackingCode;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public String getGoodsIdStr() {
        return goodsIdStr;
    }

    public void setGoodsIdStr(String goodsIdStr) {
        this.goodsIdStr = goodsIdStr;
    }

    public String getBannerBIType() {
        return bannerBIType;
    }

    public void setBannerBIType(String bannerBIType) {
        this.bannerBIType = bannerBIType;
    }

    public String getBannerGoodsId() {
        return bannerGoodsId;
    }

    public void setBannerGoodsId(String bannerGoodsId) {
        this.bannerGoodsId = bannerGoodsId;
    }
}
