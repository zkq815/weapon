package com.zkq.alldemo.fortest.gsvideo.gsdemo.gsyvideoplayer.holder;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.zkq.alldemo.R;
import com.zkq.alldemo.fortest.gsvideo.gsdemo.gsyvideoplayer.model.VideoModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GUO on 2015/12/3.
 */
public class RecyclerItemViewHolder extends RecyclerItemBaseHolder {

    public final static String TAG = "RecyclerView2List";

    protected Context context = null;

    @BindView(R.id.list_item_container)
    FrameLayout listItemContainer;

    @BindView(R.id.list_item_btn)
    ImageView listItemBtn;

    ImageView imageView;

    private GSYVideoHelper smallVideoHelper;

    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;

    public RecyclerItemViewHolder(Context context, View v) {
        super(v);
        this.context = context;
        ButterKnife.bind(this, v);
        imageView = new ImageView(context);
    }

    public void onBind(final int position, VideoModel videoModel) {

        //增加封面
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.xxx1);

        smallVideoHelper.addVideoPlayer(position, imageView, TAG, listItemContainer, listItemBtn);

        listItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallVideoHelper.setPlayPositionAndTag(position, TAG);
                getRecyclerBaseAdapter().notifyDataSetChanged();
                //listVideoUtil.setLoop(true);
                String url;
                if (position % 2 == 0) {
                    url = "https://res.exexm.com/cw_145225549855002";
                } else {
                    url = "http://video.7k.cn/app_video/20171202/6c8cf3ea/v.m3u8.mp4";
                }
                //listVideoUtil.setCachePath(new File(FileUtils.getPath()));

                gsySmallVideoHelperBuilder.setVideoTitle("title " + position).setUrl(url);

                smallVideoHelper.startPlay();

                //必须在startPlay之后设置才能生效
                //listVideoUtil.getGsyVideoPlayer().getTitleTextView().setVisibility(View.VISIBLE);
            }
        });
    }


    public void setVideoHelper(GSYVideoHelper smallVideoHelper, GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder) {
        this.smallVideoHelper = smallVideoHelper;
        this.gsySmallVideoHelperBuilder = gsySmallVideoHelperBuilder;
    }
}





