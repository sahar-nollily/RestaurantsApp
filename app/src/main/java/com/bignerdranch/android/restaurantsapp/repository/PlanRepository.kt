package com.bignerdranch.android.restaurantsapp.repository

import com.bignerdranch.android.restaurantsapp.data.Plan
import com.bignerdranch.android.restaurantsapp.database.plan.PlanDao
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlanRepository @Inject constructor(private val planDao: PlanDao) : IPlanRepository {

    override fun getPlan() = planDao.getPlan()
    override fun getFavPlace(planID: String) = planDao.getFavPlace(planID)
    override fun getFavDetails(placeID: String) = planDao.getFavDetails(placeID)



    override suspend fun addPlan(plan: Plan){
        planDao.addPlan(plan)
    }

    override suspend fun updatePlan(plan: Plan){
        planDao.updatePlan(plan)
    }

    override suspend fun deletePlan(plan: Plan){
        planDao.deletePlan(plan)
    }

    override suspend fun addFavPlace(placesDetail: PlacesDetail){
        planDao.addFavPlace(placesDetail)
    }

    override suspend fun setTimeNotification(favID: String, time: String){
        planDao.setTimeNotification(favID, time)
    }

    override suspend fun deleteFavDetails(placesDetail: PlacesDetail){
        planDao.deleteFavDetails(placesDetail)
    }

}