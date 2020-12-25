package com.bignerdranch.android.restaurantsapp.repository

import com.bignerdranch.android.restaurantsapp.database.weather.WeatherDao
import com.bignerdranch.android.restaurantsapp.network.weather.Forecast
import com.bignerdranch.android.restaurantsapp.network.weather.Weather
import com.bignerdranch.android.restaurantsapp.network.weather.WeatherApi

class WeatherRepository (private val weatherApi: WeatherApi, private val weatherDao: WeatherDao){

    suspend fun getWeather(weatherID: String): Weather{
        return weatherDao.getWeather(weatherID)
    }

    suspend fun getWeather(key:String,latLon: String): Weather {
        return weatherApi.getWeather(key, latLon).current
    }

    suspend fun getForecast(key:String,latLon: String, days:String,index: Int= 0): List<Forecast> {
        return weatherApi.getForecast(key, latLon, days).forecast.forecastday[index].hour
    }

}