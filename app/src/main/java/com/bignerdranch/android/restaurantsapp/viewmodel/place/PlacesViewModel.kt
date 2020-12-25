package com.bignerdranch.android.restaurantsapp.viewmodel.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.restaurantsapp.ServiceLocator
import com.bignerdranch.android.restaurantsapp.network.places.Places
import com.bignerdranch.android.restaurantsapp.network.places.PlacesDetail
import kotlinx.coroutines.launch

class PlacesViewModel : ViewModel(){
    private val yelpRepository= ServiceLocator.placeRepository
    private val place = MutableLiveData<List<Places>>()
    private val placesDetail = MutableLiveData<PlacesDetail>()


    fun getPlace() : LiveData<List<Places>> {
        viewModelScope.launch {
            place.value = yelpRepository.getPlace()
        }
        return place
    }

    fun getPlaces(authorization: String, term:String,latitude: String, longitude: String): LiveData<List<Places>> {
        viewModelScope.launch {
            place.value= yelpRepository.getPlaces(authorization, term, latitude, longitude)
        }
        return place
    }

    fun placeDetails(authorization: String, restaurantId: String): LiveData<PlacesDetail> {
        viewModelScope.launch {
            placesDetail.value = yelpRepository.placeDetails(authorization, restaurantId)
        }
        return placesDetail
    }

}