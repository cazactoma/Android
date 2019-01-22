package com.example.toma.androidsapt10.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.toma.androidsapt10.DAO.TaskDao;
import com.example.toma.androidsapt10.DAO.UserDao;
import com.example.toma.androidsapt10.Entities.Task;
import com.example.toma.androidsapt10.Entities.User;

@Database(entities = {User.class, Task.class}, version = 2)
@TypeConverters({DateConverter.class})
public abstract class UserDatabase extends RoomDatabase
{
    public static UserDatabase databaseInstance;

    public abstract UserDao getUserDao();

    public abstract TaskDao getTaskDao();

    public static UserDatabase getDatabase(Context context)
    {
        if(databaseInstance==null)
        {
            databaseInstance=Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class, "user-database").allowMainThreadQueries().build();
        }
        return databaseInstance;
    }

    public static void destroyInstance()
    {
        databaseInstance = null;
    }
}
