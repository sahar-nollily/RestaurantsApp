package com.bignerdranch.android.restaurantsapp.network.restaurants

data class RestaurantDetail(
    val name : String,
    val is_closed: Boolean,
    val location: RestaurantLocation,
    val phone: String,
    val url: String,
    val coordinates: PlacesLocation,
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
    val city: String,
    val address1: String
)