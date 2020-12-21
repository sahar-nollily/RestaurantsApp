package com.bignerdranch.android.restaurantsapp.viewmodel.weather

import androidx.databinding.BaseObservable
import com.bignerdranch.android.restaurantsapp.weather.Forecast

class ForecastWeatherViewModel (
    forecast: Forecast
) : BaseObservable() {
    val temp_c = forecast.temp_c.toInt().toString()+"C°"
    val temp_f = forecast.temp_f.toInt().toString()+"F°"
    val time = forecast.time.split(" ")[1]
    val weatherText = forecast.condition.text
    val icon: String = forecast.condition.icon
}