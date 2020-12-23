package com.bignerdranch.android.restaurantsapp.viewmodel.restaurant

import androidx.databinding.BaseObservable
import com.bignerdranch.android.restaurantsapp.network.restaurants.RestaurantDetail

class RestaurantDetailViewModel(
    restaurantDetail: RestaurantDetail
): BaseObservable(){
    val name: String = restaurantDetail.name
    val location: String = "${restaurantDetail.location.city}, ${restaurantDetail.location.address1}"
    val isClose: String = restaurantDetail.isClosed()
    val note: String= restaurantDetail.note
    val categories = restaurantDetail.categories[0].title

}
