package me.maxandroid.doubanfilm.api.common;


public class Cast {
    private String alt;
    private Avatars avatars;
    private String name;
    private String id;


    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAlt() {
        return alt;
    }


    public void setAvatars(Avatars avatars) {
        this.avatars = avatars;
    }

    public Avatars getAvatars() {
        return avatars;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
