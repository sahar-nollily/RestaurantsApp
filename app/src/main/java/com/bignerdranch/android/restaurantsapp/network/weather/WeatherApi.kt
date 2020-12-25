package com.bignerdranch.android.restaurantsapp.network.weather

import com.bignerdranch.android.restaurantsapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi{

    @GET("current.json")
       suspend fun getWeather(@Query("q") latLon: String,
                              @Query("key") key: String= BuildConfig.WEATHER_API_KEY): WeatherResponse

    @GET("forecast.json")
        suspend fun getForecast(@Query("q") latLon: String,
                                @Query("days") days: String,
                                @Query("key") key: String= BuildConfig.WEATHER_API_KEY): WeatherResponse

    @GET("forecast.json")
        suspend fun getForecastTest(@Query("q") latLon: String,
                            @Query("days") days: String,
                            @Query("key") key: String= BuildConfig.WEATHER_API_KEY): Response<WeatherResponse>
}