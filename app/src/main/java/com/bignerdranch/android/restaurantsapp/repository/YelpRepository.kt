package com.bignerdranch.android.restaurantsapp.repository

import com.bignerdranch.android.restaurantsapp.database.restaurant.RestaurantDao
import com.bignerdranch.android.restaurantsapp.network.restaurants.Restaurant
import com.bignerdranch.android.restaurantsapp.network.restaurants.RestaurantDetail
import com.bignerdranch.android.restaurantsapp.network.restaurants.YelpApi
import com.bignerdranch.android.restaurantsapp.network.weather.Weather
import com.bignerdranch.android.restaurantsapp.network.weather.WeatherApi

class YelpRepository(
    private val yelpApi: YelpApi,
    private val restaurantDao: RestaurantDao,
    private val weatherApi: WeatherApi
){

    suspend fun getRestaurant() : List<Restaurant> =restaurantDao.getRestaurant()

    suspend fun getRestaurants(authorization: String, term:String,latitude: String, longitude: String):List<Restaurant>{
        deleteWeather()
        deleteRestaurants()
        val restaurant = yelpApi.getRestaurants(authorization, term, latitude, longitude).restaurants
        restaurantDao.addRestaurant(*restaurant.map {
            Restaurant(
                    it.restaurantID,
                    it.name,
                    it.rating,
                    it.distanceInMeters,
                    it.imageUrl,
                    it.categories,
                    it.coordinates
            )
        }.toTypedArray()
        )
        restaurant.map {
        val weather = weatherApi.getWeather("803bb8a53fdd48baaa0113628201712","${it.coordinates.latitude}, ${it.coordinates.longitude}").current
        restaurantDao.addWeather(
            Weather(it.restaurantID,
            weather.temp_c,
            weather.temp_f,
            weather.condition))
        }
        return restaurant
    }

    suspend fun restaurantDetails(authorization: String, restaurantId: String): RestaurantDetail {
        return yelpApi.restaurantDetails(authorization,restaurantId)
    }

    private suspend fun deleteRestaurants()=restaurantDao.deleteRestaurants()

    private suspend fun deleteWeather()=restaurantDao.deleteWeather()


}