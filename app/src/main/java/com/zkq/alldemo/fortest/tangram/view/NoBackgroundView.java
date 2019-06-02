package com.zkq.alldemo.fortest.tangram.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;
import com.tmall.wireless.tangram.support.ExposureSupport;
import com.zkq.weapon.market.tools.ToolSize;

import java.util.Locale;

/**
 * 没有设置背景的自定义View
 *
 * @author SunQiang
 * @since 2019-04-19
 */
public class NoBackgroundView extends LinearLayout implements ITangramViewLifeCycle {
    private TextView mTextView;

    public NoBackgroundView(Context context) {
        super(context);
        init();
    }

    public NoBackgroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NoBackgroundView(Context context, @Nullable AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        int padding = ToolSize.dp2Px(getContext(), 10);
        setPadding(padding, padding, padding, padding);
        mTextView = new TextView(getContext());
        mTextView.setTextSize(12);
        addView(mTextView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void cellInited(BaseCell cell) {
        setOnClickListener(cell);
        if (cell.serviceManager != null) {
            ExposureSupport exposureSupport = cell.serviceManager.getService(ExposureSupport.class);
            if (exposureSupport != null) {
                exposureSupport.onTrace(this, cell, cell.type);
            }
        }
    }

    @Override
    public void postBindView(BaseCell cell) {
        setBackgroundColor(0xff00c3f5);
        mTextView.setText(String.format(Locale.CHINA, "%s%d: %s", getClass().getSimpleName(),
                cell.pos, cell.optParam("text")));
    }

    @Override
    public void postUnBindView(BaseCell cell) {

    }
}
