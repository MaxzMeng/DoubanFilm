package me.maxandroid.doubanfilm.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class PagingRecyclerFragment<RspModel, Model> extends RecyclerFragment<RspModel, Model> {
    private static final int VISIBLE_THRESHOLD = 1;
    protected int page = 0;
    protected int start = 0;
    protected int count = 20;
    protected int totalCount = 0;

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
                        onLoadMore();
                    }
                }
            }
        });
    }

    public abstract boolean canLoadMore();

    protected void onLoadMore() {
        page++;
        start = page * count;
    }

    protected final void resetData() {
        page = 0;
        start = 0;
        count = 20;
        totalCount = 0;
    }
}
