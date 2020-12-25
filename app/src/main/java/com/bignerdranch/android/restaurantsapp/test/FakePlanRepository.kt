package com.bignerdranch.android.restaurantsapp.test

import androidx.lifecycle.LiveData
import com.bignerdranch.android.restaurantsapp.data.Plan
import com.bignerdranch.android.restaurantsapp.database.plan.PlanDao
import javax.inject.Inject


class FakePlanRepository @Inject constructor(private val planDao: PlanDao)
    : PlanTest {
    override fun getPlan(): LiveData<List<Plan>> {
        return planDao.getPlan()
    }

    override suspend fun addPlan(plan: Plan) {
        planDao.addPlan(plan)
    }

    override suspend fun deletePlan(plan: Plan) {
        planDao.deletePlan(plan)
    }


}