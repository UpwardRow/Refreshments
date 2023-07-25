package com.adowney.refreshments

import com.google.gson.annotations.SerializedName

// This class defines what Retrofit looks for when formatting the JSON

data class RecipeData(
    val url: String,
    @SerializedName("label") val recipeTitle: String
)
