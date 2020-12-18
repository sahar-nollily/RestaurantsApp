package com.bignerdranch.android.restaurantsapp.weather

data class Weather(
    val temp_c: Double,
    val temp_f: Double,
    val condition: WeatherCondition)

data class Forecast(
    val time_epoch : String,
    val time: String,
    val temp_c: Double,
    val temp_f: Double,
    val condition: WeatherCondition)

data class WeatherCondition(
    val text: String,
    val icon: String)
