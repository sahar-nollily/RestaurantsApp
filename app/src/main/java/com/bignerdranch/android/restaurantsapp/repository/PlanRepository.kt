package com.bignerdranch.android.restaurantsapp.repository

import com.bignerdranch.android.restaurantsapp.database.plan.Plan
import com.bignerdranch.android.restaurantsapp.database.plan.PlanDao
import com.bignerdranch.android.restaurantsapp.network.places.PlacesDetail

class PlanRepository (private val planDao: PlanDao){

    val getPlan = planDao.getPlan()
    fun getFavPlace(planID: String) = planDao.getFavPlace(planID)
    fun getFavDetails(placeID: String) = planDao.getFavDetails(placeID)

    suspend fun addPlan(plan: Plan){
        planDao.addPlan(plan)
    }

    suspend fun updatePlan(plan: Plan){
        planDao.updatePlan(plan)
    }

    suspend fun deletePlan(plan: Plan){
        planDao.deletePlan(plan)
    }

    suspend fun addFavPlace(placesDetail: PlacesDetail){
        planDao.addFavPlace(placesDetail)
    }

    suspend fun deleteFavDetails(placesDetail: PlacesDetail){
        planDao.deleteFavDetails(placesDetail)
    }

}