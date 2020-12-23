package com.bignerdranch.android.restaurantsapp.repository

import com.bignerdranch.android.restaurantsapp.database.plan.Plan
import com.bignerdranch.android.restaurantsapp.database.plan.PlanDao
import com.bignerdranch.android.restaurantsapp.network.restaurants.RestaurantDetail

class PlanRepository (private val planDao: PlanDao){

    val getPlan = planDao.getPlan()
    fun getFavPlace(planID: String) = planDao.getFavPlace(planID)
    fun getFavDetails(favID: String) = planDao.getFavDetails(favID)

    suspend fun addPlan(plan: Plan){
        planDao.addPlan(plan)
    }

    suspend fun updatePlan(plan: Plan){
        planDao.updatePlan(plan)
    }

    suspend fun deletePlan(plan: Plan){
        planDao.deletePlan(plan)
    }

    suspend fun addFavPlace(restaurantDetail: RestaurantDetail){
        planDao.addFavPlace(restaurantDetail)
    }


}