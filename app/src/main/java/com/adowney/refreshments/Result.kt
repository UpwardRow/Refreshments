package com.adowney.refreshments

import com.google.gson.annotations.SerializedName

//This class defines what Retrofit looks for when formatting the JSON
data class Result(
    val id: Int,
    val image: String,
    val imageType: String,
    @SerializedName("title") val recipeTitle: String
)