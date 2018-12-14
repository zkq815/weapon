package com.zkq.alldemo.fortest.fingertest;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zkq.alldemo.R;


/**
 * @author zkq
 * time: 2018/11/28:11:48
 * email: zkq815@126.com
 * desc:
 */
public class CheckPopupWindow extends PopupWindow {
    private ImageView ivPic;
    private TextView tvTips;
    public CheckPopupWindow(final Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fingerprint_check_popup, null);
        tvTips = (TextView) view.findViewById(R.id.tv_tips);
        ivPic = (ImageView) view.findViewById(R.id.iv_fingerprint);
        view.findViewById(R.id.tv_close).setOnClickListener(v->dismiss());
        setFocusable(true);
        setOutsideTouchable(false);
        setClippingEnabled(true);
        update();
        ColorDrawable dw = new ColorDrawable(0);
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimationAlpha);
        setContentView(view);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }


    public void success(){
        ivPic.setImageResource(R.drawable.fingerprint_normal);
        tvTips.setText("认证通过");
        dismiss();
    }

    public void fail(){
        tvTips.setText("认证失败，请重试");
        ivPic.setImageResource(R.drawable.fingerprint_guide);
    }
}
