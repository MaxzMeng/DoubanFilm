package me.maxandroid.doubanfilm.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.maxandroid.doubanfilm.common.R;
import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;

public class HorListRecycler extends LinearLayout {
    private int row = 1;
    private RecyclerView mRecycler;
    private TextView mTitle;
    private TextView mMore;
    private RecyclerAdapter mAdapter;
    private GridLayoutManager manager;

    public HorListRecycler(Context context) {
        this(context, null);
    }

    public HorListRecycler(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorListRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }


    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_hor_recycler, this);
        mRecycler = findViewById(R.id.recycler);
        mTitle = findViewById(R.id.tv_title);
        mMore = findViewById(R.id.tv_more);
        manager = new GridLayoutManager(context, row);
        manager.setOrientation(GridLayoutManager.HORIZONTAL);
        setRow(2);
        mRecycler.setLayoutManager(manager);
//        mMore.setOnClickListener();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
        manager.setSpanCount(row);
    }

    public RecyclerAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(RecyclerAdapter mAdapter) {
        this.mAdapter = mAdapter;
        mRecycler.setAdapter(mAdapter);
    }

    public TextView getTitle() {
        return mTitle;
    }

    public void setTitle(TextView mTitle) {
        this.mTitle = mTitle;
    }

}
