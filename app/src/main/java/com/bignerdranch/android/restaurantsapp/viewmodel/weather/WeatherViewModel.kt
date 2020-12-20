package com.bignerdranch.android.restaurantsapp.viewmodel.weather

import androidx.databinding.BaseObservable
import com.bignerdranch.android.restaurantsapp.Yelp.Restaurant
import com.bignerdranch.android.restaurantsapp.weather.Weather

class WeatherViewModel (
    weather: Weather
) : BaseObservable() {
    val temp_c: Double = weather.temp_c
    val temp_f: Double = weather.temp_f
    val text: String = weather.condition.text
    val icon: String = weather.condition.icon
}