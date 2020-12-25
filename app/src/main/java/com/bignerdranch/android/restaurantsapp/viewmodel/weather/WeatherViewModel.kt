package com.bignerdranch.android.restaurantsapp.viewmodel.weather

import androidx.databinding.BaseObservable
import com.bignerdranch.android.restaurantsapp.network.weather.Weather

class WeatherViewModel (
    weather: Weather
) : BaseObservable() {
    val temp_c = weather.temp_c.toString()+"C°"
    val temp_f = weather.temp_f.toString()+"C°"
    val text: String? = weather.condition.text
    val icon: String? = weather.condition.icon
}