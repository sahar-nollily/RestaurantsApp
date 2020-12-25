package com.bignerdranch.android.restaurantsapp.network.places

import com.bignerdranch.android.restaurantsapp.network.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceApi{

    @GET("businesses/search")
    suspend fun getPlaces(@Header("Authorization") authorization: String,
                               @Query("term") term: String,
                               @Query("latitude") latitude: String,
                               @Query("longitude") longitude: String): PlaceResponse

    @GET("businesses/{id}")
    suspend fun placeDetails(@Header("Authorization") authorization: String,
                                  @Path("id")placeId: String): PlacesDetail

    @GET("current.json")
    suspend fun getWeather(@Query("key") key: String,
                           @Query("q") latLon: String): WeatherResponse
}