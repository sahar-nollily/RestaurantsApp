package com.bignerdranch.android.restaurantsapp.viewmodel.restaurant

import androidx.databinding.BaseObservable
import com.bignerdranch.android.restaurantsapp.yelp.RestaurantDetail

class RestaurantDetailViewModel(
    restaurantDetail: RestaurantDetail
): BaseObservable(){
    val name: String = restaurantDetail.name
    val location: String = "${restaurantDetail.location.city}, ${restaurantDetail.location.address1}"
    val isClose: String = restaurantDetail.isClosed()
    val phone: String = restaurantDetail.phone

}
