package com.bignerdranch.android.restaurantsapp.network.places

import com.bignerdranch.android.restaurantsapp.data.Places
import com.google.gson.annotations.SerializedName

class PlaceResponse{
    @SerializedName("businesses")
    lateinit var places: List<Places>
}