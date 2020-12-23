package com.bignerdranch.android.restaurantsapp.network.restaurants

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class RestaurantDetail(
    @PrimaryKey
    @SerializedName("id") var favID: String,
    var planID: Int= 0,
    val name : String= "",
    val is_closed: Boolean= false,
    val location: RestaurantLocation,
    val phone: String= "",
    val url: String= "",
    val coordinates: PlacesLocation,
    val categories: List<YelpCategory>,
    var note: String= "",
    @SerializedName("image_url") val imageUrl: String= "",
    val photos: List<String>){
    fun isClosed() :String{
        return if(is_closed){
            "Closed"
        }else{
            "Open"
        }
    }
}

data class RestaurantLocation(
    val city: String = "",
    val address1: String = ""
)