package com.bignerdranch.android.restaurantsapp.Yelp

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpApi{

    @GET("businesses/search")
    suspend fun getRestaurants(@Header("Authorization") authorization: String,
                               @Query("term") term: String,
                               @Query("latitude") latitude: String,
                               @Query("longitude") longitude: String): YelpResponse
}