package me.maxandroid.doubanfilm.util;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.api.common.Subject;
import me.maxandroid.doubanfilm.api.subject.Directors;

public class TextContentUtil {
    public static void setCastName(Context context, TextView textView, Subject subject, int num) {
        switch (num) {
            case 0:
                textView.setText(context.getResources().getString(R.string.detail_casts_none));
                break;
            case 1:
                textView.setText(String.format(context.getResources().getString(R.string.detail_casts_one), subject.getCasts().get(0).getName()));
                break;
            case 2:
                textView.setText(String.format(context.getResources().getString(R.string.detail_casts_two), subject.getCasts().get(0).getName(), subject.getCasts().get(1).getName()));
                break;
            default:
                textView.setText(String.format(context.getResources().getString(R.string.detail_casts_three), subject.getCasts().get(0).getName(), subject.getCasts().get(1).getName(), subject.getCasts().get(2).getName()));
        }
    }


    public static void setSeenCount(Context context, TextView textView, int num) {
        if (num > 10000) {
            float realNum;
            realNum = (float) num / 10000;
            java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.0");
            String str = myformat.format(realNum);
            textView.setText(String.format(context.getString(R.string.have_seen_ten_thousand), str));
        } else {
            textView.setText(String.format(context.getString(R.string.have_seen), num));
        }
    }

    public static void setWishCount(Context context, TextView textView, int num) {
        if (num > 10000) {
            float realNum;
            realNum = (float) num / 10000;
            java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.0");
            String str = myformat.format(realNum);
            textView.setText(String.format(context.getString(R.string.wish_to_see_thousand), str));
        } else {
            textView.setText(String.format(context.getString(R.string.wish_to_see), num));
        }
    }

    public static void setLabel(Context context, TextView textView, Subject subject) {
        switch (subject.getGenres().size()) {
            case 0:
                textView.setText(context.getResources().getString(R.string.detail_label_none));
                break;
            case 1:
                textView.setText(String.format(context.getResources().getString(R.string.detail_label_one), subject.getGenres().get(0)));
                break;
            case 2:
                textView.setText(String.format(context.getResources().getString(R.string.detail_label_two), subject.getGenres().get(0), subject.getGenres().get(1)));
                break;
            default:
                textView.setText(String.format(context.getResources().getString(R.string.detail_label_three), subject.getGenres().get(0), subject.getGenres().get(1), subject.getGenres().get(2)));
        }
    }

    public static void setTopColor(TextView textView, int topNum) {
        Context context = textView.getContext();
        int color;
        switch (topNum) {
            case 1:
                color = context.getResources().getColor(R.color.top_1);
                break;
            case 2:
                color = context.getResources().getColor(R.color.top_2);
                break;
            case 3:
                color = context.getResources().getColor(R.color.top_3);
                break;
            default:
                color = context.getResources().getColor(R.color.top_default);
                break;
        }
        textView.setTextColor(color);
    }

    public static void setDirectorName(Context context, TextView mDirector, List<Directors> directors) {
        int size = directors.size();
        if (size == 0) {
            mDirector.setText(context.getResources().getString(R.string.detail_no_director));
        } else {
            mDirector.setText(String.format(context.getResources().getString(R.string.detail_director), directors.get(0).getName()));
        }
    }
}
