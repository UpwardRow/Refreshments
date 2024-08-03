package com.adowney.refreshments

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    //Getting input from FiltersActivity and putting into the link String
    @GET("api/recipes/v2/?")
    fun getEdamamData(
        @Query("app_key") appKey : String,
        @Query("app_id") appId : String,
        @Query("type") type : String,
        @Query("q") query : String,
        @Query("excluded") taskIds: List<String>,
    ): Call<Result>

}
