package me.maxandroid.doubanfilm.fragment;

import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.common.widget.bottom.BottomBarFragment;
import me.maxandroid.doubanfilm.common.widget.bottom.BottomItem;

public class MainFragment extends BottomBarFragment {

    @Override
    protected int setIndexFragment() {
        return 1;
    }

    @Override
    protected int setClickColor() {
        return 0;
    }


    @Override
    protected void addItems() {
        add(new BottomItem(R.string.item_comming, R.drawable.ic_coming_soon), new CommingFragment());
        add(new BottomItem(R.string.item_hot, R.drawable.ic_theater), new TheaterFragment());
        add(new BottomItem(R.string.item_find, R.drawable.ic_eye), new FindFragment());
    }
}
