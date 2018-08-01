package me.maxandroid.doubanfilm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.R2;
import me.maxandroid.doubanfilm.StrikeAdapterImp;
import me.maxandroid.doubanfilm.api.coming.ComingSubject;
import me.maxandroid.doubanfilm.common.app.BaseFragment;
import me.maxandroid.doubanfilm.common.widget.recycler.Model;
import me.maxandroid.doubanfilm.common.widget.recycler.StrikeAdapter;
import me.maxandroid.doubanfilm.common.widget.recycler.StrikeHeaderRecyclerView;
import me.maxandroid.doubanfilm.net.NetWork;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R2.id.recycler)
    StrikeHeaderRecyclerView mRecycler;
    StrikeAdapter mAdapter;
    List<Model> lists = new ArrayList<>();
    SwipeRefreshLayout mRefresh;

    @Override
    public int setLayout() {
        return R.layout.fragment_comming;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefresh = mRecycler.getRefreshLayout();
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setRefreshListener(this);
        mRecycler.setAdapter(new StrikeAdapterImp(lists, getContext()));
        mAdapter = mRecycler.getAdapter();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(true);
        Call<List<ComingSubject>> call = NetWork.remote().getComing();
        call.enqueue(new Callback<List<ComingSubject>>() {
            @Override
            public void onResponse(Call<List<ComingSubject>> call, Response<List<ComingSubject>> response) {
                lists.clear();
                lists.addAll(response.body());
                mAdapter.notifyDataSetChanged();
                mRefresh.setRefreshing(false);
                mRecycler.itemHeader.setVisibility(View.VISIBLE);
                mRecycler.itemHeader.setText(lists.get(0).getHeader());
                Toast.makeText(getContext(), "成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ComingSubject>> call, Throwable t) {
                Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
