package me.maxandroid.doubanfilm.api.subject;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("doubanId")
    private int id;
    private String name;
    private String avatar;
    private int votes;
    private String text;
    private String time;
    private int rate;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }


    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getVotes() {
        return votes;
    }


    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }


    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
