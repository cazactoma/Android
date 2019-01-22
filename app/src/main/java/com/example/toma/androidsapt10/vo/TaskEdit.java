package com.example.toma.androidsapt10.vo;

import java.io.Serializable;

public class TaskEdit implements Serializable
{
    private String description;
    private int id;

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public TaskEdit(String description, int id) {
        this.description = description;
        this.id = id;
    }
}
