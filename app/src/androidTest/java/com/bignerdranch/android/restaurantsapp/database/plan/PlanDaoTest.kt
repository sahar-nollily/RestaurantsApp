package com.bignerdranch.android.restaurantsapp.database.plan

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.bignerdranch.android.restaurantsapp.data.Plan
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
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class PlanDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instanceTaskExpectedRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var appDatabase: AppDatabase
    private lateinit var planDao: PlanDao

    @Before
    fun setup(){
        hiltRule.inject()

        planDao = appDatabase.planDao()
    }

    @After
    fun tearDown(){
        appDatabase.close()
    }

    @Test
    fun addPlan()= runBlockingTest {
        val plan = Plan(2, "name", "yellow", "date", "test")
        planDao.addPlan(plan)
        val plans = planDao.getPlan().getOrAwaitValue()

        assertThat(plans).contains(plan)
    }

    @Test
    fun deletePlan()= runBlockingTest {
        val plan = Plan(2, "name", "yellow", "date", "test")
        planDao.addPlan(plan)
        planDao.deletePlan(plan)
        val plans = planDao.getPlan().getOrAwaitValue()

        assertThat(plans).doesNotContain(plans)
    }


}