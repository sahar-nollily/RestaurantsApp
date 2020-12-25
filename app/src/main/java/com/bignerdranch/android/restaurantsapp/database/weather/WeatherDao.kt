package com.bignerdranch.android.restaurantsapp.database.weather

import androidx.room.Dao
import androidx.room.Query
import com.bignerdranch.android.restaurantsapp.data.Weather

@Dao
interface WeatherDao{

    @Query("SELECT * FROM Weather WHERE weatherID = :weatherID")
    suspend fun getWeather(weatherID: String): Weather

}