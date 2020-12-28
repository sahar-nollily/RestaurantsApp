package com.bignerdranch.android.restaurantsapp.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bignerdranch.android.restaurantsapp.MainCoroutineRule
import com.bignerdranch.android.restaurantsapp.getOrAwaitValueTest
import com.bignerdranch.android.restaurantsapp.viewmodel.plan.PlanViewModel
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

    private lateinit var planViewModel: PlanViewModel

    @Before
    fun setup() {
        planViewModel = PlanViewModel(FakePlanRepositoryTest())
    }

    @Test
    fun `add plan with empty field, returns error`() {
        planViewModel.addPlan("name", "", "date","")

        val value = planViewModel.getPlan.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }


    @Test
    fun `add plan with valid input, returns success`() {
        planViewModel.addPlan("name", "color", "date","description")

        val value = planViewModel.getPlan.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

}