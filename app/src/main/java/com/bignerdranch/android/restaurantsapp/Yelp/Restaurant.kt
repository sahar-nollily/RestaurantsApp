package com.bignerdranch.android.restaurantsapp.Yelp

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Restaurant(
        @PrimaryKey(autoGenerate = true) val restaurantID : Int = 0,
        val name: String,
        val rating: Double,
        @SerializedName("distance") val distanceInMeters: Double,
        @SerializedName("image_url") val imageUrl: String,
        var categories: List<YelpCategory>
) {
    fun displayDistance(): String {
        val milesPerMeter = 0.000621371
        val distanceInMiles = "%.2f".format(distanceInMeters * milesPerMeter)
        return "$distanceInMiles mi"
    }
}

data class YelpCategory(
        val title: String
)
