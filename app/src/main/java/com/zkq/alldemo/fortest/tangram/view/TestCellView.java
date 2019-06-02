package com.zkq.alldemo.fortest.tangram.view;

import android.content.Context;
import android.util.AttributeSet;

import com.zkq.weapon.market.glide.glideimpl.GlideImageLoader;
import com.zkq.weapon.market.tools.ToolAndroid;
import com.zkq.weapon.market.tools.ToolSize;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * 自定义model
 *
 * @author SunQiang
 * @since 2019-04-19
 */
public class TestCellView extends AppCompatImageView {

    public TestCellView(Context context) {
        super(context);
        init();
    }

    public TestCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setScaleType(ScaleType.FIT_XY);
    }

    public void setImageUrl(String url) {
        url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559068148060&di=78736a70f2410d555522f19b138f46bc&imgtype=0&src=http%3A%2F%2Fp2.ssl.cdn.btime.com%2Ft0182ef20bbc3d3aec9.jpg%3Fsize%3D640x849";
        GlideImageLoader.getInstance().load(url).with(getContext()).into(this);
    }

}
