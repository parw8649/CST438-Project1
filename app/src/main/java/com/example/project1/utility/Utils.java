package com.example.project1.Utility;

import com.example.project1.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static boolean verifyUsername(String username, String persistedUsername) {

        return username.equals(persistedUsername);
    }

    public static boolean verifyPassword(String password, String persistedPassword) {

        return password.equals(persistedPassword);
    }

    public static List<Exercise> getExerciseDataFromExternal() {

        List<Exercise> statExerciseList = new ArrayList<>();
        statExerciseList.add(new Exercise(101, "biceps curl", "bicep curls 20 sets"));
        statExerciseList.add(new Exercise(102, "triceps curl", "triceps curls 20 sets"));
        statExerciseList.add(new Exercise(103, "chest", "chest 25 sets"));
        statExerciseList.add(new Exercise(104, "bench press", "bench press 30 sets"));
        statExerciseList.add(new Exercise(105, "lateral pull", "lateral pull 35 sets"));

        return statExerciseList;
    }
}
