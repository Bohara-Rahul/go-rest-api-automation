package com.gorest.models;

public class Post {
    private int id;
    private int user_id;
    private String title;
    private String body;

    public Post(int id) {
        setId(id);
    }

    public Post(int id, int user_id, String title, String body) {
        setId(id);
        setUser_id(user_id);
        setTitle(title);
        setBody(body);
    }

    public Post(int user_id, String title, String body) {
        setUser_id(user_id);
        setTitle(title);
        setBody(body);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
