package com.bignerdranch.android.restaurantsapp.database

import androidx.room.TypeConverter
import com.bignerdranch.android.restaurantsapp.network.restaurants.PlacesLocation
import com.bignerdranch.android.restaurantsapp.network.restaurants.YelpCategory
import com.bignerdranch.android.restaurantsapp.network.weather.WeatherCondition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppTypeConverters {
    @TypeConverter
    fun fromList(title: List<YelpCategory>): String {
        val gson = Gson()
        val type = object : TypeToken<List<YelpCategory>>() {}.type
        return gson.toJson(title, type)
    }

    @TypeConverter
    fun toList(title: String): List<YelpCategory> {
        val gson = Gson()
        val type = object : TypeToken<List<YelpCategory>>() {}.type
        return gson.fromJson<List<YelpCategory>>(title, type)
    }

    @TypeConverter
    fun fromPlacesLocation(placesLocation: PlacesLocation):String{
        return "${placesLocation.latitude},${placesLocation.longitude}"
    }

    @TypeConverter
    fun toPlacesLocation(location :String):PlacesLocation{
        val x = location.split(",")
        val placesLocation = PlacesLocation(x[0].toDouble(),x[1].toDouble())
        return placesLocation
    }

    @TypeConverter
    fun fromweatherCondition(weatherCondition: WeatherCondition):String{
        return "${weatherCondition.text},${weatherCondition.icon}"
    }

    @TypeConverter
    fun toweatherCondition(condition :String): WeatherCondition {
        val x = condition.split(",")
        return WeatherCondition(x[0],x[1])
    }
}