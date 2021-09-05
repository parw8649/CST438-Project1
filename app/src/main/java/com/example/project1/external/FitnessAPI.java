package com.example.project1.external;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FitnessAPI {

    @GET("exerciseinfo/?format=json")
    Call<ExerciseData> getAllExerciseInfo();
}
