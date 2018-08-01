package me.maxandroid.doubanfilm.util;

import android.content.Context;
import android.widget.TextView;

import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.api.common.Subject;

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
}
