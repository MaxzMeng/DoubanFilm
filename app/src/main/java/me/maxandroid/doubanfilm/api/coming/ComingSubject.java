package me.maxandroid.doubanfilm.api.coming;

import com.google.gson.annotations.SerializedName;

public class ComingSubject {
    @SerializedName("doubanId")
    private int id;
    private String avatar;
    private String name;
    private String date;
    private String label;
    private String area;
    private int like;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }


    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }


    public void setLike(int like) {
        this.like = like;
    }

    public int getLike() {
        return like;
    }

}
