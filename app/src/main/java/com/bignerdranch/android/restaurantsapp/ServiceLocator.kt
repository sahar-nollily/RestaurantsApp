package com.bignerdranch.android.restaurantsapp

import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.restaurantsapp.network.places.PlaceApi
import com.bignerdranch.android.restaurantsapp.repository.PlaceRepository
import com.bignerdranch.android.restaurantsapp.database.AppDatabase
import com.bignerdranch.android.restaurantsapp.network.weather.WeatherApi
import com.bignerdranch.android.restaurantsapp.repository.PlanRepository
import com.bignerdranch.android.restaurantsapp.repository.WeatherRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private lateinit var app: App
    private lateinit var yelpRetrofit: Retrofit
    private lateinit var weatherRetrofit: Retrofit
    private lateinit var placeApi: PlaceApi
    private lateinit var weatherApi: WeatherApi
    private lateinit var appDatabase: AppDatabase


    fun init(app: App) {
        this.app = app
        initializeYelpNetwork()
        initializeWeatherNetwork()
        initializeYelpDatabase(app)
    }

    private fun initializeYelpNetwork() {
        yelpRetrofit = Retrofit.Builder()
            .baseUrl(" https://api.yelp.com/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        placeApi = yelpRetrofit.create(PlaceApi::class.java)

    }

    private fun initializeWeatherNetwork() {
        weatherRetrofit = Retrofit.Builder()
                .baseUrl(" https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        weatherApi = weatherRetrofit.create(WeatherApi::class.java)

    }

    private fun initializeYelpDatabase(context: Context) {
        appDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "place_db"
        ).build()
    }

    val placeRepository: PlaceRepository by lazy {
        PlaceRepository(placeApi, appDatabase.placeDao(),weatherApi)
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherApi, appDatabase.weatherDao())
    }

    val planRepository: PlanRepository by lazy {
        PlanRepository(appDatabase.planDao())
    }
}