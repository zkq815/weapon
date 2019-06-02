package com.zkq.weapon.basehodler.module;

/**
 * @author zkq
 * create:2019/5/29 12:05 AM
 * email:zkq815@126.com
 * desc: 模块数据基类
 */
public abstract class BaseNativeEdtionModule {

    /**
     * 文字模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_TEXT = 3;
    /**
     * 纯图片等分模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_DIVISION = 4;
    /**
     * 图文等分模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_DIVISION_TEXT = 5;
    /**
     * 分割线模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_SPLIT = 6;
    /**
     * 轮播图模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_SLIDE_SHOW = 7;
    /**
     * 运费提示模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_SHIPPING_TIPS = 8;
    /**
     * 闪购模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_FLASH_SALE = 9;
    /**
     * 底部单品推荐模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_GOODS_PROMOTION = 10;
    /**
     * 纯图片非等分（2：1：1）模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_IR_DIVISION_SIX = 11;
    /**
     * 图文非等分（2：1：1）模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_IR_DIVISION_TEXT_SIX = 12;
    /**
     * 商品轮播模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_GOODS_SLIDE = 13;
    /**
     * 个性化图文等分模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_PERSONAL_DIVISION = 14;
    /**
     * 倒计时模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_TIME_LIMIT = 15;
    /**
     * 高分店铺模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_SCORE_STORE = 16;
    /**
     * 店铺列表展示模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_STORE_LIST = 17;
    /**
     * 频道模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_CHANNEL = 18;
    /**
     * 店铺列表展示模块中ViewMore在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_STORE_LIST_VIEW_MORE = 19;
    /**
     * 店铺列表展示模块中head在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_STORE_LIST_HEAD = 20;

    /**
     * 优惠券模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_COUPON = 21;
    /**
     * 秒杀模块在Adapter中的ViewType基数
     */
    final int ADAPTER_VIEW_TYPE_SEC_KILL = 22;

    /**
     * 底部推荐模块在Adapter中的ViewType基数
     */
    static final int ADAPTER_VIEW_TYPE_RECOMMEND_PAGER = 23;
    /**
     * id(index,1,2.3...)
     */
    private int id;
    /**
     * 标示类型模块
     */
    private String code;
    /**
     * 是否有上边框，0:无，1:有
     */
    private int hasTopBorder;
    /**
     * 模块id
     */
    private int moduleId;
    /**
     * 数据库中专题记录id
     */
    private int edtionId;
    /**
     * 是否显示内部分割线，0:隐藏，1:显示
     */
    private int showSplitLine;
    /**
     * 货币名称
     */
    private String currency;

    /**
     * 是否现在内部分割线
     *
     * @return true:显示
     */
    public boolean isShowSplitLine() {
        return showSplitLine == 1;
    }

    /**
     * 是否有上边框
     *
     * @return true:有
     */
    public boolean hasTopBorder() {
        return hasTopBorder == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getHasTopBorder() {
        return hasTopBorder;
    }

    public void setHasTopBorder(int hasTopBorder) {
        this.hasTopBorder = hasTopBorder;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getEdtionId() {
        return edtionId;
    }

    public void setEdtionId(int edtionId) {
        this.edtionId = edtionId;
    }

    public int getShowSplitLine() {
        return showSplitLine;
    }

    public void setShowSplitLine(int showSplitLine) {
        this.showSplitLine = showSplitLine;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return 获取Adapter中的视图类别
     */
    public abstract int getViewType();
}
