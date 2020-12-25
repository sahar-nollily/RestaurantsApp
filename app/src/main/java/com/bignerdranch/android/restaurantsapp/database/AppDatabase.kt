package com.bignerdranch.android.restaurantsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.restaurantsapp.database.places.PlaceDao
import com.bignerdranch.android.restaurantsapp.data.Plan
import com.bignerdranch.android.restaurantsapp.database.plan.PlanDao
import com.bignerdranch.android.restaurantsapp.database.weather.WeatherDao
import com.bignerdranch.android.restaurantsapp.data.Places
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import com.bignerdranch.android.restaurantsapp.data.Weather

@Database(entities = [Places::class, Weather::class, Plan::class, PlacesDetail::class], version = 1)
@TypeConverters(AppTypeConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun weatherDao(): WeatherDao
    abstract fun planDao(): PlanDao

}