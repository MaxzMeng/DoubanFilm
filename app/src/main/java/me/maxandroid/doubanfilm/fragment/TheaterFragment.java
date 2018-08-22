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

    boolean isOnRefresh = false;

    private static final String PREF_SETTING = "settings";
    private static final String CITY_NAME = "city_name";
    private static final String CITY_ALIAS = "city_alias";
    private static final String CITY_DEFAULT = "北京";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        cityName = SharedPrefsUtil.getValue(getContext(), PREF_SETTING, CITY_NAME, CITY_DEFAULT);
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
        isOnRefresh = true;
        mRefresh.setRefreshing(isOnRefresh);
        setCall().clone().enqueue(this);
    }


    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        setCall().clone().enqueue(this);
    }

    @Override
    public void onResponse(Call<RspModel<List<Subject>>> call, Response<RspModel<List<Subject>>> response) {
        if (start == 0 && !firstInit) {
            Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
        }
        if (isOnRefresh) {
            mAdapter.clear();
            resetData();
            isOnRefresh = false;
        }
        mRefresh.setRefreshing(isOnRefresh);
        totalCount = response.body().getTotal();
        mAdapter.add(response.body().getResult());
        super.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<RspModel<List<Subject>>> call, Throwable t) {
        mRefresh.setRefreshing(false);
        Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R2.id.tv_city)
    public void onCityClick() {
        ((MainFragment) getParentFragment()).start(CityPickFragment.newInstance(this));
    }

    @Override
    public void onCityChanged(City city) {
        SharedPrefsUtil.putValue(getContext(), PREF_SETTING, CITY_NAME, city.getName());
        SharedPrefsUtil.putValue(getContext(), PREF_SETTING, CITY_ALIAS, city.getAlias());
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
