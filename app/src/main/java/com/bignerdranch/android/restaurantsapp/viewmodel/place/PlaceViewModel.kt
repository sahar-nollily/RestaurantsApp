package com.bignerdranch.android.restaurantsapp.viewmodel.place

import androidx.databinding.BaseObservable
import com.bignerdranch.android.restaurantsapp.network.places.Places

class PlaceViewModel (
    places: Places,
) : BaseObservable() {
    val name: String = places.name
    val rating: Float = places.rating.toFloat()
    val distanceInMeters = places.displayDistance()
    val categories = places.categories[0].title

}