package com.bignerdranch.android.restaurantsapp.network.places

import com.google.gson.annotations.SerializedName

class PlaceResponse{
    @SerializedName("businesses")
    lateinit var places: List<Places>
}