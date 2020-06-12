package com.zkq.weapon.basehodler.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zkq.weapon.basehodler.datamodel.EdtionImageDataModel;
import com.zkq.weapon.basehodler.module.BaseNativeEdtionModule;
import com.zkq.weapon.market.glide.ImageLoader;
import com.zkq.weapon.market.tools.ToolApp;
import com.zkq.weapon.market.tools.ToolList;
import com.zkq.weapon.market.tools.ToolScreen;

import java.util.List;

import androidx.annotation.CallSuper;
import androidx.fragment.app.FragmentActivity;

/**
 * @author zkq
 * create:2019/5/28 11:49 PM
 * email:zkq815@126.com
 * desc: 模块Holder基类
 */
public abstract class BaseNativeEdtionHolder extends BaseViewHolder {

    private Context mContext;

    private View.OnClickListener mOnClickListener;
    /**
     * 是否为第一次显示，true:是 <br/>
     * 主要防止复用时，一些方法重复使用<br/>
     * 会在{@link #refreshViews(BaseNativeEdtionModule)}中重置
     */
    private boolean mIsFirstShow = true;

    //页面Id,用于生成spm值
    private int mEdtionId;

    //根SPM值
    protected String mRootSpm;

    /**
     * 俄罗斯方块版本类型，0:review，1:release	是
     */
    private int edtionVType;
    /**
     * 获取专题类型 首页 or Boutique
     */
    private int mEdtionEntryType;

    public BaseNativeEdtionHolder(Context ctx, View itemView) {
        super(itemView);
        this.mContext = ctx;
    }

    public Context getContext() {
        if (mContext instanceof FragmentActivity && ((FragmentActivity) mContext).isDestroyed()) {
            return ToolApp.getAppContext();
        }
        return mContext;
    }

    /**
     * 显示视图界面
     *
     * @param edtionModule 模块数据实例
     */
    public abstract void showViews(BaseNativeEdtionModule edtionModule);

    /**
     * 只在刷新Adapter时调用
     *
     * @param edtionModule 模块数据实例
     */
    @CallSuper
    public void refreshViews(BaseNativeEdtionModule edtionModule) {
        mIsFirstShow = true;
        //子类重写，重写时一定要调用 super()方法
    }

    public View.OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public int getEdtionId() {
        return mEdtionId;
    }

    public int getEdtionVType() {
        return edtionVType;
    }

    public void setEdtionId(int edtionId) {
        this.mEdtionId = edtionId;
    }

    public void setRootSpm(String rootSpm) {
        this.mRootSpm = rootSpm;
    }

    public void setEdtionVType(int edtionVType) {
        this.edtionVType = edtionVType;
    }

    public int getEdtionEntryType() {
        return mEdtionEntryType;
    }

    public void setEdtionEntryType(int edtionEntryType) {
        mEdtionEntryType = edtionEntryType;
    }

    /**
     * 生命周期：暂停
     */
    public void onPauseActive() {
        //子类重写
    }

    /**
     * 生命周期：重新启动
     */
    public void onResume() {
        //子类重写
    }

    /**
     * 生命周期：销毁
     */
    public void onDestroy() {
        //子类重写
    }

    /**
     * 展示在屏幕上
     */
    public void onViewAttachedToWindow() {
        //子类重写
    }

    /**
     * 滑出屏幕
     */
    public void onViewDetachedFromWindow() {
        //子类重写
    }

    /**
     * holder被回收时
     */
    public void onViewRecycled() {
        //子类重写
    }

    /**
     * 设置模块内部数据的位置参数
     *
     * @param dataList     内部数据集合
     * @param edtionModule 模块实例
     */
    protected void setDataPosition(List<?> dataList, BaseNativeEdtionModule edtionModule) {
        if (ToolList.isNullOrEmpty(dataList) || !mIsFirstShow) {
            return;
        }
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i) instanceof EdtionImageDataModel) {
                EdtionImageDataModel model = (EdtionImageDataModel) dataList.get(i);
                //顺序从0开始算
                model.setDataPosition(i);
                model.setModulePosition(edtionModule.getId());
                model.setModuleId(edtionModule.getModuleId());
            }
        }
        mIsFirstShow = false;
    }

    /**
     * 初始化视图高度
     *
     * @param whRatio 宽高比
     */
    protected void initViewLayoutParams(View containerView, String whRatio) {
        initViewLayoutParams(containerView, getViewHeight(whRatio, containerView));
    }

    /**
     * 初始化视图高度
     */
    protected void initViewLayoutParams(View containerView) {
        initViewLayoutParams(containerView, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 初始化视图高度
     *
     * @param height 控件高度
     */
    protected void initViewLayoutParams(View containerView, int height) {
        ViewGroup.LayoutParams containerLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , height);
        containerView.setLayoutParams(containerLp);
    }

    /**
     * 根据宽高比，获取高度
     *
     * @param whRatio 宽高比
     * @return 高度
     */
    protected int getViewHeight(String whRatio, View containerView) {
//        int[] ratioNumArr = BusinessCommon.parseColonSplit(whRatio);
        int[] ratioNumArr = {1,1};
        if (ratioNumArr.length == 2) {
            return (ToolScreen.getScreenWidth(containerView.getContext()) * ratioNumArr[1]) / ratioNumArr[0];
        }
        return 0;
    }

    /**
     * 清除图片控件中图片信息
     *
     * @param imageViews 图片视图控件数组
     */
    protected void clearImageForGlide(ImageView... imageViews) {
        if (imageViews == null || imageViews.length == 0 || getContext() == null) {
            return;
        }

        if (getContext() instanceof Activity && (((Activity) getContext()).isDestroyed())) {
            return;
        }

        for (ImageView imageView : imageViews) {
            ImageLoader.getInstance().load("")
                    .with(getContext())
                    .into(imageView);
        }
    }

}
