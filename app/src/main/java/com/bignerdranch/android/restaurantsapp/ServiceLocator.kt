package com.bignerdranch.android.restaurantsapp

import com.bignerdranch.android.restaurantsapp.Yelp.YelpApi
import com.bignerdranch.android.restaurantsapp.Yelp.YelpRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private lateinit var app: App
    lateinit var retrofit: Retrofit
    private lateinit var yelpApi: YelpApi

    fun init(app: App) {
        this.app = app
        initializeNetwork()
    }

    private fun initializeNetwork() {
        retrofit = Retrofit.Builder()
            .baseUrl(" https://api.yelp.com/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        yelpApi = retrofit.create(YelpApi::class.java)

    }

    val yelpRepository: YelpRepository by lazy {
        YelpRepository(yelpApi)
    }
}