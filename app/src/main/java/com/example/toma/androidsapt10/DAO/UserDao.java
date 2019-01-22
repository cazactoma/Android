package com.example.toma.androidsapt10.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.toma.androidsapt10.Entities.User;

@Dao
public interface UserDao
{
    @Insert
    void insert(User user);

    @Query("SELECT * FROM Users WHERE Users.username LIKE :username")
    LiveData<User> getUser(String username);

    @Query("SELECT * FROM Users LIMIT 1")
    LiveData<User> getCurrentUser();

    @Query("DELETE FROM Users")
    void deleteAll();
}
