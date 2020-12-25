package com.bignerdranch.android.restaurantsapp.repository

import com.bignerdranch.android.restaurantsapp.database.RestaurantDao
import com.bignerdranch.android.restaurantsapp.yelp.Restaurant
import com.bignerdranch.android.restaurantsapp.yelp.YelpApi

class YelpRepository (private val yelpApi: YelpApi, private val restaurantDao: RestaurantDao){

    suspend fun getRestaurant() : List<Restaurant> =restaurantDao.getRestaurant()

    suspend fun getRestaurants(authorization: String, term:String,latitude: String, longitude: String):List<Restaurant>{
        val restaurant = yelpApi.getRestaurants(authorization, term, latitude, longitude).restaurants
        restaurantDao.addRestaurant(*restaurant.map {
            Restaurant(
                    0,
                    it.name,
                    it.rating,
                    it.distanceInMeters,
                    it.imageUrl,
                    it.categories
            )
        }.toTypedArray()
        )
        return restaurant
    }

     suspend fun deleteRestaurants()=restaurantDao.deleteRestaurants()

}