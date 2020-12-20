package com.bignerdranch.android.restaurantsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.restaurantsapp.yelp.Restaurant

@Database(entities = [Restaurant::class], version = 1)
@TypeConverters(RestaurantTypeConverters::class)
abstract class RestaurantDatabase: RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
}