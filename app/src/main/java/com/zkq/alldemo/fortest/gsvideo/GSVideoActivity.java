package com.zkq.alldemo.fortest.gsvideo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zkq.alldemo.R;
import com.zkq.weapon.base.BaseActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GSVideoActivity extends BaseActivity {
    OrientationUtils orientationUtils;
    private StandardGSYVideoPlayer videoPlayer;
    private RecyclerView rvVideoList;
    private Adapter mAdapter;
    private Activity mActivity;
    String source1 = "https://zhibo.58ag8.com/host02/gs20190719.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsvideo);
        mActivity = this;
        initRvList();
        initVideo();
    }

    private void initRvList(){
        rvVideoList = findViewById(R.id.rv_video_list);
        rvVideoList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter(this);
        rvVideoList.setAdapter(mAdapter);
//        rvVideoList.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {}
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int lastVisibleItem = firstVisibleItem + visibleItemCount;
//                //大于0说明有播放
//                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
//                    //当前播放的位置
//                    int position = GSYVideoManager.instance().getPlayPosition();
//                    //对应的播放列表TAG
//                    if (GSYVideoManager.instance().getPlayTag().equals(ListNormalAdapter.TAG)
//                            && (position < firstVisibleItem || position > lastVisibleItem)) {
//                        if(GSYVideoManager.isFullState(mActivity)) {
//                            return;
//                        }
//                        //如果滑出去了上面和下面就是否，和今日头条一样
//                        GSYVideoManager.releaseAllVideos();
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });

    }

    private void initVideo() {
        videoPlayer =  (StandardGSYVideoPlayer)findViewById(R.id.detail_player);

//        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
//        String source1 = "https://zhibo.58ag8.com/host02/gs20190719.mp4";
        videoPlayer.setUp(source1, true, "测试视频");

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_launcher);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        videoPlayer.startPlayLogic();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private Activity activity;

        Adapter(Activity activity){
            this.activity = activity;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemVideoHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.item_gsvideo, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof ItemVideoHolder){
                ItemVideoHolder itemVideoHolder = (ItemVideoHolder) holder;
//                itemVideoHolder.video.setUp(source1, true, "测试视频"+position);
//
//                //增加封面
//                ImageView imageView = new ImageView(getBaseContext());
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setImageResource(R.mipmap.ic_launcher);
//                itemVideoHolder.video.setThumbImageView(imageView);
//                //增加title
//                itemVideoHolder.video.getTitleTextView().setVisibility(View.VISIBLE);
//                //设置返回键
//                itemVideoHolder.video.getBackButton().setVisibility(View.VISIBLE);
//                //设置旋转
//                orientationUtils = new OrientationUtils(activity, videoPlayer);
//                //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
//                itemVideoHolder.video.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        orientationUtils.resolveByClick();
//                    }
//                });
//                //是否可以滑动调整
//                itemVideoHolder.video.setIsTouchWiget(true);
//                //设置返回按键功能
//                itemVideoHolder.video.getBackButton().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onBackPressed();
//                    }
//                });

                itemVideoHolder.video.setUpLazy(source1, true, null, null, "这是title");
                //增加title
                itemVideoHolder.video.getTitleTextView().setVisibility(View.GONE);
                //设置返回键
                itemVideoHolder.video.getBackButton().setVisibility(View.GONE);
                //设置全屏按键功能
                itemVideoHolder.video.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemVideoHolder.video.startWindowFullscreen(activity, false, true);
                    }
                });
                //防止错位设置
                itemVideoHolder.video.setPlayTag(position+"");
                itemVideoHolder.video.setPlayPosition(position);
                //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
                itemVideoHolder.video.setAutoFullWithSize(true);
                //音频焦点冲突时是否释放
                itemVideoHolder.video.setReleaseWhenLossAudio(false);
                //全屏动画
                itemVideoHolder.video.setShowFullAnimation(true);
                //小屏时不触摸滑动
                itemVideoHolder.video.setIsTouchWiget(false);
            }
        }

        @Override
        public int getItemCount() {
            return 7;
        }
    }

    class ItemVideoHolder extends RecyclerView.ViewHolder{
        private StandardGSYVideoPlayer video;
        ItemVideoHolder(View view){
            super(view);
            video =  (StandardGSYVideoPlayer)findViewById(R.id.detail_player);
        }

    }
}
