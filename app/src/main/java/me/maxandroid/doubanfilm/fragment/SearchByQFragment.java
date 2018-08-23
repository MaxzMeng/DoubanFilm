package me.maxandroid.doubanfilm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.R2;
import me.maxandroid.doubanfilm.api.RspModel;
import me.maxandroid.doubanfilm.api.common.Subject;
import me.maxandroid.doubanfilm.common.app.RecyclerFragment;
import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.doubanfilm.net.NetWork;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchByQFragment extends RecyclerFragment<RspModel<List<Subject>>, Subject> {

    private String q;
    @BindView(R2.id.et_name)
    EditText mSearchName;

    public SearchByQFragment() {
        // Required empty public constructor
    }


    @Override
    public int setLayout() {
        return R.layout.fragment_search_by_q;
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        mSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                q = s.toString();
                if (call != null && !call.isExecuted()) {
                    call.cancel();
                }
                call = NetWork.remote().searchMovieByQ(q);
                call.enqueue(SearchByQFragment.this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSearchName.requestFocus();
        showSoftInput(mSearchName);
    }


    @OnClick(R2.id.btn_cancel)
    public void onCancelClick() {
        hideSoftInput();
        pop();
    }

    @Override
    public void onResponse(Call<RspModel<List<Subject>>> call, Response<RspModel<List<Subject>>> response) {
        super.onResponse(call, response);
        List<Subject> result = response.body().getResult();
        mAdapter.replace(result);
    }

    @Override
    protected RecyclerAdapter<Subject> setAdapter() {
        return new RecyclerAdapter<Subject>() {
            @Override
            protected int getItemViewType(int position, Subject subject) {
                return R.layout.item_search_q;
            }

            @Override
            protected ViewHolder<Subject> onCreateViewHolder(View root, int viewType) {
                return new SearchHolder(root);
            }
        };
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Subject subject) {
        start(DetailFragment.newInstance(subject.getId()));
        hideSoftInput();
    }

    @Override
    protected int getRecyclerId() {
        return R.id.recycler;
    }

    @Override
    protected Call<RspModel<List<Subject>>> setCall() {
        return null;
    }

    class SearchHolder extends RecyclerAdapter.ViewHolder<Subject> {
        @BindView(R2.id.iv_image)
        ImageView mImage;
        @BindView(R2.id.tv_title)
        TextView mTitle;
        @BindView(R2.id.tv_content)
        TextView mContent;

        public SearchHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Subject subject) {
            Glide.with(getContext()).load(subject.getImages().getSmall()).into(mImage);
            mTitle.setText(subject.getTitle());
            mContent.setText(subject.getRating().getAverage() + "分/" + subject.getYear() + "年");
        }
    }

}
