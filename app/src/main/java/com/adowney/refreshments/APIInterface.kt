package com.adowney.refreshments

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// This is where the API key is inserted into the url, which is hidden from potential threats
const val API_KEY = BuildConfig.SPOONACULAR_API_KEY
interface APIInterface {

    //Getting input from FiltersActivity and putting into the link String
    @GET("recipes/complexSearch?apiKey=")
    fun getSpoonacularData(
        @Query(API_KEY) apiKey : String,
        @Query("excludeIngredients") ingredients : List<String>,
        @Query("query") searchBarValue : String
    ): Call<SpoonacularData>

}
