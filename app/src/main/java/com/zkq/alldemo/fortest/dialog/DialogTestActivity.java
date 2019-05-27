package com.zkq.alldemo.fortest.dialog;

import android.app.Dialog;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityDialogTestBinding;
import com.zkq.weapon.base.BaseActivity;

public class DialogTestActivity extends BaseActivity implements View.OnClickListener{
    private ActivityDialogTestBinding mBinding;
    Dialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_dialog_test);
        initDialog();
        mBinding.tvDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void initDialog(){
        if(null==dialog){
            dialog = new Dialog(this, R.style.my_dialog);
            LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
            root.findViewById(R.id.tv_dialog_delete).setOnClickListener(this);
            root.findViewById(R.id.tv_dialog_cancel).setOnClickListener(this);
            dialog.setContentView(root);
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
//            dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
            dialog.setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            lp.x = 0; // 新位置X坐标
            lp.y = 0; // 新位置Y坐标
            lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
            root.measure(0, 0);
            lp.height = root.getMeasuredHeight();
            dialogWindow.setAttributes(lp);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //删除
            case R.id.tv_dialog_delete:
                dialog.dismiss();
                break;
            // 取消
            case R.id.tv_dialog_cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

}
