package com.bignerdranch.android.restaurantsapp

import com.bignerdranch.android.restaurantsapp.Yelp.YelpApi
import com.bignerdranch.android.restaurantsapp.Yelp.YelpRepository
import com.bignerdranch.android.restaurantsapp.weather.WeatherApi
import com.bignerdranch.android.restaurantsapp.weather.WeatherRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private lateinit var app: App
    lateinit var yelpRetrofit: Retrofit
    lateinit var weatherRetrofit: Retrofit
    private lateinit var yelpApi: YelpApi
    private lateinit var weatherApi: WeatherApi


    fun init(app: App) {
        this.app = app
        initializeYelpNetwork()
        initializeWeatherNetwork()
    }

    private fun initializeYelpNetwork() {
        yelpRetrofit = Retrofit.Builder()
            .baseUrl(" https://api.yelp.com/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        yelpApi = yelpRetrofit.create(YelpApi::class.java)

    }

    private fun initializeWeatherNetwork() {
        weatherRetrofit = Retrofit.Builder()
                .baseUrl(" https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        weatherApi = weatherRetrofit.create(WeatherApi::class.java)

    }

    val yelpRepository: YelpRepository by lazy {
        YelpRepository(yelpApi)
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherApi)
    }
}