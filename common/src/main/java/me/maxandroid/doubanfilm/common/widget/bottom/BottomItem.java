package me.maxandroid.doubanfilm.common.widget.bottom;

import android.support.annotation.DrawableRes;

public class BottomItem {
    private final int TITLE;
    @DrawableRes
    private final int ICON;

    public BottomItem(int TITLE, int ICON) {
        this.TITLE = TITLE;
        this.ICON = ICON;
    }

    public int getTITLE() {
        return TITLE;
    }

    public int getICON() {
        return ICON;
    }

}
