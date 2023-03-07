package com.adowney.refreshments

/*
List of JSON items. This is for correct formatting of JSON file, as there is an object instead of
an array at the start of the JSON file
 */

data class SpoonacularData(
    val results: List<Result>
)