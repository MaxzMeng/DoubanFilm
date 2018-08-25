package me.maxandroid.doubanfilm.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

//可以分页的recyclerfragment，在这里没有使用比较复杂的实现方式和第三方库
public abstract class PagingRecyclerFragment<RspModel, Model> extends RecyclerFragment<RspModel, Model> {
    private static final int VISIBLE_THRESHOLD = 1;
    protected int page = 0;//第几页
    protected int start = 0;//目前已经加载了多少条数据
    protected int count = 20;//每一页加载多少条数据
    protected int totalCount = 0;//目前已经加载了多少条数据

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (canLoadMore() && dy > 0) {
                    int totalItemCount = mAdapter.getItemCount();
                    int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                    if (lastVisibleItem + VISIBLE_THRESHOLD >= totalItemCount) {
                        onLoadMore();//当加载到倒数第 VISIBLE_THRESHOLD 条数据且可以加载更多时调用加载更多
                    }
                }
            }
        });
    }

    /**
     * 是否可以加载更多数据，由子类来判断
     */
    public abstract boolean canLoadMore();

    /**
     * 当加载更多时需要执行的方法，需要子类重写该方法且调用super
     */
    protected void onLoadMore() {
        page++;
        start = page * count;
        call = setCall().clone();
        if (Integer.valueOf(call.request().url().queryParameter("start")) < totalCount) {
            call.enqueue(this);
        }
    }

    /**
     * 当刷新数据时需要执行的操作，将页数归零
     */
    protected final void resetData() {
        page = 0;
        start = 0;
        totalCount = 0;
    }
}
