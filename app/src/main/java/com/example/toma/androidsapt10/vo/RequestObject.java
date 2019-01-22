package com.example.toma.androidsapt10.vo;

import com.example.toma.androidsapt10.Entities.Task;

import java.util.List;

public class RequestObject {
    private String requestType;
    private List<Task> tasks;
    private String token;

    public RequestObject(String requestType, List<Task> tasks, String token) {
        this.requestType = requestType;
        this.tasks = tasks;
        this.token = token;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
