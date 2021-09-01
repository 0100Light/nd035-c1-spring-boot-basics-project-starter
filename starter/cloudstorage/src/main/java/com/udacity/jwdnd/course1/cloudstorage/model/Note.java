package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    // title, desc, userid
    private String title;
    private String description;
    private int userId;

    public Note(String title, String description, int userId) {
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
