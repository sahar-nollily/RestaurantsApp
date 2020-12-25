package com.bignerdranch.android.restaurantsapp

import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.restaurantsapp.yelp.YelpApi
import com.bignerdranch.android.restaurantsapp.repository.YelpRepository
import com.bignerdranch.android.restaurantsapp.database.RestaurantDatabase
import com.bignerdranch.android.restaurantsapp.weather.WeatherApi
import com.bignerdranch.android.restaurantsapp.repository.WeatherRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private lateinit var app: App
    private lateinit var yelpRetrofit: Retrofit
    private lateinit var weatherRetrofit: Retrofit
    private lateinit var yelpApi: YelpApi
    private lateinit var weatherApi: WeatherApi
    private lateinit var restaurantDatabase: RestaurantDatabase


    fun init(app: App) {
        this.app = app
        initializeYelpNetwork()
        initializeWeatherNetwork()
        initializeDatabase(app)
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

    private fun initializeDatabase(context: Context) {
        restaurantDatabase = Room.databaseBuilder(
            context,
            RestaurantDatabase::class.java,
            "restaurant_db"
        ).build()
    }


    val yelpRepository: YelpRepository by lazy {
        YelpRepository(yelpApi, restaurantDatabase.restaurantDao())
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherApi)
    }
}