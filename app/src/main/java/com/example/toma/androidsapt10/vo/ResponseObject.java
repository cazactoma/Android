package com.example.toma.androidsapt10.vo;

import com.example.toma.androidsapt10.Entities.Task;

import java.util.List;

public class ResponseObject {
    private String responseType;
    private List<Task> tasks;

    public ResponseObject(String responseType, List<Task> tasks) {
        this.responseType = responseType;
        this.tasks = tasks;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
