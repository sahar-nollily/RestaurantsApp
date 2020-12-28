package com.bignerdranch.android.restaurantsapp.database.places

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.bignerdranch.android.restaurantsapp.data.Category
import com.bignerdranch.android.restaurantsapp.data.Places
import com.bignerdranch.android.restaurantsapp.data.PlacesLocation
import com.bignerdranch.android.restaurantsapp.database.AppDatabase
import com.bignerdranch.android.restaurantsapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class PlaceDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instanceTaskExpectedRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var appDatabase: AppDatabase
    private lateinit var placeDao: PlaceDao

    @Before
    fun setup(){
        hiltRule.inject()

        placeDao = appDatabase.placeDao()
    }

    @After
    fun tearDown(){
        appDatabase.close()
    }

    @Test
    fun addPlace()= runBlockingTest {
        val place = Places("id","name",1.0,1.0,"imageUrl",
            listOf(Category("title")), PlacesLocation(0.0,0.0)
        )
        placeDao.addPlace(place)
        val places = placeDao.getPlace().getOrAwaitValue()

        assertThat(places).contains(place)
    }

    @Test
    fun deletePlace()= runBlockingTest {
        val place = Places("id","name",1.0,1.0,"imageUrl",
            listOf(Category("title")), PlacesLocation(0.0,0.0)
        )
        placeDao.addPlace(place)
        placeDao.deletePlace()

        val places = placeDao.getPlace().getOrAwaitValue()

        assertThat(places).doesNotContain(place)
    }


}