package com.bignerdranch.android.restaurantsapp.viewmodel.place

import androidx.databinding.BaseObservable
import com.bignerdranch.android.restaurantsapp.network.places.PlacesDetail

class PlaceDetailViewModel(
    placesDetail: PlacesDetail
): BaseObservable(){
    val name: String = placesDetail.name
    val location: String = "${placesDetail.location.city}, ${placesDetail.location.address1}"
    val isClose: String = placesDetail.isClosed()
    val note: String= placesDetail.note
    val categories = placesDetail.categories[0].title

}
