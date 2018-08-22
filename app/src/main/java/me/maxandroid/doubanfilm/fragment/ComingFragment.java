package me.maxandroid.doubanfilm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.R2;
import me.maxandroid.doubanfilm.api.coming.ComingSubject;
import me.maxandroid.doubanfilm.common.app.RecyclerFragment;
import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.doubanfilm.common.widget.recycler.StrikeHeaderDecoration;
import me.maxandroid.doubanfilm.net.NetWork;
import me.maxandroid.doubanfilm.util.TextContentUtil;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComingFragment extends RecyclerFragment<List<ComingSubject>, ComingSubject> implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R2.id.srl_refresh)
    SwipeRefreshLayout mRefresh;

    @Override
    public int setLayout() {
        return R.layout.fragment_comming;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(R.color.blue,
                R.color.green,
                R.color.orange);
        mRecycler.addItemDecoration(new StrikeHeaderDecoration() {
            @Override
            public String getHeaderName(int pos) {
                return mAdapter.getItems().get(pos).getDate();
            }
        });
    }

    @Override
    protected RecyclerAdapter<ComingSubject> setAdapter() {
        return new RecyclerAdapter<ComingSubject>() {
            @Override
            protected int getItemViewType(int position, ComingSubject comingSubject) {
                return R.layout.coming_item;
            }

            @Override
            protected ViewHolder<ComingSubject> onCreateViewHolder(View root, int viewType) {
                return new ComingViewHolder(root);
            }
        };
    }

    @Override
    protected int getRecyclerId() {
        return R.id.recycler;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefresh.setRefreshing(true);
    }

    @Override
    protected Call<List<ComingSubject>> setCall() {
        return NetWork.remote().getComing();
    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(true);
        call.clone().enqueue(this);
    }

    @Override
    public void onResponse(Call<List<ComingSubject>> call, Response<List<ComingSubject>> response) {
        List<ComingSubject> result = response.body();
        mAdapter.replace(result);
        mRefresh.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<List<ComingSubject>> call, Throwable t) {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, ComingSubject comingSubject) {
        ((MainFragment) getParentFragment()).start(DetailFragment.newInstance(comingSubject.getId() + ""));
    }

    class ComingViewHolder extends RecyclerAdapter.ViewHolder<ComingSubject> {
        @BindView(R2.id.iv_image)
        ImageView mImage;
        @BindView(R2.id.tv_title)
        TextView mTitle;
        @BindView(R2.id.tv_label)
        TextView mLabel;
        @BindView(R2.id.tv_wish)
        TextView mWish;

        @BindView(R2.id.tv_area)
        TextView mArea;

        public ComingViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(ComingSubject subject) {
            Glide.with(getContext()).load(subject.getAvatar()).into(mImage);
            mTitle.setText(subject.getName());
            mLabel.setText(subject.getLabel());
            mArea.setText(subject.getArea());
            mArea.setText(subject.getArea());
            TextContentUtil.setWishCount(getContext(), mWish, subject.getLike());
        }
    }
}
