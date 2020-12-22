package com.bignerdranch.android.restaurantsapp.network.weather

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi{

    @GET("current.json")
       suspend fun getWeather(@Query("key") key: String,
                              @Query("q") latLon: String): WeatherResponse

    @GET("forecast.json")
    suspend fun getForecast(@Query("key") key: String,
                            @Query("q") latLon: String,
                            @Query("days") days: String): WeatherResponse
}