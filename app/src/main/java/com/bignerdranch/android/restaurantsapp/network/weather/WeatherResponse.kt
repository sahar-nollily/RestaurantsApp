package com.bignerdranch.android.restaurantsapp.network.weather

import com.bignerdranch.android.restaurantsapp.data.Weather

class WeatherResponse{
    lateinit var current: Weather
    lateinit var forecast: ForecastResponse
}