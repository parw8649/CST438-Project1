package com.example.project1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project1.db.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.HEART_RATE_LOG_TABLE)
public class HeartRateLog {

    @PrimaryKey(autoGenerate = true)
    private int hrLogId;

    private int wristMonitor;
    private int chestStrapMonitor;

    private Date date = new Date();

    private int userId;

    public HeartRateLog(int wristMonitor, int chestStrapMonitor, int userId) {
        this.wristMonitor = wristMonitor;
        this.chestStrapMonitor = chestStrapMonitor;
        this.userId = userId;
    }

    public int getHrLogId() {
        return hrLogId;
    }

    public void setHrLogId(int hrLogId) {
        this.hrLogId = hrLogId;
    }

    public int getWristMonitor() {
        return wristMonitor;
    }

    public void setWristMonitor(int wristMonitor) {
        this.wristMonitor = wristMonitor;
    }

    public int getChestStrapMonitor() {
        return chestStrapMonitor;
    }

    public void setChestStrapMonitor(int chestStrapMonitor) {
        this.chestStrapMonitor = chestStrapMonitor;
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
        return "WristMonitor=" + wristMonitor + " bpm\n" +
                "ChestStrapMonitor=" + chestStrapMonitor + " bpm\n" +
                "Date=" + date;
    }
}
