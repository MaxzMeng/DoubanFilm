package me.maxandroid.doubanfilm.api.celebrity;

import me.maxandroid.doubanfilm.api.common.Avatars;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CelebrityRspModel {

    private String id;
    private String name;
    @SerializedName("name_en")
    private String nameEn;
    private String alt;
    private Avatars avatars;
    private List<String> aka;
    @SerializedName("aka_en")
    private List<String> akaEn;
    private String gender;
    @SerializedName("mobile_url")
    private String mobileUrl;
    private List<Works> works;
    @SerializedName("born_place")
    private String bornPlace;


    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }


    public void setAkaEn(List<String> akaEn) {
        this.akaEn = akaEn;
    }

    public List<String> getAkaEn() {
        return akaEn;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setWorks(List<Works> works) {
        this.works = works;
    }

    public List<Works> getWorks() {
        return works;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }


    public void setAvatars(Avatars avatars) {
        this.avatars = avatars;
    }

    public Avatars getAvatars() {
        return avatars;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public List<String> getAka() {
        return aka;
    }


    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameEn() {
        return nameEn;
    }


    public void setBornPlace(String bornPlace) {
        this.bornPlace = bornPlace;
    }

    public String getBornPlace() {
        return bornPlace;
    }


    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAlt() {
        return alt;
    }

}
