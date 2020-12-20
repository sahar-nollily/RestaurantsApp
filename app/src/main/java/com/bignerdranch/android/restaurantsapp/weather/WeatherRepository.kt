package com.bignerdranch.android.restaurantsapp.weather

class WeatherRepository (private val weatherApi: WeatherApi){

    suspend fun getWeather(key:String,latLon: String):Weather {
        return weatherApi.getWeather(key, latLon).current
    }

    suspend fun getForecast(key:String,latLon: String, days:String,index: Int= 0): List<Forecast> {
        return weatherApi.getForecast(key, latLon, days).forecast.forecastday[index].hour
    }

}