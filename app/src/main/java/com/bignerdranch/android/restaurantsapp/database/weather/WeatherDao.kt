package com.bignerdranch.android.restaurantsapp.database.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bignerdranch.android.restaurantsapp.network.weather.Weather

@Dao
interface WeatherDao{

    @Query("SELECT * FROM Weather WHERE weatherID = :weatherID")
    suspend fun getWeather(weatherID: String): Weather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeather(weather: Weather)
}