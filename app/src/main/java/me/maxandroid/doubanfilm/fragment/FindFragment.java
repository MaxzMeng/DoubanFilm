package me.maxandroid.doubanfilm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import me.maxandroid.doubanfilm.R;
import me.maxandroid.doubanfilm.common.app.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {


    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public int setLayout() {
        return R.layout.fragment_find;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

}
