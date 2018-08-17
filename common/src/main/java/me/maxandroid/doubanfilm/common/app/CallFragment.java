package me.maxandroid.doubanfilm.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallFragment<RspModel> extends BaseFragment implements Callback<RspModel> {
    protected boolean firstInit = true;
    protected Call<RspModel> call;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        call = setCall();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        call.enqueue(this);
        firstInit = false;
    }


    protected abstract Call<RspModel> setCall();

    @Override
    public void onResponse(Call<RspModel> call, Response<RspModel> response) {

    }

    @Override
    public void onFailure(Call<RspModel> call, Throwable t) {

    }
}
