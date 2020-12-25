package com.bignerdranch.android.restaurantsapp.network.places

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Places(
        @PrimaryKey
        @SerializedName("id")val placeID : String,
        val name: String,
        val rating: Double,
        @SerializedName("distance") val distanceInMeters: Double,
        @SerializedName("image_url") val imageUrl: String,
        val categories: List<Category>,
        val coordinates: PlacesLocation
) {
    fun displayDistance(): String {
        val milesPerMeter = 0.000621371
        val distanceInMiles = "%.2f".format(distanceInMeters * milesPerMeter)
        return "$distanceInMiles mi"
    }
}

data class Category(
        val title: String)

data class PlacesLocation(
        val latitude: Double,
        val longitude: Double)