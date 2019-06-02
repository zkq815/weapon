package com.zkq.alldemo.fortest.tangram;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.structure.viewcreator.ViewHolderCreator;
import com.tmall.wireless.tangram.support.async.AsyncLoader;
import com.tmall.wireless.tangram.support.async.AsyncPageLoader;
import com.tmall.wireless.tangram.support.async.CardLoadSupport;
import com.tmall.wireless.tangram.util.IInnerImageSetter;
import com.tmall.wireless.vaf.framework.VafContext;
import com.tmall.wireless.vaf.virtualview.Helper.ImageLoader;
import com.tmall.wireless.vaf.virtualview.event.EventData;
import com.tmall.wireless.vaf.virtualview.event.EventManager;
import com.tmall.wireless.vaf.virtualview.event.IEventProcessor;
import com.tmall.wireless.vaf.virtualview.view.image.ImageBase;
import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityTangramTestBinding;
import com.zkq.alldemo.fortest.tangram.view.CustomAnnotationView;
import com.zkq.alldemo.fortest.tangram.view.CustomCell;
import com.zkq.alldemo.fortest.tangram.view.CustomCellView;
import com.zkq.alldemo.fortest.tangram.view.CustomClickSupport;
import com.zkq.alldemo.fortest.tangram.view.CustomExposureSupport;
import com.zkq.alldemo.fortest.tangram.view.CustomHolderCell;
import com.zkq.alldemo.fortest.tangram.view.CustomInterfaceView;
import com.zkq.alldemo.fortest.tangram.view.CustomViewHolder;
import com.zkq.alldemo.fortest.tangram.view.NoBackgroundView;
import com.zkq.alldemo.fortest.tangram.view.TestCell;
import com.zkq.alldemo.fortest.tangram.view.TestCellView;
import com.zkq.alldemo.fortest.tangram.view.TestViewHolder;
import com.zkq.weapon.base.BaseActivity;
import com.zkq.weapon.basehodler.view.SlideShowInsideEdtionView;
import com.zkq.weapon.basehodler.view.TestView;
import com.zkq.weapon.market.tangram.TangramUtils;
import com.zkq.weapon.market.tools.ToolJson;

import org.json.JSONArray;
import org.json.JSONException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zkq
 * create:2019/5/29 3:42 PM
 * email:zkq815@126.com
 * desc:
 */
public class TangramTestActivity extends BaseActivity {
    private ActivityTangramTestBinding mBinding;
    private RecyclerView rvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_tangram_test);
        rvList = mBinding.rvList;
        initTangram();
    }

    private void initTangram(){
        // 初始化 TangramBuilder
        TangramBuilder.InnerBuilder builder = TangramUtils.initTrngram(this);
        // 注册自定义的卡片和组件
        builder.registerCell("TestCell", TestCell.class, TestCellView.class);
        builder.registerCell("InterfaceCell", CustomInterfaceView.class);
        builder.registerCell("AnnotationCell", CustomAnnotationView.class);
        builder.registerCell("CustomCell", CustomCell.class, CustomCellView.class);
        builder.registerCell("HolderCell", CustomHolderCell.class,
                new ViewHolderCreator<>(R.layout.item_holder, CustomViewHolder.class,
                        TextView.class));
        builder.registerCell("NoBackground", NoBackgroundView.class);
        // 注册 VirtualView 版本的 Tangram 组件
//        builder.registerVirtualView("VVTest");
        // 生成TangramEngine实例
        mEngine = builder.build();
        // 加载VirtualView模板数据
//        mEngine.setVirtualViewTemplate(VVTEST.BIN);
//        mEngine.setVirtualViewTemplate(Utils.getAssetsFile(this, "VVTest.out"));
        // 绑定业务 support 类到 engine
        // 处理点击
        mEngine.addSimpleClickSupport(new CustomClickSupport());
        // 处理曝光
        mEngine.addExposureSupport(new CustomExposureSupport());
        // 异步加载数据
        CardLoadSupport cardLoadSupport = new CardLoadSupport(new AsyncLoader() {
            @Override
            public void loadData(Card card, @NonNull LoadedCallback callback) {
//                ZLog.d(TAG, "loadData: cardType=" + card.stringType);
            }
        }, new AsyncPageLoader() {
            @Override
            public void loadData(int page, @NonNull Card card, @NonNull LoadedCallback callback) {
//                Log.d(TAG, "loadData: page=" + page + ", cardType=" + card.stringType);
            }
        });
        CardLoadSupport.setInitialPage(1);
        mEngine.addCardLoadSupport(cardLoadSupport);
        VafContext vafContext = mEngine.getService(VafContext.class);
        vafContext.setImageLoaderAdapter(new ImageLoader.IImageLoaderAdapter() {
            @Override
            public void bindImage(String uri, ImageBase imageBase, int reqWidth, int reqHeight) {
                if (ToolJson.isValidContextForGlide(TangramTestActivity.this)) {
                    RequestBuilder requestBuilder =
                            Glide.with(TangramTestActivity.this).asBitmap().load(uri);
                    if (reqWidth > 0 || reqHeight > 0) {
                        requestBuilder.submit(reqWidth, reqHeight);
                    }
                    ImageTarget imageTarget = new ImageTarget(imageBase);
                    requestBuilder.into(imageTarget);
                }
            }

            @Override
            public void getBitmap(String uri, int reqWidth, int reqHeight,
                                  ImageLoader.Listener lis) {
                if (ToolJson.isValidContextForGlide(TangramTestActivity.this)) {
                    RequestBuilder requestBuilder =
                            Glide.with(TangramTestActivity.this).asBitmap().load(uri);
                    if (reqWidth > 0 || reqHeight > 0) {
                        requestBuilder.submit(reqWidth, reqHeight);
                    }
                    ImageTarget imageTarget = new ImageTarget(lis);
                    requestBuilder.into(imageTarget);
                }
            }
        });
//         注册VirtualView事件处理器
        vafContext.getEventManager().register(EventManager.TYPE_Click, new IEventProcessor() {
            @Override
            public boolean process(EventData data) {
                Toast.makeText(TangramTestActivity.this, data.mVB.getAction(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        vafContext.getEventManager().register(EventManager.TYPE_Exposure, new IEventProcessor() {
            @Override
            public boolean process(EventData data) {
//                Log.d(TAG, "Exposure process: " + data.mVB.getViewCache().getComponentData());
                return true;
            }
        });
        // 绑定 recyclerView
        mEngine.bindView(rvList);
        // 监听 recyclerView 的滚动事件
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 在 scroll 事件中触发 engine 的 onScroll，内部会触发需要异步加载的卡片去提前加载数据
                mEngine.onScrolled();
            }
        });
        // 设置悬浮类型布局的偏移（可选）
        mEngine.getLayoutManager().setFixOffset(0, 40, 0, 0);
        // 设置卡片预加载的偏移量（可选）
        mEngine.setPreLoadNumber(3);
        // 加载数据并传递给 engine
        byte[] bytes = ToolJson.getAssetsFile(this, "data2.json");
        if (bytes != null) {
            String json = new String(bytes);
            try {
                JSONArray data = new JSONArray(json);
                mEngine.setData(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
