package com.bignerdranch.android.restaurantsapp.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import com.bignerdranch.android.restaurantsapp.data.Plan
import com.bignerdranch.android.restaurantsapp.repository.IPlanRepository

class FakePlanRepositoryTest : IPlanRepository {

    private val plans = mutableListOf<Plan>()

    private val getPlan = MutableLiveData<List<Plan>>(plans)

    private fun refreshLiveData() {
        getPlan.postValue(plans)
    }

    override fun getPlan(): LiveData<List<Plan>> {
        return getPlan
    }

    override fun getFavPlace(planID: String): LiveData<List<PlacesDetail>> {
        TODO("Not yet implemented")
    }

    override fun getFavDetails(placeID: String): LiveData<PlacesDetail> {
        TODO("Not yet implemented")
    }

    override suspend fun addPlan(plan: Plan) {
        plans.add(plan)
        refreshLiveData()
    }

    override suspend fun updatePlan(plan: Plan) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlan(plan: Plan) {
        plans.remove(plan)
        refreshLiveData()
    }

    override suspend fun addFavPlace(placesDetail: PlacesDetail) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavDetails(placesDetail: PlacesDetail) {
        TODO("Not yet implemented")
    }


}