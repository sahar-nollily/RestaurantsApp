package com.bignerdranch.android.restaurantsapp.database

import androidx.room.TypeConverter
import com.bignerdranch.android.restaurantsapp.network.places.Category
import com.bignerdranch.android.restaurantsapp.network.places.PlacesLocation
import com.bignerdranch.android.restaurantsapp.network.places.RestaurantLocation
import com.bignerdranch.android.restaurantsapp.network.weather.WeatherCondition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppTypeConverters {
    @TypeConverter
    fun fromList(title: List<Category>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Category>>() {}.type
        return gson.toJson(title, type)
    }

    @TypeConverter
    fun toList(title: String): List<Category> {
        val gson = Gson()
        val type = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson<List<Category>>(title, type)
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

    @TypeConverter
    fun fromRestaurantLocation(restaurantLocation: RestaurantLocation):String{
        return "${restaurantLocation.city},${restaurantLocation.address1}"
    }

    @TypeConverter
    fun toRestaurantLocation(location :String): RestaurantLocation {
        val x = location.split(",")
        return RestaurantLocation(x[0],x[1])
    }

    @TypeConverter
    fun fromPhotoList(photo: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(photo, type)
    }

    @TypeConverter
    fun toPhotoList(photo: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson<List<String>>(photo, type)
    }


}