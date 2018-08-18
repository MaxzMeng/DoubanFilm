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
import butterknife.OnClick;
import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.R2;
import me.maxandroid.doubanfilm.api.RspModel;
import me.maxandroid.doubanfilm.api.city.City;
import me.maxandroid.doubanfilm.api.common.Subject;
import me.maxandroid.doubanfilm.common.app.PagingRecyclerFragment;
import me.maxandroid.doubanfilm.common.tools.SharedPrefsUtil;
import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.doubanfilm.net.NetWork;
import me.maxandroid.doubanfilm.util.TextContentUtil;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TheaterFragment extends PagingRecyclerFragment<RspModel<List<Subject>>, Subject> implements SwipeRefreshLayout.OnRefreshListener, CityPickFragment.CityChangeListener {
    @BindView(R2.id.srl_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R2.id.tv_city)
    TextView mCity;
    String cityName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        cityName = SharedPrefsUtil.getValue(getContext(), "settings", "city_name", "北京");
        super.onCreate(savedInstanceState);
    }

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
        mCity.setText(cityName);
    }

    @Override
    public boolean canLoadMore() {
        return totalCount > start;
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
        return NetWork.remote().getInTheaters(start, count, cityName);
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
        mAdapter.clear();
        resetData();
        setCall().clone().enqueue(this);
    }


    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        setCall().clone().enqueue(this);
    }

    @Override
    public void onResponse(Call<RspModel<List<Subject>>> call, Response<RspModel<List<Subject>>> response) {
        totalCount = response.body().getTotal();
        mAdapter.add(response.body().getResult());
        mRefresh.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<RspModel<List<Subject>>> call, Throwable t) {
        Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R2.id.tv_city)
    public void onCityClick() {
        ((MainFragment) getParentFragment()).start(CityPickFragment.newInstance(this));
    }

    @Override
    public void onCityChanged(City city) {
        SharedPrefsUtil.putValue(getContext(), "settings", "city_name", city.getName());
        SharedPrefsUtil.putValue(getContext(), "settings", "city_alias", city.getAlias());
        cityName = city.getName();
        mCity.setText(cityName);
        onRefresh();
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

            TextContentUtil.setDirectorName(getContext(), mDirector, subject.getDirectors());
            TextContentUtil.setCastName(getContext(), mCasts, subject, subject.getCasts().size());
            TextContentUtil.setSeenCount(getContext(), mSeen, subject.getCollectCount());
        }
    }
}
