package com.example.project1.external;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FitnessAPI {

    @GET("exerciseinfo/?format=json")
    Call<FitnessData<ExerciseDataInfo>> getAllExerciseInfo();

    @GET("workout/?format=json")
    Call<FitnessData<WorkoutDataInfo>> getAllWorkoutInfo();

    @Headers({"Content-Type: application/json", "Authorization: " + ExternalProcess.AUTH_TOKEN})
    @POST("workout/?format=json")
    Call<FitnessData<WorkoutDataInfo>> sendWorkoutInfo(@Body WorkoutDataInfo workoutDataInfo);
}
