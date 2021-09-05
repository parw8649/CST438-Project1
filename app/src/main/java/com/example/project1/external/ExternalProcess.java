package com.example.project1.external;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExternalProcess {

    public static FitnessAPI getFitnessAPIInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wger.de/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FitnessAPI.class);
    }
}
