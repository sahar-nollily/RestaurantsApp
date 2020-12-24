package com.bignerdranch.android.restaurantsapp.viewmodel.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.restaurantsapp.ServiceLocator
import com.bignerdranch.android.restaurantsapp.database.plan.Plan
import com.bignerdranch.android.restaurantsapp.network.restaurants.RestaurantDetail
import kotlinx.coroutines.launch

class PlanViewModel: ViewModel() {
    private val planRepository= ServiceLocator.planRepository
    val getPlan : LiveData<List<Plan>> = planRepository.getPlan
    fun getFavPlace(planID: String) : LiveData<List<RestaurantDetail>> = planRepository.getFavPlace(planID)
    fun getFavDetails(restaurantID: String) = planRepository.getFavDetails(restaurantID)


    fun addPlan(plan: Plan){
        viewModelScope.launch {
            planRepository.addPlan(plan)
        }
    }

    fun updatePlan(plan: Plan){
        viewModelScope.launch {
            planRepository.updatePlan(plan)
        }
    }

    fun deletePlan(plan: Plan){
        viewModelScope.launch {
            viewModelScope.launch {
                planRepository.deletePlan(plan)
            }
        }
    }

    fun addFavPlace(restaurantDetail: RestaurantDetail){
        viewModelScope.launch {
            planRepository.addFavPlace(restaurantDetail)
        }
    }

    fun deleteFavDetails(restaurantDetail: RestaurantDetail){
        viewModelScope.launch {
            planRepository.deleteFavDetails(restaurantDetail)
        }
    }


}