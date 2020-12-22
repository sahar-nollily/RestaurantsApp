package com.bignerdranch.android.restaurantsapp.database.restaurant

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bignerdranch.android.restaurantsapp.network.restaurants.Restaurant

@Dao
interface RestaurantDao{

    @Query("SELECT * FROM Restaurant")
    suspend fun getRestaurant(): List<Restaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRestaurant(vararg restaurant: Restaurant)

    @Query("DELETE FROM Restaurant")
    suspend fun deleteRestaurants()

    @Query("DELETE FROM Weather")
    suspend fun deleteWeather()

}