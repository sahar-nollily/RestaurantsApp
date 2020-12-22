package com.bignerdranch.android.restaurantsapp.network.restaurants

import com.google.gson.annotations.SerializedName

class YelpResponse{
    @SerializedName("businesses")
    lateinit var restaurants: List<Restaurant>
}