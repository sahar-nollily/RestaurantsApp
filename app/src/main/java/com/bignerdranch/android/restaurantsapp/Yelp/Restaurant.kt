package com.bignerdranch.android.restaurantsapp.Yelp

import com.google.gson.annotations.SerializedName

data class Restaurant(
    val name: String,
    val rating: Double,
    @SerializedName("distance") val distanceInMeters: Double,
    @SerializedName("image_url") val imageUrl: String,
    val categories: List<YelpCategory>) {
    fun displayDistance(): String {
        val milesPerMeter = 0.000621371
        val distanceInMiles = "%.2f".format(distanceInMeters * milesPerMeter)
        return "$distanceInMiles mi"
    }
}

data class YelpCategory(
    val title: String
)
