package com.example.project1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project1.db.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.FITNESS_LOG_TABLE)
public class FitnessLog {

    @PrimaryKey(autoGenerate = true)
    private int mLogId;

    private String mExercise;
    private double mWeight;
    private int mReps;
    private Long workoutId;
    private String workoutName;
    private String workoutDescription;
    private Date mDate = new Date();

    private int mUserId;

    public FitnessLog(String mExercise, double weight, int reps, int mUserId, Long workoutId, String workoutName, String workoutDescription) {
        this.mExercise = mExercise;
        this.mWeight = weight;
        this.mReps = reps;
        this.mUserId = mUserId;
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.workoutDescription = workoutDescription;
    }

    public int getLogId() {
        return mLogId;
    }

    public void setLogId(int mLogId) {
        this.mLogId = mLogId;
    }

    public String getExercise() {
        return mExercise;
    }

    public void setExercise(String mExercise) {
        this.mExercise = mExercise;
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double mWeight) {
        this.mWeight = mWeight;
    }

    public int getReps() {
        return mReps;
    }

    public void setReps(int mReps) {
        this.mReps = mReps;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutDescription() {
        return workoutDescription;
    }

    public void setWorkoutDescription(String workoutDescription) {
        this.workoutDescription = workoutDescription;
    }

    @Override
    public String toString() {

        String output = mExercise + " " + mWeight + " : " + mReps;
        output += "\n" + getDate();
        output += "\nUserId =" + mUserId;
        output += "\nWorkout name: " + workoutName;
        output += "\nWorkout description: " + workoutDescription;

        return output;
    }
}
