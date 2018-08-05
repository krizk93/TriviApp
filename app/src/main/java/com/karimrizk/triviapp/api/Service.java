package com.karimrizk.triviapp.api;

import com.karimrizk.triviapp.model.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {


    @GET("/api.php")
    Call<Results> getResults(
            @Query("amount") int numberOfQuestions,
            @Query("category") int categoryID,
            @Query("difficulty") String difficulty,
            @Query("type") String typeOfQuestions
    );

}
