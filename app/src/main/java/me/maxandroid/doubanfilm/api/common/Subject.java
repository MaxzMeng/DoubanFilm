package me.maxandroid.doubanfilm.api.common;

import me.maxandroid.doubanfilm.api.subject.Directors;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Subject {
    private Rating rating;
    private List<String> genres;
    private String title;
    private List<Cast> casts;
    @SerializedName("collect_count")
    private int collectCount;
    @SerializedName("original_title")
    private String originalTitle;
    private String subtype;
    private List<Directors> directors;
    private String year;
    private Images images;
    private String alt;
    private String id;


    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Rating getRating() {
        return rating;
    }


    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    public List<Cast> getCasts() {
        return casts;
    }


    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getCollectCount() {
        return collectCount;
    }


    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }


    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getSubtype() {
        return subtype;
    }

    public List<Directors> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Directors> directors) {
        this.directors = directors;
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

}
