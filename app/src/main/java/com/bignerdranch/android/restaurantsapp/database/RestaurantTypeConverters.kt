package com.bignerdranch.android.restaurantsapp.database

import androidx.room.TypeConverter
import com.bignerdranch.android.restaurantsapp.yelp.YelpCategory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RestaurantTypeConverters {
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
}