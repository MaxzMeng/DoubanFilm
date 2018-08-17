package me.maxandroid.doubanfilm.net;

import java.util.List;

import me.maxandroid.doubanfilm.api.RspModel;
import me.maxandroid.doubanfilm.api.celebrity.CelebrityRspModel;
import me.maxandroid.doubanfilm.api.coming.ComingSubject;
import me.maxandroid.doubanfilm.api.common.Subject;
import me.maxandroid.doubanfilm.api.subject.Comment;
import me.maxandroid.doubanfilm.api.subject.SimpleSubject;
import me.maxandroid.doubanfilm.api.subject.SubjectRspModel;
import me.maxandroid.doubanfilm.api.subject.Subjects;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmService {
    @GET("subjects/{id}")
    Call<SubjectRspModel> getMovieSubject(@Path("id") String id);

    @GET("celebrity/{id}")
    Call<CelebrityRspModel> getCelebrityDetail(@Path("id") String id);


    @GET("search")
    Call<RspModel<List<Subject>>> searchMovieByTag(@Query("tag") String tag);

    @GET("search")
    Call<RspModel<List<Subject>>> searchMovieByQ(@Query("q") String Q);

    @GET("top250")
    Call<RspModel<List<Subject>>> getTop250(@Query("start") int start, @Query("count") int count);

    @GET("us_box")
    Call<RspModel<List<Subjects>>> getUSBox();

    @GET("in_theaters")
    Call<RspModel<List<Subject>>> getInTheaters();

    @GET("coming_soon")
    Call<List<ComingSubject>> getComing();

    @GET("comment/{id}")
    Call<List<Comment>> getComments(@Path("id") String id);

    @GET("movie_hot")
    Call<List<SimpleSubject>> getHot();
}
