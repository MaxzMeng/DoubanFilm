package me.maxandroid.doubanfilm.common.widget.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public abstract class StrikeAdapter extends RecyclerView.Adapter<StrikeAdapter.StrikeViewHolder> {
    protected List<Model> dataList;
    protected Context mContext;

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    public StrikeAdapter(List<Model> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
    }


    @Override
    public void onBindViewHolder(@NonNull StrikeViewHolder holder, int position) {

        Model model = dataList.get(position);
        holder.headerTv.setText(model.getHeader());
        holder.itemView.setContentDescription(model.getHeader());
        if (position == 0) {
            holder.headerLayout.setVisibility(View.VISIBLE);
            holder.itemView.setTag(FIRST_STICKY_VIEW);
        } else {
            if (model.getHeader().equals(dataList.get(position - 1).getHeader())) //当前Item头部与上一个Item头部相同，则隐藏头部
            {
                holder.headerLayout.setVisibility(View.GONE);
                holder.itemView.setTag(NONE_STICKY_VIEW);
            } else {
                holder.headerLayout.setVisibility(View.VISIBLE);
                holder.itemView.setTag(HAS_STICKY_VIEW);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public abstract class StrikeViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout headerLayout;

        public TextView headerTv;

//        public View contentView;

        public StrikeViewHolder(View itemView) {
            super(itemView);
        }
    }
}
