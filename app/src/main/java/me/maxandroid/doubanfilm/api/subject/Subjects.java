package me.maxandroid.doubanfilm.api.subject;

import me.maxandroid.doubanfilm.api.common.Subject;

public class Subjects {
    private int box;
    private boolean isNew;
    private int rank;
    private Subject subject;


    public void setBox(int box) {
        this.box = box;
    }

    public int getBox() {
        return box;
    }


    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean getNew() {
        return isNew;
    }


    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }


    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

}
