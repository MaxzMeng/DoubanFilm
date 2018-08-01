package me.maxandroid.doubanfilm.net;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWork {
    private static final String API_URL = "http://192.168.31.228:3000/movie/";
    private static NetWork instance;
    private Retrofit retrofit;

    static {
        instance = new NetWork();
    }

    private NetWork() {

    }

    public static Retrofit getRetrofit() {
        if (instance.retrofit != null) return instance.retrofit;
        Retrofit.Builder builder = new Retrofit.Builder();
        instance.retrofit = builder.baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return instance.retrofit;
    }

    public static FilmService remote() {
        return NetWork.getRetrofit().create(FilmService.class);
    }
}
