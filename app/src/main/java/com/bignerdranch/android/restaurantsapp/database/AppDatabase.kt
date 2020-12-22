package com.bignerdranch.android.restaurantsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.restaurantsapp.database.plan.Plan
import com.bignerdranch.android.restaurantsapp.database.plan.PlanDao
import com.bignerdranch.android.restaurantsapp.database.restaurant.RestaurantDao
import com.bignerdranch.android.restaurantsapp.database.weather.WeatherDao
import com.bignerdranch.android.restaurantsapp.network.restaurants.Restaurant
import com.bignerdranch.android.restaurantsapp.network.weather.Weather

@Database(entities = [Restaurant::class, Weather::class, Plan::class], version = 1)
@TypeConverters(AppTypeConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun weatherDao(): WeatherDao
    abstract fun planDao(): PlanDao

}