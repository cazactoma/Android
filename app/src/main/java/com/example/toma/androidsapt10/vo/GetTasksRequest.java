package com.example.toma.androidsapt10.vo;

public class GetTasksRequest {
    private String token;

    public GetTasksRequest(String token) {
        this.token = token;
    }

    public GetTasksRequest(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
