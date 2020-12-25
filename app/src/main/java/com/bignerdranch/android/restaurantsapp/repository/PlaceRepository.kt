package com.bignerdranch.android.restaurantsapp.repository

import com.bignerdranch.android.restaurantsapp.database.places.PlaceDao
import com.bignerdranch.android.restaurantsapp.network.places.PlaceApi
import com.bignerdranch.android.restaurantsapp.data.Places
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import com.bignerdranch.android.restaurantsapp.data.ReviewResponse
import com.bignerdranch.android.restaurantsapp.data.Weather
import com.bignerdranch.android.restaurantsapp.network.weather.WeatherApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
    private val placeApi: PlaceApi,
    private val placeDao: PlaceDao,
    private val weatherApi: WeatherApi
){

    suspend fun getPlace() : List<Places> =placeDao.getPlace()

    suspend fun getPlaces(term:String,latitude: String, longitude: String):List<Places>{
        deleteWeather()
        deletePlace()
        val places = placeApi.getPlaces(term, latitude, longitude).places
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
        val weather = weatherApi.getWeather("${it.coordinates.latitude}, ${it.coordinates.longitude}").current
        placeDao.addWeather(
                Weather(it.placeID,
                        weather.temp_c,
                        weather.temp_f,
                        weather.condition))
        }
        return places
    }

    suspend fun placeDetails(placeId: String): PlacesDetail {
        return placeApi.placeDetails(placeId)
    }

    suspend fun placeReview(placeId: String): ReviewResponse {
        return placeApi.placeReview(placeId)
    }

    private suspend fun deletePlace()=placeDao.deletePlace()

    private suspend fun deleteWeather()=placeDao.deleteWeather()


}