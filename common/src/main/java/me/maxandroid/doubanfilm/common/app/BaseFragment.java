package me.maxandroid.doubanfilm.common.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment extends SupportFragment {
    private Unbinder mUnbinder = null;

    @LayoutRes
    public abstract int setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(setLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, v);
        onBindView(savedInstanceState, v);
        return v;
    }
}
