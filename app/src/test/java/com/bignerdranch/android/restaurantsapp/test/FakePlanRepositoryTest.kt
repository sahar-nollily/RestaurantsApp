package com.bignerdranch.android.restaurantsapp.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.restaurantsapp.data.Plan

class FakePlanRepositoryTest : PlanTest {

    private val plans = mutableListOf<Plan>()

    private val getPlan = MutableLiveData<List<Plan>>(plans)

    private fun refreshLiveData() {
        getPlan.postValue(plans)
    }

    override fun getPlan(): LiveData<List<Plan>> {
        return getPlan
    }

    override suspend fun addPlan(plan: Plan) {
        plans.add(plan)
        refreshLiveData()
    }

    override suspend fun deletePlan(plan: Plan) {
        plans.remove(plan)
        refreshLiveData()
    }


}