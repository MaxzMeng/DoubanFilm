package me.maxandroid.doubanfilm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.R2;
import me.maxandroid.doubanfilm.api.subject.SimpleSubject;
import me.maxandroid.doubanfilm.common.app.RecyclerFragment;
import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.doubanfilm.net.NetWork;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends RecyclerFragment<List<SimpleSubject>, SimpleSubject> {

    @BindView(R2.id.progress_bar)
    ProgressBar progressBar;

    public FindFragment() {

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLayoutManager = manager;
        super.onBindView(savedInstanceState, rootView);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected RecyclerAdapter<SimpleSubject> setAdapter() {
        return new RecyclerAdapter<SimpleSubject>() {
            @Override
            protected int getItemViewType(int position, SimpleSubject simpleSubject) {
                return R.layout.item_hot;
            }

            @Override
            protected ViewHolder<SimpleSubject> onCreateViewHolder(View root, int viewType) {
                return new HotViewHolder(root);
            }
        };
    }

    @Override
    protected int getRecyclerId() {
        return R.id.recycler;
    }


    @Override
    protected Call<List<SimpleSubject>> setCall() {
        return NetWork.remote().getHot();
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, SimpleSubject simpleSubject) {
        ((MainFragment) getParentFragment()).start(DetailFragment.newInstance(simpleSubject.getId() + ""));
    }

    @Override
    public void onResponse(Call<List<SimpleSubject>> call, Response<List<SimpleSubject>> response) {
        progressBar.setVisibility(View.GONE);
        mAdapter.replace(response.body());
    }

    @Override
    public void onFailure(Call<List<SimpleSubject>> call, Throwable t) {

    }

    @OnClick(R2.id.tv_search)
    void onSearchClick() {
        ((MainFragment) getParentFragment()).start(new SearchByQFragment());
    }

    @OnClick(R2.id.ll_search)
    void searchClick() {
        start(new SearchByTagFragment());
    }

    @OnClick(R2.id.ll_us)
    void usClick() {
        ((MainFragment) getParentFragment()).start(new USBoxFragment());
    }

    @OnClick(R2.id.ll_top)
    void topClick() {
        ((MainFragment) getParentFragment()).start(new TopFragment());
    }

    class HotViewHolder extends RecyclerAdapter.ViewHolder<SimpleSubject> {
        @BindView(R2.id.iv_image)
        ImageView mImage;
        @BindView(R2.id.tv_name)
        TextView mName;

        public HotViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(SimpleSubject subject) {
            Glide.with(getContext()).load(subject.getImg()).into(mImage);
            mName.setText(subject.getName());
        }
    }
}

