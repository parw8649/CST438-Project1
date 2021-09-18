package com.example.project1.activity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MainData.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //database instance
    private static RoomDB database;
    MainAdapter adapter;

    //database name
    private static String DATABASE_NAME = "database";

    public synchronized  static RoomDB getInstance(Context context){
        //check if statement
        if (database == null){
            //if database empty
            //initialize database
            database = Room.databaseBuilder(context.getApplicationContext()
                    ,RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    //create Dao
    public abstract MainDao mainDao();
}
