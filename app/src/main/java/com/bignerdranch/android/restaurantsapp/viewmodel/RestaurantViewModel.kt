package com.bignerdranch.android.restaurantsapp.viewmodel

import androidx.databinding.BaseObservable
import com.bignerdranch.android.restaurantsapp.Yelp.Restaurant

class RestaurantViewModel (
    restaurant: Restaurant
) : BaseObservable() {
    val name: String = restaurant.name
    val rating: Float = restaurant.rating.toFloat()
    val distanceInMeters = restaurant.displayDistance()
    val categories = restaurant.categories[0].title

}