package com.bignerdranch.android.restaurantsapp.network.places

import com.bignerdranch.android.restaurantsapp.BuildConfig
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceApi{

    @GET("businesses/search")
    suspend fun getPlaces(@Query("term") term: String,
                               @Query("latitude") latitude: String,
                               @Query("longitude") longitude: String,
                               @Header("Authorization") authorization: String = "Bearer "+BuildConfig.RESTAURANT_API_KEY): PlaceResponse

    @GET("businesses/{id}")
    suspend fun placeDetails(@Path("id")placeId: String,
                             @Header("Authorization") authorization: String = "Bearer "+BuildConfig.RESTAURANT_API_KEY): PlacesDetail

}