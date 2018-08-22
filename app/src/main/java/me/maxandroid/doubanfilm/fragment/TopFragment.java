package me.maxandroid.doubanfilm.fragment;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
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
public class TopFragment extends RecyclerFragment<RspModel<List<Subject>>, Subject> {

    int start = 0;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.tablayout)
    TabLayout mTabLayout;

    List<Subject> allSubjects = new ArrayList<>();

    public TopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 250; i++) {
            allSubjects.add(null);
        }
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_top;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        initToolBar();
        for (int i = 0; i < 250; i += 25) {
            mTabLayout.addTab(mTabLayout.newTab().setText((i + 1) + "-" + (i + 25)));
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mAdapter.clear();
                start = tab.getPosition() * 25;
                if (!call.isExecuted()) {
                    call.cancel();
                }
                if (allSubjects.get(start) == null) {
                    call = setCall().clone();
                    call.enqueue(TopFragment.this);
                } else {
                    mAdapter.replace(allSubjects.subList(start, start + 24));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected RecyclerAdapter<Subject> setAdapter() {
        return new RecyclerAdapter<Subject>() {
            @Override
            protected int getItemViewType(int position, Subject subject) {
                return R.layout.item_top;
            }

            @Override
            protected ViewHolder<Subject> onCreateViewHolder(View root, int viewType) {
                return new TopHolder(root);
            }
        };
    }

    @Override
    protected int getRecyclerId() {
        return R.id.recycler;
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Subject subject) {
        start(DetailFragment.newInstance(subject.getId()));
    }

    @Override
    public void onResponse(Call<RspModel<List<Subject>>> call, Response<RspModel<List<Subject>>> response) {
        super.onResponse(call, response);
        int callStart = Integer.valueOf(call.request().url().queryParameter("start"));
        for (int i = 0; i < response.body().getResult().size(); i++) {
            allSubjects.remove(callStart + i);
            allSubjects.add(callStart + i, response.body().getResult().get(i));
        }
        if (start == callStart) {
            mAdapter.replace(allSubjects.subList(callStart, callStart + 24));
        }

    }

    private void initToolBar() {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_back);
        DrawableCompat.setTint(drawable, Color.GREEN);
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }


    @Override
    protected Call<RspModel<List<Subject>>> setCall() {
        return NetWork.remote().getTop250(start, 25);
    }

    class TopHolder extends RecyclerAdapter.ViewHolder<Subject> {
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
        @BindView(R2.id.tv_year)
        TextView mYear;

        public TopHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Subject subject) {
            Glide.with(getContext()).load(subject.getImages().getSmall()).into(mImage);
            mTitle.setText(subject.getTitle());
            double rate = subject.getRating().getAverage();
            if (rate == 0) {
                mRate.setText(getResources().getString(R.string.detail_no_rate));
            } else {
                java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.0");
                String str = myformat.format(rate);
                mRate.setText(String.format(getResources().getString(R.string.detail_rate), str));
            }
            mYear.setText(subject.getYear() + "年");
            TextContentUtil.setDirectorName(getContext(), mDirector, subject.getDirectors());
            TextContentUtil.setCastName(getContext(), mCasts, subject, subject.getCasts().size());
            TextContentUtil.setSeenCount(getContext(), mSeen, subject.getCollectCount());
        }
    }
}
