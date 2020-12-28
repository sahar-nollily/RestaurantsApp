package com.bignerdranch.android.restaurantsapp.repository

import androidx.lifecycle.LiveData
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import com.bignerdranch.android.restaurantsapp.data.Plan

interface IPlanRepository {
    fun getPlan(): LiveData<List<Plan>>
    fun getFavPlace(planID: String): LiveData<List<PlacesDetail>>
    fun getFavDetails(placeID: String): LiveData<PlacesDetail>
    suspend fun setTimeNotification(favID: String, time: String)

    suspend fun addPlan(plan: Plan)
    suspend fun updatePlan(plan: Plan)
    suspend fun deletePlan(plan: Plan)
    suspend fun addFavPlace(placesDetail: PlacesDetail)
    suspend fun deleteFavDetails(placesDetail: PlacesDetail)
}