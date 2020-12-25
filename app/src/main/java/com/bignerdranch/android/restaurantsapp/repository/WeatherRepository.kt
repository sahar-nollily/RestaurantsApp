package com.bignerdranch.android.restaurantsapp.repository

import com.bignerdranch.android.restaurantsapp.database.weather.WeatherDao
import com.bignerdranch.android.restaurantsapp.data.Forecast
import com.bignerdranch.android.restaurantsapp.data.Weather
import com.bignerdranch.android.restaurantsapp.network.weather.WeatherApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi,
                                                 private val weatherDao: WeatherDao) {

    suspend fun getWeather(weatherID: String): Weather {
        return weatherDao.getWeather(weatherID)
    }

    suspend fun getForecast(latLon: String, days:String,index: Int= 0): List<Forecast> {
        return weatherApi.getForecast(latLon, days).forecast.forecastday[index].hour
    }

}