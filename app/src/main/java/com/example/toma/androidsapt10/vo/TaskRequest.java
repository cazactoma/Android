package com.example.toma.androidsapt10.vo;

public class TaskRequest
{
    private String description;
    private int id;
    private String token;

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TaskRequest(String description, int id) {
        this.description = description;
        this.id = id;

    }

    public TaskRequest()
    {

    }

}
