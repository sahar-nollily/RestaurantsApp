package com.bignerdranch.android.restaurantsapp.viewmodel.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.restaurantsapp.data.Forecast
import com.bignerdranch.android.restaurantsapp.data.Weather
import com.bignerdranch.android.restaurantsapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeathersViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel(){

    var weather = MutableLiveData<Weather>()
    var forecast = MutableLiveData<List<Forecast>>()

     fun getWeather(weatherID: String): LiveData<Weather>{
        viewModelScope.launch {
            weather.value = weatherRepository.getWeather(weatherID)
        }
        return weather
    }

    fun getForecast(latLon: String, days:String, index: Int= 0): LiveData<List<Forecast>> {
        viewModelScope.launch {
            forecast.value = weatherRepository.getForecast(latLon, days, index)
        }
        return forecast
    }

}