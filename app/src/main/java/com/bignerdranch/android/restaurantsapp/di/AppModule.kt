package com.bignerdranch.android.restaurantsapp.di

import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.restaurantsapp.database.AppDatabase
import com.bignerdranch.android.restaurantsapp.database.places.PlaceDao
import com.bignerdranch.android.restaurantsapp.database.plan.PlanDao
import com.bignerdranch.android.restaurantsapp.database.weather.WeatherDao
import com.bignerdranch.android.restaurantsapp.network.places.PlaceApi
import com.bignerdranch.android.restaurantsapp.network.weather.WeatherApi
import com.bignerdranch.android.restaurantsapp.repository.PlaceRepository
import com.bignerdranch.android.restaurantsapp.repository.PlanRepository
import com.bignerdranch.android.restaurantsapp.repository.WeatherRepository
import com.bignerdranch.android.restaurantsapp.test.FakePlanRepository
import com.bignerdranch.android.restaurantsapp.test.PlanTest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    @Named("initializeYelpNetwork")
    fun initializeYelpNetwork(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.yelp.com/v3/")
            .build()
    }

    @Singleton
    @Provides
    @Named("initializeWeatherNetwork")
    fun initializeWeatherNetwork(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.weatherapi.com/v1/")
            .build()
    }

    @Singleton
    @Provides
    fun initializeYelpDatabase(@ApplicationContext context: Context):AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "place_db"
        ).build()
    }

    @Singleton
    @Provides
    @Named("providePlaceApi")
    fun providePlaceApi(retrofit: Retrofit): PlaceApi {
        return retrofit.create(PlaceApi::class.java)
    }


    @Singleton
    @Provides
    @Named("provideWeatherApi")
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    @Named("providePlaceRepository")
    fun providePlaceRepository(placeApi: PlaceApi, placeDao: PlaceDao, weatherApi: WeatherApi): PlaceRepository {
        return PlaceRepository(placeApi, placeDao,weatherApi)
    }


    @Singleton
    @Provides
    @Named("providePlaceDao")
    fun providePlaceDao(appDatabase: AppDatabase): PlaceDao {
        return appDatabase.placeDao()
    }

    @Singleton
    @Provides
    @Named("providePlanRepository")
    fun providePlanRepository(placeDao: PlaceDao): PlanRepository {
        return providePlanRepository(placeDao)
    }


    @Singleton
    @Provides
    @Named("providePlanDao")
    fun providePlanDao(appDatabase: AppDatabase): PlanDao {
        return appDatabase.planDao()
    }

    @Singleton
    @Provides
    @Named("provideWeatherRepository")
    fun provideWeatherRepository(weatherApi: WeatherApi, weatherDao: WeatherDao): WeatherRepository {
        return provideWeatherRepository(weatherApi, weatherDao)
    }


    @Singleton
    @Provides
    @Named("provideWeatherDao")
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }

    @Provides
    fun placeApi (): PlaceApi {
        return AppModule_ProvidePlaceApiFactory.providePlaceApi(initializeYelpNetwork())
    }

    @Provides
    fun weatherApi (): WeatherApi {
        return AppModule_ProvideWeatherApiFactory.provideWeatherApi(initializeWeatherNetwork())
    }


    @Provides
    fun placeDao (@ApplicationContext context: Context): PlaceDao {
        return AppModule_ProvidePlaceDaoFactory.providePlaceDao(initializeYelpDatabase(context))
    }


    @Provides
    fun planDao (@ApplicationContext context: Context): PlanDao {
        return AppModule_ProvidePlanDaoFactory.providePlanDao(initializeYelpDatabase(context))
    }

    @Provides
    fun weatherDao (@ApplicationContext context: Context): WeatherDao {
        return AppModule_ProvideWeatherDaoFactory.provideWeatherDao(initializeYelpDatabase(context))
    }

    @Singleton
    @Provides
    fun provideFakeWeatherRepository(
        planDao: PlanDao
    ) = FakePlanRepository(planDao) as PlanTest

}