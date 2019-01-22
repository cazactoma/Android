package com.example.toma.androidsapt10.vo;

import java.util.List;

public class Page {
    List<TaskVO> tasks;

    public List<TaskVO> getTasks() {
        return tasks;
    }

    public Page(List<TaskVO> tasks)
    {
        this.tasks =tasks;
    }

    public Page()
    {

    }
}
