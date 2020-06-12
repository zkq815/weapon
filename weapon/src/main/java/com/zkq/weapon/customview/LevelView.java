package com.zkq.weapon.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zkq.weapon.base.LevelViewBean;
import com.zkq.weapon.market.tools.ToolScreen;
import com.zkq.weapon.market.tools.ToolSize;

import java.util.ArrayList;

/**
 * @author zkq
 * create:2018/11/16 10:20 AM
 * email:zkq815@126.com
 * desc:简单的线性布局，使得子view自动换行排列
 */
public class LevelView extends View {
    private Context mContext;
    private int colorAlphaGreen;
    private int colorBlue;
    private int colorWhite;
    private int colorAlphaRed;
    private int colorRed;
    private int colorGray;
    private int colorBlack;
    private Rect textRect;

    private int leftFirst;
    private int leftSecond;
    private int leftThird;
    private int paddingTop;
    private int paddingLeft;
    private int paddingRight;
    private int rectHeight;
    private int radius;
    private int rectRadius;

    private Paint mTextPaint;
    private Paint mRectPaint;
    private Paint mLinePaint;
    private Paint mCirclePaint;

    private int text12;
    private int text14;

    private LevelViewBean mBean;
    private LevelViewBean testBean;

    int heightAll = 0;
    int heightArray[];
    private boolean hasThirdSun = false;

    public LevelView(Context context) {
        this(context, null);
    }

    public LevelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mContext = getContext();
        colorAlphaGreen = Color.parseColor("#6608B452");
        colorWhite = Color.parseColor("#ffffff");
        colorBlue = Color.parseColor("#178CEA");
        colorAlphaRed = Color.parseColor("#66E32932");
        colorRed = Color.parseColor("#CCE32932");
        colorGray = Color.parseColor("#669E9E9E");
        colorBlack = Color.parseColor("#252525");
        text12 = ToolSize.dp2Px(mContext, 12);
        text14 = ToolSize.dp2Px(mContext, 14);
        rectRadius = ToolSize.dp2Px(mContext, 4);
        radius = ToolSize.dp2Px(mContext, 2);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(colorBlack);
        mTextPaint.setTextSize(text12);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(colorGray);
        mCirclePaint.setStrokeWidth(1);

        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setTextSize(ToolSize.dp2Px(mContext, 18));

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(colorGray);

        leftFirst = 0;
        leftSecond = ToolScreen.getScreenWidth(mContext) / 3 * 1;
        leftThird = ToolScreen.getScreenWidth(mContext) / 3 * 2;
        paddingTop = ToolSize.dp2Px(mContext, 15);
        paddingLeft = ToolSize.dp2Px(mContext, 25);
        rectHeight = ToolSize.dp2Px(mContext, 30);
        setTestBean();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureHeight();
        setMeasuredDimension(getMeasuredWidth(), height);
    }

    private int measureHeight() {
        Log.e("zkq","真实测量高度=="+ getAllHeight());
        return getAllHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        heightAll = 0;

        if (null == mBean || null == mBean.getPlateType()) {
            return;
        }
        heightArray = new int[mBean.getChain().size()];
        //先判断是否存在第三级结构
        for (int i = 0; i < mBean.getChain().size(); i++) {
            if(null != mBean.getChain().get(i).getChildren() || mBean.getChain().get(i).getChildren().isEmpty()){
                continue;
            }

            for (int j = 0; j < mBean.getChain().get(i).getChildren().size(); j++) {
                if(!"".equals(mBean.getChain().get(i).getChildren().get(j).getName())){
                    hasThirdSun = true;
                    break;
                }
            }
            if(hasThirdSun){
                break;
            }
        }

        for (int i = 0; i < mBean.getChain().size(); i++) {
            mTextPaint.setTextSize(text12);
            int rectHeightStart = getHeightStartForRect(i);
            int heightThird = 0;
            //先绘制第三级
            LevelViewBean.ChainBean chainBean = mBean.getChain().get(i);
            if (hasThirdSun) {
                //不存在第三级
                if(heightThird == 0){
                    heightArray[i] = heightThird;
                }else{
                    heightArray[i] = heightThird + paddingTop;
                }
            } else {
                //存在第三级
                for (int j = 0; j < chainBean.getChildren().size(); j++) {
                    LevelViewBean.ChainBean.ChildrenBean thirdBean = chainBean.getChildren().get(j);
                    textRect = new Rect();
                    String name = thirdBean.getName();
                    mTextPaint.getTextBounds(name, 0, name.length(), textRect);
                    heightThird += textRect.height() > rectHeight ? textRect.height() : rectHeight;
                    canvas.drawText(name, leftThird + paddingLeft
                            , (heightThird - (rectHeight - textRect.height()) / 2) + rectHeightStart, mTextPaint);
                    canvas.drawLine(leftThird
                            , heightThird + rectHeightStart
                            , leftThird  + paddingLeft + textRect.width(), heightThird + rectHeightStart
                            , mLinePaint);

                    //绘制二三级之间的连线
                    if(j == 0){
                        if(chainBean.getChildren().size() ==1){

                        }else{
                            canvas.drawLine(leftThird
                                    , heightThird + rectHeightStart
                                    , leftThird, heightThird + rectHeightStart + rectHeight
                                    , mLinePaint);
                        }
                    }else{
                        if(j != chainBean.getChildren().size()-1){
                            canvas.drawLine(leftThird
                                    , heightThird + rectHeightStart
                                    , leftThird, heightThird + rectHeightStart + rectHeight
                                    , mLinePaint);
                        }
                    }

                }
                if(heightThird == 0){
                    heightArray[i] = heightThird;
                }else{
                    heightArray[i] = heightThird + paddingTop;
                }
            }

            //绘制第二级
            textRect = new Rect();
            String name = chainBean.getPlateType().getName();
            mRectPaint.setColor(colorAlphaGreen);
            mTextPaint.getTextBounds(name, 0, name.length(), textRect);
            if (heightArray[i] == 0) {
                heightArray[i] = rectHeight + paddingTop;
                heightAll += heightArray[i];
            } else if (heightArray[i] != 0) {
                heightAll += heightArray[i];
                if (chainBean.getChildren().size() == 1) {
                    //需要单独处理只有1个第三级的情况
                } else {

                }
            }

            mRectPaint.setColor(colorGray);
            canvas.drawRoundRect(leftSecond, rectHeightStart + heightArray[i] / 2 - rectHeight / 2
                    , leftThird - paddingLeft, rectHeightStart + heightArray[i] / 2 + rectHeight / 2
                    , rectRadius, rectRadius, mRectPaint);
            //绘制边框前的圆圈
            canvas.drawCircle(leftSecond - radius , rectHeightStart + heightArray[i] / 2,radius,mCirclePaint);
            if(chainBean.getChildren().size() != 0){
                //绘制边框后的圆圈
                canvas.drawCircle(leftThird - paddingLeft + radius , rectHeightStart + heightArray[i] / 2,radius,mCirclePaint);
                //第二级与第三级的连接线
                canvas.drawLine(leftThird - paddingLeft + 2* radius
                        , rectHeightStart + heightArray[i] / 2
                        , leftThird, rectHeightStart + heightArray[i] / 2
                        , mLinePaint);
            }

            mTextPaint.setColor(colorBlack);
            int secondTextStart = leftSecond + (((leftThird - paddingLeft) - leftSecond) - textRect.width())/2;
            canvas.drawText(name, secondTextStart
                    , rectHeightStart + heightArray[i] / 2 + textRect.height() / 2, mTextPaint);

            //第二级与第一级的连接线
            canvas.drawLine(leftSecond - paddingTop
                    , rectHeightStart + heightArray[i] / 2
                    , leftSecond - 2* radius, rectHeightStart + heightArray[i] / 2
                    , mLinePaint);
            Log.e("zkq","第"+ i +"区块高度=="+ heightArray[i]);

        }
        //绘制第一级别
        textRect = new Rect();
        String name = mBean.getPlateType().getName();
        mRectPaint.setColor(colorBlue);
        mTextPaint.setTextSize(text14);
        mTextPaint.setColor(colorWhite);
        mTextPaint.getTextBounds(name, 0, name.length(), textRect);
        canvas.drawRoundRect(leftFirst + paddingTop, heightAll / 2 - (rectHeight / 2 * 1.2f)
                , leftSecond - paddingLeft, heightAll / 2 + (rectHeight / 2 * 1.2f)
                , rectRadius, rectRadius, mRectPaint);
        canvas.drawCircle(leftSecond - paddingLeft + radius , heightAll / 2,radius,mCirclePaint);
        int firstTextStart = (leftFirst + paddingTop) + (((leftSecond - paddingLeft) - (leftFirst + paddingTop)) - textRect.width())/2;
        canvas.drawText(name, firstTextStart
                , heightAll / 2 + textRect.height()/2, mTextPaint);
        canvas.drawLine(leftSecond - paddingLeft + 2*radius
                , heightAll / 2
                , leftSecond - paddingTop, heightAll / 2
                , mLinePaint);

        if(heightArray.length != 0){
            int first = heightArray[0];
            int last = getHeightStartForRect(heightArray.length -1);
            canvas.drawLine(leftSecond - paddingTop
                    , first/2
                    , leftSecond - paddingTop, last + heightArray[heightArray.length -1]/2
                    , mLinePaint);
        }
    }

    private int getAllHeight(){
        heightAll = 0;
        if (null == mBean || null == mBean.getPlateType()) {
            return 0;
        }
        heightArray = new int[mBean.getChain().size()];
        //先判断是否存在第三级结构
        for (int i = 0; i < mBean.getChain().size(); i++) {
            if(null != mBean.getChain().get(i).getChildren() || mBean.getChain().get(i).getChildren().isEmpty()){
                continue;
            }
            if(!"".equals(mBean.getChain().get(i).getChildren().get(0).getName())){
                hasThirdSun = true;
                break;
            }
        }

        for (int i = 0; i < mBean.getChain().size(); i++) {
            mTextPaint.setTextSize(text12);
            int heightThird = 0;
            //先绘制第三级
            LevelViewBean.ChainBean chainBean = mBean.getChain().get(i);
            if (hasThirdSun) {
                //不存在第三级
                if(heightThird == 0){
                    heightArray[i] = heightThird;
                }else{
                    heightArray[i] = heightThird + paddingTop;
                }
                heightAll += heightArray[i];
            } else {
                //存在第三级
                for (int j = 0; j < chainBean.getChildren().size(); j++) {
                    LevelViewBean.ChainBean.ChildrenBean thirdBean = chainBean.getChildren().get(j);
                    textRect = new Rect();
                    String name = thirdBean.getName();
                    mTextPaint.getTextBounds(name, 0, name.length(), textRect);
                    heightThird += textRect.height() > rectHeight ? textRect.height() : rectHeight;

                }
                if(heightThird == 0){
                    heightArray[i] = rectHeight + paddingTop;
                }else{
                    heightArray[i] = heightThird + paddingTop;
                }
            }
            heightAll += heightArray[i];
        }
        return heightAll;
    }

    private int getHeightStartForRect(int position) {
        int heightStart = 0;
        for (int i = position; i > 0 && i < heightArray.length; i--) {
            heightStart += heightArray[i - 1];
        }
        return heightStart;
    }

    public void setData(LevelViewBean bean) {
        mBean = bean;
        invalidate();
        forceLayout();
        requestLayout();
    }

    private void setTestBean() {
        int[] sunThird = {0, 0, 0, 0, 0};
        testBean = new LevelViewBean();
        //一级标签
        LevelViewBean.PlateTypeBean plateTypeBean = new LevelViewBean.PlateTypeBean();
        plateTypeBean.setName("测试一级");
        testBean.setPlateType(plateTypeBean);

        //二级分类
        ArrayList<LevelViewBean.ChainBean> chainBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            LevelViewBean.ChainBean chainBean = new LevelViewBean.ChainBean();
            LevelViewBean.ChainBean.PlateTypeBeanX plateTypeBeanX = new LevelViewBean.ChainBean.PlateTypeBeanX();
            plateTypeBeanX.setName("测试二级" + i);
            chainBean.setPlateType(plateTypeBeanX);


            ArrayList<LevelViewBean.ChainBean.ChildrenBean> childrenBeanList = new ArrayList<>();
            for (int j = 0; j < sunThird[i]; j++) {
                LevelViewBean.ChainBean.ChildrenBean childrenBean = new LevelViewBean.ChainBean.ChildrenBean();
                childrenBean.setName(i + "的测试三级" + j);

                childrenBeanList.add(childrenBean);
            }
            chainBean.setChildren(childrenBeanList);
            chainBeanList.add(chainBean);
        }

        testBean.setChain(chainBeanList);
        testBean.setSkipUrl("www.baidu.com");
        testBean.setShowH5(true);
//        mBean = testBean;
    }



}