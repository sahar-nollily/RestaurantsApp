package com.bignerdranch.android.restaurantsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bignerdranch.android.restaurantsapp.yelp.Restaurant

@Dao
interface RestaurantDao{

    @Query("SELECT * FROM Restaurant")
    suspend fun getRestaurant(): List<Restaurant>

    @Insert
    suspend fun addRestaurant(vararg restaurant: Restaurant)

    @Query("DELETE FROM Restaurant")
    suspend fun deleteRestaurants()
}