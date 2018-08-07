package me.maxandroid.doubanfilm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maxandroid.doubanfilm.api.coming.ComingSubject;
import me.maxandroid.doubanfilm.common.widget.recycler.Model;
import me.maxandroid.doubanfilm.common.widget.recycler.StrikeAdapter;
import me.maxandroid.doubanfilm.util.TextContentUtil;

public class StrikeAdapterImp extends StrikeAdapter {
    OnItemClickListener listener;

    public StrikeAdapterImp(List<Model> dataList, Context mContext) {
        super(dataList, mContext);
    }

    @NonNull
    @Override
    public StrikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coming_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        ButterKnife.bind(holder, v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StrikeViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
        ComingSubject subject = (ComingSubject) dataList.get(position);
//        ComingSubject subject = dataList.get(position);
//        holder.contentView.findViewById(R.id.iv_image);
        Glide.with(mContext).load(subject.getAvatar()).into(((ViewHolder) holder).mImage);
        ((ViewHolder) holder).mTitle.setText(subject.getName());
        ((ViewHolder) holder).mLabel.setText(subject.getLabel());
        ((ViewHolder) holder).mArea.setText(subject.getArea());
        ((ViewHolder) holder).mArea.setText(subject.getArea());
        TextContentUtil.setWishCount(mContext, ((ViewHolder) holder).mWish, subject.getLike());
    }

    class ViewHolder extends StrikeAdapter.StrikeViewHolder {
        @BindView(R2.id.iv_image)
        ImageView mImage;
        @BindView(R2.id.tv_title)
        TextView mTitle;
        @BindView(R2.id.tv_label)
        TextView mLabel;
        @BindView(R2.id.tv_wish)
        TextView mWish;

        @BindView(R2.id.tv_area)
        TextView mArea;

        public ViewHolder(View itemView) {
            super(itemView);
            headerLayout = itemView.findViewById(R.id.sticky_header);
            headerTv = itemView.findViewById(R.id.header_textview);
//            contentView = itemView.findViewById(R.id.item_content_view);
//            mImage = itemView.findViewById(R.id.iv_image);
        }
    }

    public interface OnItemClickListener {
        void onClick(int postion);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
