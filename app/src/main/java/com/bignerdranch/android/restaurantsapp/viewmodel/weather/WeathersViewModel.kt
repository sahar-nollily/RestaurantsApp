package com.bignerdranch.android.restaurantsapp.viewmodel.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.restaurantsapp.ServiceLocator
import com.bignerdranch.android.restaurantsapp.weather.Forecast
import com.bignerdranch.android.restaurantsapp.weather.Weather
import kotlinx.coroutines.launch

class WeathersViewModel : ViewModel(){
    var weatherRepository = ServiceLocator.weatherRepository
    var weather = MutableLiveData<Weather>()
    var forecast = MutableLiveData<List<Forecast>>()


    fun getWeather(key: String,latLon: String): LiveData<Weather> {
        viewModelScope.launch{
            weather.value = weatherRepository.getWeather(key, latLon)
        }
            return weather
    }

    fun getForecast(key:String,latLon: String, days:String, index: Int= 0): LiveData<List<Forecast>> {
        viewModelScope.launch {
            forecast.value = weatherRepository.getForecast(key, latLon, days, index)
        }
        return forecast
    }
}