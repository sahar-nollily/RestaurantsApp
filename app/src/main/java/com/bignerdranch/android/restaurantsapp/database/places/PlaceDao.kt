package com.bignerdranch.android.restaurantsapp.database.places

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bignerdranch.android.restaurantsapp.network.places.Places
import com.bignerdranch.android.restaurantsapp.network.weather.Weather

@Dao
interface PlaceDao{

    @Query("SELECT * FROM Places")
    suspend fun getPlace(): List<Places>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlace(vararg places: Places)

    @Query("DELETE FROM Places")
    suspend fun deletePlace()

    @Query("DELETE FROM Weather")
    suspend fun deleteWeather()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWeather(weather: Weather)

}