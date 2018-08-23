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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.R2;
import me.maxandroid.doubanfilm.api.RspModel;
import me.maxandroid.doubanfilm.api.common.Subject;
import me.maxandroid.doubanfilm.common.app.PagingRecyclerFragment;
import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.doubanfilm.net.NetWork;
import me.maxandroid.doubanfilm.util.TextContentUtil;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchByTagFragment extends PagingRecyclerFragment<RspModel<List<Subject>>, Subject> {

    private static final String[] tagName = new String[]{
            "剧情", "喜剧", "动作", "爱情", "科幻", "悬疑", "惊悚", "恐怖", "犯罪", "同性", "音乐", "歌舞", "传记", "历史", "战争", "西部", "奇幻", "冒险", "灾难", "武侠", "情色"
    };
    private String tag = tagName[0];

    @BindView(R2.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R2.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    public SearchByTagFragment() {
        // Required empty public constructor
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        initToolBar();
        for (int i = 0; i < tagName.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tagName[i]));
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!call.isExecuted()) {
                    call.cancel();
                }
                resetData();
                mAdapter.clear();
                tag = tagName[tab.getPosition()];
                call = setCall().clone();
                call.enqueue(SearchByTagFragment.this);
                progressBar.setVisibility(View.VISIBLE);
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
    public int setLayout() {
        return R.layout.fragment_search_by_tag;
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
                return new SearchByTagHolder(root);
            }
        };
    }

    @Override
    public void onResponse(Call<RspModel<List<Subject>>> call, Response<RspModel<List<Subject>>> response) {
        totalCount = response.body().getTotal();
        if (call.request().url().queryParameter("tag").equals(tag)) {
            mAdapter.add(response.body().getResult());
            progressBar.setVisibility(View.GONE);
        }
        super.onResponse(call, response);
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
    protected Call<RspModel<List<Subject>>> setCall() {
        return NetWork.remote().searchMovieByTag(tag, start, count);
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

    class SearchByTagHolder extends RecyclerAdapter.ViewHolder<Subject> {
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

        public SearchByTagHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Subject subject) {
            Glide.with(SearchByTagFragment.this).load(subject.getImages().getSmall()).into(mImage);
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
