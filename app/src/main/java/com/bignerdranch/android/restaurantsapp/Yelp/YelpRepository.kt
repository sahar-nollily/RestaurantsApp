package com.bignerdranch.android.restaurantsapp.Yelp

class YelpRepository (private val yelpApi: YelpApi){

    suspend fun getRestaurants(authorization: String, term:String,latitude: String, longitude: String):List<Restaurant>{
        return yelpApi.getRestaurants(authorization, term, latitude, longitude).restaurants
    }

}