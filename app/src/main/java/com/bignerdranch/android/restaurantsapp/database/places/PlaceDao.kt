package com.bignerdranch.android.restaurantsapp.database.places

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bignerdranch.android.restaurantsapp.data.Places
import com.bignerdranch.android.restaurantsapp.data.Weather

@Dao
interface PlaceDao{

    @Query("SELECT * FROM Places")
    fun getPlace(): LiveData<List<Places>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlace(vararg places: Places)

    @Query("DELETE FROM Places")
    suspend fun deletePlace()

    @Query("DELETE FROM Weather")
    suspend fun deleteWeather()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWeather(weather: Weather)

}