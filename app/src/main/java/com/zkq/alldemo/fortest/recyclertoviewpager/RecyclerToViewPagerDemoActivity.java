package com.zkq.alldemo.fortest.recyclertoviewpager;

import android.os.Bundle;
import android.widget.TextView;

import com.zkq.alldemo.R;
import com.zkq.alldemo.fortest.recyclertoviewpager.adapter.MyAdapter;
import com.zkq.alldemo.fortest.recyclertoviewpager.view.HorizontalPageLayoutManager;
import com.zkq.alldemo.fortest.recyclertoviewpager.view.PagingItemDecoration;
import com.zkq.alldemo.fortest.recyclertoviewpager.view.PagingScrollHelper;
import com.zkq.weapon.base.BaseActivity;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zkq
 * create:2019/8/12 5:18 PM
 * email:zkq815@126.com
 * desc: RecyclerView 实现ViewPager
 */
public class RecyclerToViewPagerDemoActivity extends BaseActivity implements PagingScrollHelper.onPageChangeListener{

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    TextView tv_title;
    TextView tv_page_total;
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    private RecyclerView.ItemDecoration lastItemDecoration = null;
    private HorizontalPageLayoutManager horizontalPageLayoutManager = null;
    private PagingItemDecoration pagingItemDecoration = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_to_view_pager_demo);
        tv_title = findViewById(R.id.tv_title);
        tv_page_total = findViewById(R.id.tv_page_total);
        recyclerView = findViewById(R.id.recyclerview);
        init();
        //获取总页数,采用这种方法才能获得正确的页数。否则会因为RecyclerView.State 缓存问题，页数不正确。
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                tv_page_total.setText("共" + scrollHelper.getPageCount() + "页");
            }
        });
    }

    private void init() {
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        scrollHelper.setUpRecycleView(recyclerView);
        scrollHelper.setOnPageChangeListener(this);
        horizontalPageLayoutManager = new HorizontalPageLayoutManager(1, 4);
        pagingItemDecoration = new PagingItemDecoration(this, horizontalPageLayoutManager);
        RecyclerView.LayoutManager layoutManager = null;
        RecyclerView.ItemDecoration itemDecoration = null;
        layoutManager = horizontalPageLayoutManager;
        itemDecoration = pagingItemDecoration;
        recyclerView.setHorizontalScrollBarEnabled(true);
        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.removeItemDecoration(lastItemDecoration);
            scrollHelper.updateLayoutManger();
            scrollHelper.scrollToPosition(0);
            lastItemDecoration = itemDecoration;
        }
    }

    @Override
    public void onPageChange(int index) {
        tv_title.setText("第" + (index + 1) + "页");
    }

}
