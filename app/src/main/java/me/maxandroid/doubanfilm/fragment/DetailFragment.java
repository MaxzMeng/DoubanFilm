package me.maxandroid.doubanfilm.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.R2;
import me.maxandroid.doubanfilm.api.common.Avatars;
import me.maxandroid.doubanfilm.api.common.Cast;
import me.maxandroid.doubanfilm.api.subject.Comment;
import me.maxandroid.doubanfilm.api.subject.Directors;
import me.maxandroid.doubanfilm.api.subject.SubjectRspModel;
import me.maxandroid.doubanfilm.common.app.CallFragment;
import me.maxandroid.doubanfilm.common.widget.recycler.RecyclerAdapter;
import me.maxandroid.doubanfilm.net.NetWork;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 电影详情页
 *
 */
public class DetailFragment extends CallFragment<SubjectRspModel> {
    private static final String SUBJECT_ID = "SUBJECT_ID";
    private String ID;
    private SubjectRspModel subject;
    @BindView(R2.id.sv_index)
    NestedScrollView scrollView;
    @BindView(R2.id.ll_image)
    LinearLayout mLinearImage;
    @BindView(R2.id.detail_image)
    ImageView mImage;
    @BindView(R2.id.toolBar)
    Toolbar mToolBar;
    @BindView(R2.id.iv_back)
    ImageView mBack;
    @BindView(R2.id.tv_rate_count)
    TextView mRateCount;
    @BindView(R2.id.tv_rate_detail)
    TextView mRate;
    @BindView(R2.id.tv_description)
    TextView mDescription;
    @BindView(R2.id.tv_movie_title)
    TextView mMovieTitle;
    @BindView(R2.id.tv_time_and_genres)
    TextView mContent;
    @BindView(R2.id.ll_casts)
    LinearLayout mCastLayout;
    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    RecyclerAdapter<Comment> mAdapter;

    int totalHeight = 0;

    public static DetailFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(SUBJECT_ID, id);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ID = getArguments().getString(SUBJECT_ID, "");
        if (ID.isEmpty()) {
            Toast.makeText(getContext(), "获取失败", Toast.LENGTH_SHORT).show();
            pop();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        DrawableCompat.setTint(mBack.getDrawable(), Color.WHITE);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > totalHeight) {
                    scrollY = totalHeight;
                    mToolBar.findViewById(R.id.iv_popcorn).setVisibility(View.GONE);
                    ((TextView) mToolBar.findViewById(R.id.tv_title)).setText(mToolBar.getTitle());
                } else {
                    mToolBar.findViewById(R.id.iv_popcorn).setVisibility(View.VISIBLE);
                    ((TextView) mToolBar.findViewById(R.id.tv_title)).setText("电影");
                }
                float alpha = (float) scrollY / (float) totalHeight;
                mToolBar.setBackgroundColor(Color.argb((int) (alpha * 255), 255, 124, 2));
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<Comment>() {
            @Override
            protected int getItemViewType(int position, Comment comment) {
                if (comment.getId() == 0 && comment.getName().equals("fake")) {
                    return R.layout.item_comment_fake;
                } else {
                    return R.layout.item_comment;
                }
            }

            @Override
            protected ViewHolder onCreateViewHolder(View root, int viewType) {
                if (viewType == R.layout.item_comment_fake) {
                    return new FakeCommentHolder(root);
                } else {
                    return new CommentViewHolder(root);
                }
            }
        });

        setFakeData(2);

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mLinearImage.measure(w, h);
        totalHeight = mLinearImage.getMeasuredHeight();
    }

    private void setFakeData(int num) {
        for (int i = 0; i < num; i++) {
            Comment comment = new Comment();
            comment.setId(0);
            comment.setName("fake");
            mAdapter.add(comment);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Call<List<Comment>> commentCall = NetWork.remote().getComments(ID);
        commentCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                mAdapter.replace(response.body());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

    @Override
    protected Call<SubjectRspModel> setCall() {
        return NetWork.remote().getMovieSubject(ID);
    }


    @Override
    public void onResponse(Call<SubjectRspModel> call, Response<SubjectRspModel> response) {
        super.onResponse(call, response);
        subject = response.body();
        mToolBar.setTitle(subject.getTitle());
        mRateCount.setText(String.format(getResources().getString(R.string.detail_rating_count), subject.getRatingsCount()));
        mRate.setText(String.format("%s", subject.getRating().getAverage()));
        mDescription.setText(subject.getSummary());
        mMovieTitle.setText(subject.getTitle());
        mContent.setText(String.format(getResources().getString(R.string.detail_time_and_genres), subject.getYear(), subject.getGenres().get(0)));
        Glide.with(getContext()).load(subject.getImages().getLarge()).into(mImage);

        List<Directors> directors = subject.getDirectors();
        List<Cast> casts = subject.getCasts();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < directors.size(); i++) {
            View v = inflater.inflate(R.layout.item_cast, mCastLayout, false);
            ImageView castImage = v.findViewById(R.id.iv_cast);
            Avatars avatar = directors.get(i).getAvatars();
            TextView mName = v.findViewById(R.id.tv_cast_name);
            String name = directors.get(i).getName();
            if (avatar == null || name == null) {
                v = null;
                continue;
            }
            Glide.with(getContext()).load(avatar.getSmall()).into(castImage);
            mName.setText(name);
            mCastLayout.addView(v);
        }
        for (int i = 0; i < casts.size(); i++) {
            View v = inflater.inflate(R.layout.item_cast, mCastLayout, false);
            ImageView castImage = v.findViewById(R.id.iv_cast);
            Glide.with(getContext()).load(casts.get(i).getAvatars().getSmall()).into(castImage);
            TextView mName = v.findViewById(R.id.tv_cast_name);
            mName.setText(casts.get(i).getName());
            mCastLayout.addView(v);
        }
    }

    @Override
    public void onFailure(Call<SubjectRspModel> call, Throwable t) {
        super.onFailure(call, t);
        Toast.makeText(getContext(), "获取失败", Toast.LENGTH_SHORT).show();
    }

    class CommentViewHolder extends RecyclerAdapter.ViewHolder<Comment> {
        @BindView(R2.id.iv_avatar)
        ImageView mAvatar;
        @BindView(R2.id.tv_name)
        TextView mName;
        @BindView(R2.id.tv_date)
        TextView mData;
        @BindView(R2.id.tv_rate)
        TextView mRate;
        @BindView(R2.id.tv_vote)
        TextView mVote;
        @BindView(R2.id.tv_comment)
        TextView mComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Comment comment) {
            Glide.with(getContext()).load(comment.getAvatar()).into(mAvatar);
            mName.setText(comment.getName());
            mRate.setText(String.format("%s", comment.getRate()));
            mVote.setText(String.format("%s", comment.getVotes()));
            mComment.setText(comment.getText());
            mData.setText(comment.getTime());

        }
    }

    class FakeCommentHolder extends RecyclerAdapter.ViewHolder<Comment> {

        public FakeCommentHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Comment comment) {

        }
    }

}
