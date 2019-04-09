package com.zkq.alldemo.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zkq.alldemo.R;
import com.zkq.alldemo.fortest.actionbar.TopbarClickLinstener;


/**
 * Created by Administrator on 2016/12/2.
 */

public class MyToolBar extends RelativeLayout {

    private TextView title;
    private Button btnLeft,btnRight;
    private String strTitle,strLeft,strRight;
    private int colorLeft,colorLeftBackground,colorTitle,colorRight,colorRightBackground;
    private float sizeTitle;
    private LayoutParams paramsLeft,paramsTitle,paramsRight;
    private TopbarClickLinstener topbarClickLinstener;
    public static final int LEFT_BUTTON = 0;
    public static final int RIGHT_BUTTON = 1;

    public MyToolBar(Context context) {
        this(context,null);
    }

    public MyToolBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //从attrs文件中取出属性的值并存放在TypaArray中
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ToolBar);
        //从TypeArray中取出相应的数据，来进行赋值操作
        colorLeft = ta.getColor(R.styleable.ToolBar_leftTextColor,0);
        colorLeftBackground = ta.getColor(R.styleable.ToolBar_leftBackground,0);
        strLeft = ta.getString(R.styleable.ToolBar_leftText);

        colorTitle = ta.getColor(R.styleable.ToolBar_rightTextColor,0);
        strTitle = ta.getString(R.styleable.ToolBar_mytitle);
        sizeTitle = ta.getDimension(R.styleable.ToolBar_mytitleTextSize,10);

        colorRight = ta.getColor(R.styleable.ToolBar_rightTextColor,0);
        strRight = ta.getString(R.styleable.ToolBar_rightText);
        colorRightBackground = ta.getColor(R.styleable.ToolBar_rightBackground,0);
        ta.recycle();

        btnLeft = new Button(context);
        btnRight = new Button(context);
        title = new TextView(context);

        btnLeft.setText(strLeft);
        btnLeft.setTextColor(colorLeft);
        btnLeft.setBackgroundColor(colorLeftBackground);

        title.setText(strTitle);
        title.setTextColor(colorTitle);
        title.getPaint().setTextSize(sizeTitle);
//        title.setTextSize(sizeTitle);
        title.setGravity(Gravity.CENTER);

        btnRight.setText(strRight);
        btnRight.setTextColor(colorRight);
        btnRight.setBackgroundColor(colorRightBackground);

        paramsLeft = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(btnLeft,paramsLeft);

        paramsRight = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        paramsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(btnRight,paramsRight);

        paramsTitle = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        paramsTitle.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(title,paramsTitle);

        btnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                topbarClickLinstener.leftClick();
            }
        });

        btnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                topbarClickLinstener.rightClick();
            }
        });
    }

    public void setOnTopbarClickListener(TopbarClickLinstener listener){
        this.topbarClickLinstener = listener;
    }

    public void setButtonShowOrNot(int leftOrRight,boolean isShow){
        if(leftOrRight == LEFT_BUTTON){
            if(isShow){
                btnLeft.setVisibility(View.VISIBLE);
            }else{
                btnLeft.setVisibility(View.GONE);
            }
        }else if(leftOrRight == RIGHT_BUTTON){
            if(isShow){
                btnRight.setVisibility(View.VISIBLE);
            }else{
                btnRight.setVisibility(View.GONE);
                Log.e("zkq","消失了");
            }
        }
    }
}
