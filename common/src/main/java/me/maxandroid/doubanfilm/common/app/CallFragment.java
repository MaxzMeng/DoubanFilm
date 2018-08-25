package me.maxandroid.doubanfilm.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//带有一个retrofit请求的fragment，请求的返回类型是泛型中的RspModel
public abstract class CallFragment<RspModel> extends BaseFragment implements Callback<RspModel> {
    protected boolean firstInit = true;//用来判别数据是否为第一次加载，用来达到非第一次加载显示Toast的效果
    protected Call<RspModel> call;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        call = setCall();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (call != null) {
            call.enqueue(this);
        }
    }

    //从子类中获取网络请求，如果不为空就加入请求队列
    protected abstract Call<RspModel> setCall();

    @Override
    public void onResponse(Call<RspModel> call, Response<RspModel> response) {
        if (firstInit == true) {
            firstInit = false;
        }
    }

    @Override
    public void onFailure(Call<RspModel> call, Throwable t) {

    }
}
