package me.maxandroid.doubanfilm.fragment;


import android.support.v4.app.Fragment;
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
import me.maxandroid.doubanfilm.api.subject.Subjects;
import me.maxandroid.doubanfilm.common.app.RecyclerFragment;
import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.doubanfilm.net.NetWork;
import me.maxandroid.doubanfilm.util.TextContentUtil;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends RecyclerFragment<RspModel<List<Subjects>>, Subjects> {
    @BindView(R2.id.progress_bar)
    ProgressBar mProgress;

    @Override
    public int setLayout() {
        return R.layout.fragment_top;
    }

    @Override
    protected RecyclerAdapter<Subjects> setAdapter() {
        return new RecyclerAdapter<Subjects>() {
            @Override
            protected int getItemViewType(int position, Subjects subjects) {
                return R.layout.item_top;
            }

            @Override
            protected ViewHolder<Subjects> onCreateViewHolder(View root, int viewType) {
                return new HotViewHolder(root);
            }
        };
    }

    @Override
    protected Call<RspModel<List<Subjects>>> setCall() {
        return NetWork.remote().getUSBox();
    }

    @Override
    protected int getRecyclerId() {
        return R.id.recycler;
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Subjects subjects) {
        ((MainFragment) getParentFragment()).start(DetailFragment.newInstance(subjects.getSubject().getId()));
    }

    @Override
    public void onResponse(Call<RspModel<List<Subjects>>> call, Response<RspModel<List<Subjects>>> response) {
        mAdapter.add(response.body().getResult());
        mRecycler.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(Call<RspModel<List<Subjects>>> call, Throwable t) {
        super.onFailure(call, t);
    }


    class HotViewHolder extends RecyclerAdapter.ViewHolder<Subjects> {
        @BindView(R2.id.tv_rank)
        TextView mRank;
        @BindView(R2.id.iv_image)
        ImageView mImage;
        @BindView(R2.id.tv_name)
        TextView mName;
        @BindView(R2.id.tv_rate)
        TextView mRate;
        @BindView(R2.id.tv_label)
        TextView mLabel;
        @BindView(R2.id.tv_casts)
        TextView mCasts;
        @BindView(R2.id.tv_money)
        TextView mMoney;

        public HotViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Subjects subjects) {
            Subject subject = subjects.getSubject();
            mRank.setText(subjects.getRank() + "");
            Glide.with(getContext()).load(subject.getImages().getSmall()).into(mImage);
            mName.setText(subject.getTitle());
            double rate = subject.getRating().getAverage();
            if (rate == 0) {
                mRate.setText(getResources().getString(R.string.detail_no_rate));
            } else {
                java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.0");
                String str = myformat.format(rate);
                mRate.setText(String.format(getResources().getString(R.string.detail_rate), str));
            }
            TextContentUtil.setCastName(getContext(), mCasts, subject, subject.getCasts().size());
            TextContentUtil.setLabel(getContext(), mLabel, subject);
            TextContentUtil.setTopColor(mRank, subjects.getRank());
            mMoney.setText(String.format(getResources().getString(R.string.detail_us_money), subjects.getBox() / 10000));
        }
    }
}
