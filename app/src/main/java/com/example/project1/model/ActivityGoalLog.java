package com.example.project1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project1.db.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.ACTIVITY_GOAL_LOG_TABLE)
public class ActivityGoalLog {

    @PrimaryKey(autoGenerate = true)
    private int agGoalId;

    private String goal;

    private Date date = new Date();

    private int userId;

    public ActivityGoalLog(String goal, int userId) {
        this.goal = goal;
        this.userId = userId;
    }

    public int getAgGoalId() {
        return agGoalId;
    }

    public void setAgGoalId(int agGoalId) {
        this.agGoalId = agGoalId;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Goal: " + goal + "\n" +
                "Date=" + date;
    }
}
