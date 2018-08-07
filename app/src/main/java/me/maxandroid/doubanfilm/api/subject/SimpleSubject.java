package me.maxandroid.doubanfilm.api.subject;

import com.google.gson.annotations.SerializedName;

public class SimpleSubject {
    @SerializedName("doubanId")
    private int id;
    private String img;
    private String name;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
