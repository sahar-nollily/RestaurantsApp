package com.bignerdranch.android.restaurantsapp.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bignerdranch.android.restaurantsapp.MainCoroutineRule
import com.bignerdranch.android.restaurantsapp.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PlanViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakePlanViewModel: FakePlanViewModel

    @Before
    fun setup() {
        fakePlanViewModel = FakePlanViewModel(FakePlanRepositoryTest())
    }

    @Test
    fun `add plan with empty field, returns error`() {
        fakePlanViewModel.addPlanTest("name", "", "date","")

        val value = fakePlanViewModel.plan.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }


    @Test
    fun `add plan with valid input, returns success`() {
        fakePlanViewModel.addPlanTest("name", "color", "date","description")

        val value = fakePlanViewModel.plan.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

}