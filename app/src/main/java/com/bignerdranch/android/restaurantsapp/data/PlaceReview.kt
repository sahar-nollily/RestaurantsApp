package com.bignerdranch.android.restaurantsapp.data

import com.google.gson.annotations.SerializedName

data class PlaceReview(
    val rating: String,
    val user: User,
    val text: String,
    @SerializedName("time_created")val reviewTime: String
)

data class User(
    @SerializedName("image_url") val UserImage: String,
    val name: String
)
