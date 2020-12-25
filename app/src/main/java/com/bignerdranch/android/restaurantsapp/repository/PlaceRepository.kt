package com.bignerdranch.android.restaurantsapp.repository

import com.bignerdranch.android.restaurantsapp.database.places.PlaceDao
import com.bignerdranch.android.restaurantsapp.network.places.PlaceApi
import com.bignerdranch.android.restaurantsapp.network.places.Places
import com.bignerdranch.android.restaurantsapp.network.places.PlacesDetail
import com.bignerdranch.android.restaurantsapp.network.weather.Weather
import com.bignerdranch.android.restaurantsapp.network.weather.WeatherApi

class PlaceRepository(
    private val placeApi: PlaceApi,
    private val placeDao: PlaceDao,
    private val weatherApi: WeatherApi
){

    suspend fun getPlace() : List<Places> =placeDao.getPlace()

    suspend fun getPlaces(authorization: String, term:String,latitude: String, longitude: String):List<Places>{
        deleteWeather()
        deletePlace()
        val places = placeApi.getPlaces(authorization, term, latitude, longitude).places
        placeDao.addPlace(*places.map {
            Places(
                    it.placeID,
                    it.name,
                    it.rating,
                    it.distanceInMeters,
                    it.imageUrl,
                    it.categories,
                    it.coordinates
            )
        }.toTypedArray()
        )
        places.map {
        val weather = weatherApi.getWeather("803bb8a53fdd48baaa0113628201712","${it.coordinates.latitude}, ${it.coordinates.longitude}").current
        placeDao.addWeather(
            Weather(it.placeID,
            weather.temp_c,
            weather.temp_f,
            weather.condition))
        }
        return places
    }

    suspend fun placeDetails(authorization: String, placeId: String): PlacesDetail {
        return placeApi.placeDetails(authorization,placeId)
    }

    private suspend fun deletePlace()=placeDao.deletePlace()

    private suspend fun deleteWeather()=placeDao.deleteWeather()


}