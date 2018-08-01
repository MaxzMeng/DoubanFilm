package me.maxandroid.doubanfilm.common.widget.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import me.maxandroid.doubanfilm.common.R;

public class StrikeHeaderRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout refreshLayout;
    SwipeRefreshLayout.OnRefreshListener listener;
    View headerView;
    public TextView itemHeader;
    RecyclerView mRecycler;

    public StrikeAdapter getAdapter() {
        return mAdapter;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public void setRefreshLayout(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    StrikeAdapter mAdapter;

    public StrikeHeaderRecyclerView(@NonNull Context context) {
        super(context, null);
    }

    public StrikeHeaderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrikeHeaderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public StrikeHeaderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.strike_header_recycler, this);
        headerView = findViewById(R.id.sticky_header);
        itemHeader = findViewById(R.id.header_textview);
//        itemHeader.setText(mAdapter.dataList.get(0).getHeader());
        mRecycler = findViewById(R.id.rv_recycler);
        refreshLayout = findViewById(R.id.srl_refresh);
        refreshLayout.setOnRefreshListener(this);
        mRecycler.addOnScrollListener(new RvScrollListener());
        refreshLayout.setProgressViewOffset(false, 0, 200);
    }

    @Override
    public void onRefresh() {
        if (this.listener != null) {
            listener.onRefresh();
        }
    }


    private class RvScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            View stickyInfoView = recyclerView.getChildAt(0);//获取头部View
            if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                headerView.setVisibility(View.VISIBLE);
                itemHeader.setText(String.valueOf(stickyInfoView.getContentDescription()));
            }
            View transInfoView = recyclerView.findChildViewUnder(headerView.getMeasuredWidth() / 2
                    , headerView.getMeasuredHeight() + 1);//位于headerView下方的itemView（该坐标是否在itemView内）
            if (transInfoView != null && transInfoView.getTag() != null) {
                int tag = (int) transInfoView.getTag();
                int deltaY = transInfoView.getTop() - headerView.getMeasuredHeight();
                if (tag == StrikeAdapter.HAS_STICKY_VIEW)//当Item包含粘性头部一类时
                {
                    if (transInfoView.getTop() > 0)//当Item还未移动出顶部时
                    {
                        headerView.setTranslationY(deltaY);
                    } else//当Item移出顶部，粘性头部复原
                    {
                        headerView.setTranslationY(0);
                    }
                } else//当Item不包含粘性头部时
                {
                    headerView.setTranslationY(0);
                }
            }
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRecycler.setLayoutManager(layout);
    }

    public void setAdapter(StrikeAdapter adapter) {
        mAdapter = adapter;
        mRecycler.setAdapter(adapter);
    }

    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        this.listener = listener;
    }
}
