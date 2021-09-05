package com.example.project1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project1.db.AppDatabase;

@Entity(tableName = AppDatabase.EXERCISE_TABLE)
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int exerciseId;
    private String exerciseName;
    private String exerciseDescription;

    public Exercise(int exerciseId, String exerciseName, String exerciseDescription) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.exerciseDescription = exerciseDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    @Override
    public String toString() {
        return exerciseName;
    }

    public String getExerciseInfo() {

        return "Exercise Details\nName: " + exerciseName + "\n" + "Description: " + exerciseDescription;
    }
}
