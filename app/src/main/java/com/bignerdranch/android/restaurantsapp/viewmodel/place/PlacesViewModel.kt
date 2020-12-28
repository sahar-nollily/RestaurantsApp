package com.bignerdranch.android.restaurantsapp.viewmodel.place

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.restaurantsapp.data.PlaceReview
import com.bignerdranch.android.restaurantsapp.data.Places
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import com.bignerdranch.android.restaurantsapp.data.ReviewResponse
import com.bignerdranch.android.restaurantsapp.repository.PlaceRepository
import kotlinx.coroutines.launch

class PlacesViewModel @ViewModelInject constructor(
    private val yelpRepository: PlaceRepository
): ViewModel(){

    private var place = MutableLiveData<List<Places>>()
    private val placesDetail = MutableLiveData<PlacesDetail>()
    private val placeReview = MutableLiveData<ReviewResponse>()


    fun getPlace() : LiveData<List<Places>> {
        place = yelpRepository.getPlace() as MutableLiveData<List<Places>>
        return place
    }

    fun getPlaces(term:String,latitude: String, longitude: String): LiveData<List<Places>> {
        viewModelScope.launch {
            place.value= yelpRepository.getPlaces(term, latitude, longitude)
        }
        return place
    }

    fun placeDetails(placeId: String): LiveData<PlacesDetail> {
        viewModelScope.launch {
            placesDetail.value = yelpRepository.placeDetails(placeId)
        }
        return placesDetail
    }

    fun placeReview(placeId: String): LiveData<ReviewResponse> {
        viewModelScope.launch {
            placeReview.value = yelpRepository.placeReview(placeId)
        }
        return placeReview
    }

}