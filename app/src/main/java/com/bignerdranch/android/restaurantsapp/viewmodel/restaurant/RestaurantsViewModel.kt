package com.bignerdranch.android.restaurantsapp.viewmodel.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.restaurantsapp.ServiceLocator
import com.bignerdranch.android.restaurantsapp.yelp.PlacesLocation
import com.bignerdranch.android.restaurantsapp.yelp.Restaurant
import com.bignerdranch.android.restaurantsapp.yelp.RestaurantDetail
import kotlinx.coroutines.launch

class RestaurantsViewModel : ViewModel(){
    private val yelpRepository= ServiceLocator.yelpRepository
    private val restaurant = MutableLiveData<List<Restaurant>>()
    private val restaurantDetail = MutableLiveData<RestaurantDetail>()


    fun getRestaurant() : LiveData<List<Restaurant>> {
        viewModelScope.launch {
        restaurant.value = yelpRepository.getRestaurant()
        }
        return restaurant
    }

    fun getRestaurants(authorization: String, term:String,latitude: String, longitude: String): LiveData<List<Restaurant>> {
        viewModelScope.launch {
            restaurant.value= yelpRepository.getRestaurants(authorization, term, latitude, longitude)
        }
        return restaurant
    }

    fun restaurantDetails(authorization: String, restaurantId: String): LiveData<RestaurantDetail> {
        viewModelScope.launch {
            restaurantDetail.value = yelpRepository.restaurantDetails(authorization, restaurantId)
        }
        return restaurantDetail
    }

}