package me.maxandroid.doubanfilm.common.widget.bottom;

import android.support.annotation.DrawableRes;

//底部导航栏图标的类，有一个图标和一个文字
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
