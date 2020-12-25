package com.bignerdranch.android.restaurantsapp.viewmodel.place

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.restaurantsapp.data.Places
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import com.bignerdranch.android.restaurantsapp.repository.PlaceRepository
import kotlinx.coroutines.launch

class PlacesViewModel @ViewModelInject constructor(
    private val yelpRepository: PlaceRepository
): ViewModel(){

    private val place = MutableLiveData<List<Places>>()
    private val placesDetail = MutableLiveData<PlacesDetail>()


    fun getPlace() : LiveData<List<Places>> {
        viewModelScope.launch {
            place.value = yelpRepository.getPlace()
        }
        return place
    }

    fun getPlaces(term:String,latitude: String, longitude: String): LiveData<List<Places>> {
        viewModelScope.launch {
            place.value= yelpRepository.getPlaces(term, latitude, longitude)
        }
        return place
    }

    fun placeDetails(restaurantId: String): LiveData<PlacesDetail> {
        viewModelScope.launch {
            placesDetail.value = yelpRepository.placeDetails(restaurantId)
        }
        return placesDetail
    }

}