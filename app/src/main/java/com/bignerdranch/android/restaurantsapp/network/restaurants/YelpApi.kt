package com.bignerdranch.android.restaurantsapp.network.restaurants

import com.bignerdranch.android.restaurantsapp.network.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpApi{

    @GET("businesses/search")
    suspend fun getRestaurants(@Header("Authorization") authorization: String,
                               @Query("term") term: String,
                               @Query("latitude") latitude: String,
                               @Query("longitude") longitude: String): YelpResponse

    @GET("businesses/{id}")
    suspend fun restaurantDetails(@Header("Authorization") authorization: String,
                                  @Path("id")restaurantId: String): RestaurantDetail

    @GET("current.json")
    suspend fun getWeather(@Query("key") key: String,
                           @Query("q") latLon: String): WeatherResponse
}