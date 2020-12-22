package com.bignerdranch.android.restaurantsapp.network.weather

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.bignerdranch.android.restaurantsapp.network.restaurants.Restaurant


@Entity(foreignKeys = [ForeignKey(
        entity = Restaurant::class,
        parentColumns = arrayOf("restaurantID"),
        childColumns = arrayOf("weatherID"),
        onDelete = CASCADE)])
data class Weather(
        @PrimaryKey var weatherID: String,
        val temp_c: Double,
        val temp_f: Double,
        val condition: WeatherCondition)

data class Forecast(
    val time_epoch : String,
    val time: String,
    val temp_c: Double,
    val temp_f: Double,
    val condition: WeatherCondition)

data class WeatherCondition(
    val text: String,
    val icon: String)
