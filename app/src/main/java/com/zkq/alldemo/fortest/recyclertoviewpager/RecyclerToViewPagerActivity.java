package com.zkq.alldemo.fortest.recyclertoviewpager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zkq.alldemo.R;
import com.zkq.alldemo.fortest.recyclertoviewpager.adapter.MyAdapter;
import com.zkq.alldemo.fortest.recyclertoviewpager.view.DividerItemDecoration;
import com.zkq.alldemo.fortest.recyclertoviewpager.view.HorizontalPageLayoutManager;
import com.zkq.alldemo.fortest.recyclertoviewpager.view.PagingItemDecoration;
import com.zkq.alldemo.fortest.recyclertoviewpager.view.PagingScrollHelper;
import com.zkq.weapon.base.BaseActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zkq
 * create:2019/8/12 5:18 PM
 * email:zkq815@126.com
 * desc: RecyclerView 实现ViewPager
 */
public class RecyclerToViewPagerActivity extends BaseActivity implements PagingScrollHelper.onPageChangeListener, View.OnClickListener {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    TextView tv_title;
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    RadioGroup rg_layout;
    Button btnUpdate;
    TextView tv_page_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_to_view_pager);
        init();
        rg_layout = (RadioGroup) findViewById(R.id.rg_layout);
        rg_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchLayout(checkedId);
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_page_total = (TextView) findViewById(R.id.tv_page_total);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        myAdapter = new MyAdapter();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setAdapter(myAdapter);
        scrollHelper.setUpRecycleView(recyclerView);
        scrollHelper.setOnPageChangeListener(this);
        switchLayout(R.id.rb_horizontal_page);
        recyclerView.setHorizontalScrollBarEnabled(true);
        //获取总页数,采用这种方法才能获得正确的页数。否则会因为RecyclerView.State 缓存问题，页数不正确。
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                tv_page_total.setText("共" + scrollHelper.getPageCount() + "页");
            }
        });

    }

    private RecyclerView.ItemDecoration lastItemDecoration = null;
    private HorizontalPageLayoutManager horizontalPageLayoutManager = null;
    private LinearLayoutManager hLinearLayoutManager = null;
    private LinearLayoutManager vLinearLayoutManager = null;
    private DividerItemDecoration hDividerItemDecoration = null;
    private DividerItemDecoration vDividerItemDecoration = null;
    private PagingItemDecoration pagingItemDecoration = null;

    private void init() {
        hLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        hDividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL);

        vDividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        vLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        horizontalPageLayoutManager = new HorizontalPageLayoutManager(3, 4);
        pagingItemDecoration = new PagingItemDecoration(this, horizontalPageLayoutManager);

    }

    private void switchLayout(int checkedId) {
        RecyclerView.LayoutManager layoutManager = null;
        RecyclerView.ItemDecoration itemDecoration = null;
        switch (checkedId) {
            case R.id.rb_horizontal_page:
                layoutManager = horizontalPageLayoutManager;
                itemDecoration = pagingItemDecoration;
                break;
            case R.id.rb_vertical_page:
                layoutManager = vLinearLayoutManager;
                itemDecoration = vDividerItemDecoration;
                break;
            case R.id.rb_vertical_page2:
                layoutManager = hLinearLayoutManager;
                itemDecoration = hDividerItemDecoration;
                break;
        }
        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.removeItemDecoration(lastItemDecoration);
            recyclerView.addItemDecoration(itemDecoration);
            scrollHelper.updateLayoutManger();
            scrollHelper.scrollToPosition(0);
            lastItemDecoration = itemDecoration;
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPageChange(int index) {

        tv_title.setText("第" + (index + 1) + "页");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                updateData();
                break;
        }
    }

    private void updateData() {
        myAdapter.updateData();
        myAdapter.notifyDataSetChanged();
        //滚动到第一页
        scrollHelper.scrollToPosition(0);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                tv_page_total.setText("共" + scrollHelper.getPageCount() + "页");
            }
        });
    }
}
