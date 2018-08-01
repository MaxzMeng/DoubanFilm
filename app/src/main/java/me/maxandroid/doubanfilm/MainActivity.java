package me.maxandroid.doubanfilm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import me.maxandroid.doubanfilm.common.app.BaseActivity;
import me.maxandroid.doubanfilm.fragment.MainFragment;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout container = new FrameLayout(this);
        container.setId(R.id.fragment_container);
        setContentView(container);
        loadRootFragment(R.id.fragment_container, new MainFragment());

    }
}
