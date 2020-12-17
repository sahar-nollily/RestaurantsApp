package com.bignerdranch.android.restaurantsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.restaurantsapp.ServiceLocator
import com.bignerdranch.android.restaurantsapp.Yelp.Restaurant
import kotlinx.coroutines.launch

class RestaurantsViewModel : ViewModel(){
    var yelpRepository= ServiceLocator.yelpRepository
    var restaurant = MutableLiveData<List<Restaurant>>()

    fun getRestaurants(authorization: String, term:String,latitude: String, longitude: String): LiveData<List<Restaurant>> {
        viewModelScope.launch {
            restaurant.value= yelpRepository.getRestaurants(authorization, term, latitude, longitude)
        }
        return restaurant
    }
}