package me.maxandroid.doubanfilm.common.widget.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.maxandroid.doubanfilm.common.R;
import me.maxandroid.doubanfilm.common.R2;
import me.maxandroid.doubanfilm.common.app.BaseFragment;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BottomBarFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R2.id.bottom_bar)
    LinearLayout mBottomBar;
    private final List<BottomItem> items = new ArrayList<>();
    private final List<SupportFragment> fragments = new ArrayList<>();

    private int mCurrentFragment = 0;
    private int mIndexFragment = 0;
    private int mClickedColor = Color.BLACK;//当图标被点击时的颜色,默认为黑

    public void add(BottomItem item, SupportFragment fragment) {
        items.add(item);
        fragments.add(fragment);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_main;
    }

    protected abstract int setIndexFragment();//设置app打开时显示的是list中第几个fragment

    protected abstract int setClickColor();

    protected abstract void addItems();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addItems();
        mIndexFragment = setIndexFragment();
        mCurrentFragment = setIndexFragment();
        if (setClickColor() != 0) {
            mClickedColor = setClickColor();
        }


    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        int size = items.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item, mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            item.setTag(i);
            item.setOnClickListener(this);
            final ImageView itemImage = (ImageView) item.getChildAt(0);
            final TextView itemTitle = (TextView) item.getChildAt(1);
            final BottomItem bean = items.get(i);
            itemImage.setImageResource(bean.getICON());
            itemTitle.setText(bean.getTITLE());
            if (i == mCurrentFragment) {
                DrawableCompat.setTint(itemImage.getDrawable(), mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }
        final SupportFragment[] fragmentArray = fragments.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexFragment, fragmentArray);

    }

    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final ImageView itemImage = (ImageView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            DrawableCompat.setTint(itemImage.getDrawable(), Color.GRAY);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        final int tag = (int) v.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;
        final ImageView itemImage = (ImageView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        DrawableCompat.setTint(itemImage.getDrawable(), mClickedColor);
        itemTitle.setTextColor(mClickedColor);
        showHideFragment(fragments.get(tag), fragments.get(mCurrentFragment));
        mCurrentFragment = tag;
    }
}
