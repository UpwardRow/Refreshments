package com.adowney.refreshments

//This class defines what Retrofit looks for when formatting the JSON
data class Result(
    val id: Int,
    val image: String,
    val imageType: String,
    val title: String
)