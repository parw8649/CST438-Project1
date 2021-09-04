package com.example.project1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project1.db.AppDatabase;

@Entity(tableName = AppDatabase.EXERCISE_TABLE)
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int exerciseId;

    private String exerciseName;

    public Exercise(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    @Override
    public String toString() {
        return exerciseName;
    }
}
