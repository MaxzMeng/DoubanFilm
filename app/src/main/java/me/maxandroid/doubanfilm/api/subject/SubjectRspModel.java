package me.maxandroid.doubanfilm.api.subject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.maxandroid.doubanfilm.api.common.Cast;
import me.maxandroid.doubanfilm.api.common.Images;
import me.maxandroid.doubanfilm.api.common.Rating;

public class SubjectRspModel {
    private Rating rating;
    @SerializedName("reviews_count")
    private int reviewsCount;
    @SerializedName("wish_count")
    private int wishCount;
    @SerializedName("douban_site")
    private String doubanSite;
    private String year;
    private Images images;
    private String alt;
    private String id;
    @SerializedName("mobile_url")
    private String mobileUrl;
    private String title;
    @SerializedName("do_count")
    private String doCount;
    @SerializedName("share_url")
    private String shareUrl;
    @SerializedName("seasons_count")
    private String seasonsCount;
    @SerializedName("schedule_url")
    private String scheduleUrl;
    @SerializedName("episodes_count")
    private String episodesCount;
    private List<String> countries;
    private List<String> genres;
    @SerializedName("collect_count")
    private int collectCount;
    private List<Cast> casts;
    @SerializedName("current_season")
    private String currentSeason;
    @SerializedName("original_title")
    private String originalTitle;
    private String summary;
    private String subtype;
    private List<Directors> directors;
    @SerializedName("comments_count")
    private int commentsCount;
    @SerializedName("ratings_count")
    private int ratingsCount;
    private List<String> aka;


    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Rating getRating() {
        return rating;
    }


    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }


    public void setWishCount(int wishCount) {
        this.wishCount = wishCount;
    }

    public int getWishCount() {
        return wishCount;
    }


    public void setDoubanSite(String doubanSite) {
        this.doubanSite = doubanSite;
    }

    public String getDoubanSite() {
        return doubanSite;
    }


    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }


    public void setImages(Images images) {
        this.images = images;
    }

    public Images getImages() {
        return images;
    }


    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAlt() {
        return alt;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setDoCount(String doCount) {
        this.doCount = doCount;
    }

    public String getDoCount() {
        return doCount;
    }


    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }


    public void setSeasonsCount(String seasonsCount) {
        this.seasonsCount = seasonsCount;
    }

    public String getSeasonsCount() {
        return seasonsCount;
    }


    public void setScheduleUrl(String scheduleUrl) {
        this.scheduleUrl = scheduleUrl;
    }

    public String getScheduleUrl() {
        return scheduleUrl;
    }


    public void setEpisodesCount(String episodesCount) {
        this.episodesCount = episodesCount;
    }

    public String getEpisodesCount() {
        return episodesCount;
    }


    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getCountries() {
        return countries;
    }


    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }


    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getCollectCount() {
        return collectCount;
    }


    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    public List<Cast> getCasts() {
        return casts;
    }


    public void setCurrentSeason(String currentSeason) {
        this.currentSeason = currentSeason;
    }

    public String getCurrentSeason() {
        return currentSeason;
    }


    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }


    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }


    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getSubtype() {
        return subtype;
    }


    public void setDirectors(List<Directors> directors) {
        this.directors = directors;
    }

    public List<Directors> getDirectors() {
        return directors;
    }


    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }


    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }


    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public List<String> getAka() {
        return aka;
    }
}
