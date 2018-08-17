package me.maxandroid.doubanfilm.common.app;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RecyclerFragment<RspModel, Model> extends CallFragment<RspModel> implements RecyclerAdapter.AdapterListener<Model>, Callback<RspModel> {
    protected RecyclerView mRecycler;
    protected RecyclerAdapter<Model> mAdapter;


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecycler = rootView.findViewById(getRecyclerId());
        mRecycler.setLayoutManager(getLayoutManager());
        mRecycler.setAdapter(mAdapter = setAdapter());
        mAdapter.setListener(this);
    }

    protected abstract RecyclerAdapter<Model> setAdapter();


    @IdRes
    protected abstract int getRecyclerId();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Model model) {

    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Model model) {

    }

    @Override
    public void onResponse(Call<RspModel> call, Response<RspModel> response) {

    }

    @Override
    public void onFailure(Call<RspModel> call, Throwable t) {

    }
}