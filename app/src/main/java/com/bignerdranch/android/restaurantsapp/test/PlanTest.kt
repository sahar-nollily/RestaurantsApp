package com.bignerdranch.android.restaurantsapp.test

import androidx.lifecycle.LiveData
import com.bignerdranch.android.restaurantsapp.data.Plan

interface PlanTest{

    fun getPlan(): LiveData<List<Plan>>

    suspend fun addPlan(plan: Plan)

    suspend fun deletePlan(plan: Plan)

}
