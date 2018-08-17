package me.maxandroid.doubanfilm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.R2;
import me.maxandroid.doubanfilm.api.RspModel;
import me.maxandroid.doubanfilm.api.common.Subject;
import me.maxandroid.doubanfilm.common.app.RecyclerFragment;
import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.doubanfilm.net.NetWork;
import me.maxandroid.doubanfilm.util.TextContentUtil;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TheaterFragment extends RecyclerFragment<RspModel<List<Subject>>, Subject> implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R2.id.srl_refresh)
    SwipeRefreshLayout mRefresh;



    @Override
    public int setLayout() {
        return R.layout.fragment_theater;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefresh.setRefreshing(true);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(R.color.blue,
                R.color.green,
                R.color.orange);
    }

    @Override
    protected RecyclerAdapter<Subject> setAdapter() {
        return new RecyclerAdapter<Subject>() {
            @Override
            protected int getItemViewType(int position, Subject subject) {
                return R.layout.theater_item;
            }

            @Override
            protected ViewHolder<Subject> onCreateViewHolder(View root, int viewType) {
                return new TheaterHolder(root);
            }
        };
    }

    @Override
    protected Call<RspModel<List<Subject>>> setCall() {
        return NetWork.remote().getInTheaters();
    }

    @Override
    protected int getRecyclerId() {
        return R.id.recycler;
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Subject subject) {
        ((MainFragment) getParentFragment()).start(DetailFragment.newInstance(subject.getId()));
    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(true);
        call.clone().enqueue(this);
    }

    @Override
    public void onResponse(Call<RspModel<List<Subject>>> call, Response<RspModel<List<Subject>>> response) {
        mAdapter.clear();
        mAdapter.add(response.body().getResult());
        mAdapter.notifyDataSetChanged();
        mRefresh.setRefreshing(false);
        if (firstInit) {
            firstInit = false;
        } else {
            Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<RspModel<List<Subject>>> call, Throwable t) {
        Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
    }

    class TheaterHolder extends RecyclerAdapter.ViewHolder<Subject> {
        @BindView(R2.id.iv_image)
        ImageView mImage;
        @BindView(R2.id.tv_title)
        TextView mTitle;
        @BindView(R2.id.tv_casts)
        TextView mCasts;
        @BindView(R2.id.tv_rate)
        TextView mRate;
        @BindView(R2.id.tv_director)
        TextView mDirector;
        @BindView(R2.id.tv_seen)
        TextView mSeen;

        public TheaterHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Subject subject) {
            Glide.with(TheaterFragment.this).load(subject.getImages().getSmall()).into(mImage);
            mTitle.setText(subject.getTitle());
            double rate = subject.getRating().getAverage();
            if (rate == 0) {
                mRate.setText(getResources().getString(R.string.detail_no_rate));
            } else {
                java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.0");
                String str = myformat.format(rate);
                mRate.setText(String.format(getResources().getString(R.string.detail_rate), str));
            }
            mDirector.setText(String.format(getResources().getString(R.string.detail_director), subject.getDirectors().get(0).getName()));
            TextContentUtil.setCastName(getContext(), mCasts, subject, subject.getCasts().size());
            TextContentUtil.setSeenCount(getContext(), mSeen, subject.getCollectCount());
        }
    }
}
