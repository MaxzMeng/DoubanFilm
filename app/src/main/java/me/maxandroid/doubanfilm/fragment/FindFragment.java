package me.maxandroid.doubanfilm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import me.maxandroid.doubanfilm.api.subject.SimpleSubject;
import me.maxandroid.doubanfilm.common.app.BaseFragment;
import me.maxandroid.doubanfilm.common.widget.HorListRecycler;
import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.doubanfilm.net.NetWork;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {
    @BindView(R2.id.hl_recycler)
    HorListRecycler mRecycler;
    RecyclerAdapter<SimpleSubject> mAdapter;

    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public int setLayout() {
        return R.layout.fragment_find;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, final View rootView) {
        mAdapter = new RecyclerAdapter<SimpleSubject>() {
            @Override
            protected int getItemViewType(int position, SimpleSubject simpleSubject) {
                return R.layout.item_hot;
            }

            @Override
            protected ViewHolder<SimpleSubject> onCreateViewHolder(View root, int viewType) {
                return new HotViewHolder(root);
            }
        };
        mRecycler.setAdapter(mAdapter);
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<SimpleSubject>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, SimpleSubject simpleSubject) {
                ((MainFragment) getParentFragment()).start(DetailFragment.newInstance(simpleSubject.getId() + ""));
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Call<List<SimpleSubject>> call = NetWork.remote().getHot();
        call.enqueue(new Callback<List<SimpleSubject>>() {
            @Override
            public void onResponse(Call<List<SimpleSubject>> call, Response<List<SimpleSubject>> response) {
                mAdapter.add(response.body());
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<SimpleSubject>> call, Throwable t) {

            }
        });
    }

    @OnClick(R2.id.ll_search)
    void searchClick() {

    }

    @OnClick(R2.id.ll_us)
    void usClick() {

    }

    @OnClick(R2.id.ll_top)
    void topClick() {

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

