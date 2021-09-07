package com.example.project1.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.project1.model.Exercise;
import com.example.project1.model.FitnessLog;
import com.example.project1.model.User;
import com.example.project1.db.typeConverters.DateTypeConverter;

@Database(entities = {FitnessLog.class, User.class, Exercise.class}, version = 4)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "FITNESS_LOG_DATABASE";
    public static final String FITNESS_LOG_TABLE = "FITNESS_LOG_TABLE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String EXERCISE_TABLE = "EXERCISE_TABLE";

    private static AppDatabase fitnessDatabase;

    public static AppDatabase getDatabaseInstance(Context context){

        if (fitnessDatabase == null) {
            fitnessDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return fitnessDatabase;
    }

    public abstract FitnessLogDao getFitnessLogDao();
}
