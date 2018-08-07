package me.maxandroid.doubanfilm;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private int targetHeight = 0;
    private int mDistanceY = 0;
    private static final int SHOW_SPEED = 3;
    private final RgbValue RGB_VALUE = new RgbValue(255, 124, 2);

    public TranslucentBehavior() {
    }

    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.sv_index;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {

        return true;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        mDistanceY += dyConsumed;
        View v = target.findViewById(R.id.ll_image);
        if (v != null) {
            targetHeight = v.getBottom();
        } else {
            targetHeight = 0;
        }


        if (mDistanceY > 0 && mDistanceY <= targetHeight) {
            child.findViewById(R.id.iv_popcorn).setVisibility(View.VISIBLE);
            final float scale = (float) mDistanceY / targetHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int) alpha, RGB_VALUE.getRed(), RGB_VALUE.getGreen(), RGB_VALUE.getBlue()));
            ((TextView) child.findViewById(R.id.tv_title)).setText("电影");
        } else if (mDistanceY > targetHeight) {
            child.setBackgroundColor(Color.rgb(RGB_VALUE.getRed(), RGB_VALUE.getGreen(), RGB_VALUE.getBlue()));
            child.findViewById(R.id.iv_popcorn).setVisibility(View.GONE);

            ((TextView) child.findViewById(R.id.tv_title)).setText(child.getTitle());


        }
    }

}

