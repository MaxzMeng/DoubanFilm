package me.maxandroid.doubanfilm.fragment;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.R2;
import me.maxandroid.doubanfilm.api.city.City;
import me.maxandroid.doubanfilm.api.city.CityModel;
import me.maxandroid.doubanfilm.api.city.Province;
import me.maxandroid.doubanfilm.common.app.RecyclerFragment;
import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.doubanfilm.common.widget.recycler.StrikeHeaderDecoration;
import me.maxandroid.doubanfilm.net.NetWork;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 城市选择界面
 */
public class CityPickFragment extends RecyclerFragment<List<Province>, CityModel> {
    private int currentLevel;
    private static final int PROVINCE_LEVEL = 1;//当前正在省份选择级别
    private static final int CITY_LEVEL = 2;//当前正在城市选择级别
    private List<Province> provinceList = new ArrayList<>();
    private List<CityModel> cityList = new ArrayList<>();
    private CityChangeListener listener;

    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    public CityPickFragment() {
        // Required empty public constructor
    }

    public static CityPickFragment newInstance(CityChangeListener listener) {

        Bundle args = new Bundle();
        CityPickFragment fragment = new CityPickFragment();
        fragment.setListener(listener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        mRecycler.addItemDecoration(new StrikeHeaderDecoration() {
            @Override
            public String getHeaderName(int pos) {
                return mAdapter.getItems().get(pos).getAlias().substring(0, 1).toUpperCase();
            }
        });
        initToolBar();
    }

    private void initToolBar() {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_back);
        DrawableCompat.setTint(drawable, Color.GREEN);
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }

    @Override
    protected RecyclerAdapter<CityModel> setAdapter() {
        return new RecyclerAdapter<CityModel>() {
            @Override
            protected int getItemViewType(int position, CityModel model) {
                return R.layout.item_city;
            }

            @Override
            protected ViewHolder<CityModel> onCreateViewHolder(View root, int viewType) {
                return new CityHolder(root);
            }
        };
    }

    @Override
    protected int getRecyclerId() {
        return R.id.recycler;
    }

    @Override
    protected Call<List<Province>> setCall() {
        return NetWork.remote().getCities();
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_city_pick;
    }

    @Override
    public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
        super.onResponse(call, response);
        for (Province province : response.body()) {
            mAdapter.add(province);
            provinceList.add(province);
        }
    }


    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, CityModel model) {
        super.onItemClick(holder, model);
        if (model instanceof Province) {
            cityList.clear();
            cityList.addAll(((Province) model).getCities());
            Collections.sort(cityList, new Comparator<CityModel>() {
                @Override
                public int compare(CityModel o1, CityModel o2) {
                    return o1.getAlias().compareTo(o2.getAlias());
                    //把城市按字典序排序
                }
            });
            mAdapter.replace(cityList);
            currentLevel = CITY_LEVEL;
            mRecycler.scrollToPosition(0);
        } else {
            pop();
            if (listener != null) {
                listener.onCityChanged((City) model);
            }
        }
    }

    public CityChangeListener getListener() {
        return listener;
    }

    public void setListener(CityChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onBackPressedSupport() {
        if (currentLevel == CITY_LEVEL) {
            mAdapter.clear();
            for (Province province : provinceList) {
                mAdapter.add(province);
            }
            cityList.clear();
            currentLevel = PROVINCE_LEVEL;
        } else {
            pop();
        }
        return true;
    }

    class CityHolder extends RecyclerAdapter.ViewHolder<CityModel> {
        @BindView(R2.id.tv_city_name)
        TextView mName;

        public CityHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(CityModel model) {
            mName.setText(model.getName());
        }
    }

    /**
     * 当城市发生变化时，主界面应立即刷新数据且更改sp中储存的数据，利用该接口的回调来实现
     */
    public interface CityChangeListener {
        void onCityChanged(City city);
    }
}

