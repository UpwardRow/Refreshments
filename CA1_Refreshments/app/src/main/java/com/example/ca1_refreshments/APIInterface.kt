package com.example.ca1_refreshments

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val str = "cheese"
const val burger = "burger"

interface APIInterface {

    val ingredients : String
    val searchBarValue : String

    //Getting input from FiltersAcitivty and putting into the link String
    @GET("recipes/complexSearch?apiKey=***REMOVED***")
    fun getSpoonacularData(
        @Query("excludeIngredients") ingredients : String,
        @Query("query") searchBarValue : String
    ): Call<SpoonacularData>

}
