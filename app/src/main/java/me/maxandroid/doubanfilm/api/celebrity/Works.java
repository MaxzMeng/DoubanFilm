package me.maxandroid.doubanfilm.api.celebrity;


import java.util.List;

import me.maxandroid.doubanfilm.api.common.Subject;

public class Works {

    private List<String> roles;
    private Subject subject;


    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }


    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

}