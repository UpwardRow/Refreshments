package com.adowney.refreshments

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    val ingredients : String
    val searchBarValue : String

    //Getting input from FiltersActivity and putting into the link String
    @GET("recipes/complexSearch?apiKey=")
    fun getSpoonacularData(
        @Query("excludeIngredients") ingredients : List<String>,
        @Query("query") searchBarValue : String
    ): Call<SpoonacularData>

}
