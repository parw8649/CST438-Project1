package com.example.project1.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project1.model.Exercise;
import com.example.project1.model.FitnessLog;
import com.example.project1.model.User;

import java.util.List;

@Dao
public interface FitnessLogDao {

    @Insert
    void insert(FitnessLog... FitnessLogs);

    @Update
    void update(FitnessLog... FitnessLogs);

    @Delete
    void delete(FitnessLog FitnessLogs);

    @Query("SELECT * FROM " + AppDatabase.FITNESS_LOG_TABLE + " ORDER BY mDate DESC")
    List<FitnessLog> getAllFitnessLogs();

    @Query("SELECT * FROM " + AppDatabase.FITNESS_LOG_TABLE + " WHERE mLogId = :logId")
    List<FitnessLog> getFitnessLogsById(int logId);

    @Query("SELECT * FROM " + AppDatabase.FITNESS_LOG_TABLE + " WHERE mUserId = :mUserId ORDER BY mDate DESC")
    List<FitnessLog> getFitnessLogsByUserId(int mUserId);

    @Query("SELECT * FROM " + AppDatabase.FITNESS_LOG_TABLE + " WHERE mExercise = :exerciseName")
    FitnessLog getFitnessLogByExercise(String exerciseName);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername = :mUsername")
    User getUserByUsername(String mUsername);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserId = :mUserId")
    User getUserByUserId(int mUserId);

    @Insert
    void insert(Exercise... exercises);

    @Update
    void update(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);

    @Query("SELECT * FROM " + AppDatabase.EXERCISE_TABLE + " ORDER BY exerciseId DESC")
    List<Exercise> getAllExerciseList();

    @Query("SELECT * FROM " + AppDatabase.EXERCISE_TABLE + " WHERE exerciseName = :exerciseName")
    Exercise getByExerciseName(String exerciseName);
}
