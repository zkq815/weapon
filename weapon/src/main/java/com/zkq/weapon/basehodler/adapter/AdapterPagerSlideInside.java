package com.zkq.weapon.basehodler.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zkq.weapon.R;
import com.zkq.weapon.basehodler.datamodel.EdtionImageDataModel;
import com.zkq.weapon.market.glide.glideimpl.GlideImageLoader;

import java.util.List;

import androidx.viewpager.widget.ViewPager;

/**
 * @author zkq
 * create:2019/5/28 11:39 PM
 * email:zkq815@126.com
 * desc: 内部滑动适配器
 */
public class AdapterPagerSlideInside extends AdapterPager4Cycle<EdtionImageDataModel> {

    private ViewGroup.LayoutParams layoutParams;
    private View.OnClickListener mOnClickListener;
    private String imageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559068148060&di=78736a70f2410d555522f19b138f46bc&imgtype=0&src=http%3A%2F%2Fp2.ssl.cdn.btime.com%2Ft0182ef20bbc3d3aec9.jpg%3Fsize%3D640x849";
    private int mEdtionId;

    public AdapterPagerSlideInside(Context context, ViewPager viewPager, View.OnClickListener onClickListener
            , int edtionId) {
        super(context, viewPager);
        layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.mOnClickListener = onClickListener;
        this.mEdtionId = edtionId;
    }

    @Override
    public View getVpItemView(Context ctx, ViewGroup container, EdtionImageDataModel itemEntity, int position) {
        ImageView imageView = new ImageView(ctx);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setId(R.id.native_edtion_ad);
        imageView.setOnClickListener(mOnClickListener);
        imageView.setBackgroundResource(R.drawable.ic_refresh);
        GlideImageLoader.getInstance().load(imageUrl)
                .with(ctx)
                .placeholder(R.drawable.ic_refresh)
                .into(imageView);
//        imageView.setTag(R.id.key_tag_glide_ad_image, itemEntity);
        return imageView;
    }

    @Override
    public void onPageSelected(int position, List<EdtionImageDataModel> dataLists) {

    }

}
