package com.bignerdranch.android.restaurantsapp.repository

import com.bignerdranch.android.restaurantsapp.database.plan.Plan
import com.bignerdranch.android.restaurantsapp.database.plan.PlanDao

class PlanRepository (private val planDao: PlanDao){

    val getPlan = planDao.getPlan()

    suspend fun addPlan(plan: Plan){
        planDao.addPlan(plan)
    }

    suspend fun updatePlan(plan: Plan){
        planDao.updatePlan(plan)
    }

    suspend fun deletePlan(plan: Plan){
        planDao.deletePlan(plan)
    }


}