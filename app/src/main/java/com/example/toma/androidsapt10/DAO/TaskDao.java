package com.example.toma.androidsapt10.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.toma.androidsapt10.Entities.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Tasks")
    LiveData<List<Task>> getTasks();

    @Insert
    void insert(Task task);

    @Query("DELETE FROM Tasks")
    void deleteAll();

    @Update
    void update(Task task);
}
