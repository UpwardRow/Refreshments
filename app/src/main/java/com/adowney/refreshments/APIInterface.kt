package com.adowney.refreshments

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface APIInterface {

    //Getting input from FiltersActivity and putting into the link String
    @GET("api/recipes/v2/?")
    fun getEdamamDataWithAllFilterTypes(
        @Query("app_key") appKey : String,
        @Query("app_id") appId : String,
        @Query("type") type : String,
        @Query("health") health : List<String>,
        @Query("q") query : String,
        @Query("excluded") excluded: List<String>,
    ): Call<Result>

    @GET("api/recipes/v2/?")
    fun getEdamamDataWithUserFilters(
        @Query("app_key") appKey : String,
        @Query("app_id") appId : String,
        @Query("type") type : String,
        @Query("q") query : String,
        @Query("excluded") excluded: List<String>,
    ): Call<Result>

    @GET("api/recipes/v2/?")
    fun getEdamamDataWithQuickFilters(
        @Query("app_key") appKey : String,
        @Query("app_id") appId : String,
        @Query("type") type : String,
        @Query("health") health : List<String>,
        @Query("q") query : String,
    ): Call<Result>
}
