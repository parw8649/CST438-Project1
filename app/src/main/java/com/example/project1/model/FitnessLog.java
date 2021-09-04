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

    private Date mDate;

    private int mUserId;

    public FitnessLog(String mExercise, double weight, int reps, int mUserId) {
        this.mExercise = mExercise;
        this.mWeight = weight;
        this.mReps = reps;
        this.mDate = new Date();
        this.mUserId = mUserId;
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

    @Override
    public String toString() {

        String output = mExercise + " " + mWeight + " : " + mReps;
        output += "\n" + getDate();
        output += "\nUserId =" + mUserId;

        return output;
    }
}
