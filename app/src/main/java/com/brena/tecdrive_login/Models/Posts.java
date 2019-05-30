package com.brena.tecdrive_login.Models;

import java.util.Date;

public class Posts {

    private int id;
    private String title;
    private String description;
    private Date createdAt;
    private Date updateAt;
    private User user;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }
    public User getUser(){
        return user;
    }

}
