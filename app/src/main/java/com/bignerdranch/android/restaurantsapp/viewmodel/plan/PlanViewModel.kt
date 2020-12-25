package com.bignerdranch.android.restaurantsapp.viewmodel.plan

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.restaurantsapp.data.Plan
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import com.bignerdranch.android.restaurantsapp.repository.PlanRepository
import kotlinx.coroutines.launch

class PlanViewModel @ViewModelInject constructor(
    private val planRepository: PlanRepository
): ViewModel() {
    fun getPlan() : LiveData<List<Plan>> = planRepository.getPlan()
    fun getFavPlace(planID: String) : LiveData<List<PlacesDetail>> = planRepository.getFavPlace(planID)
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

    fun addFavPlace(placesDetail: PlacesDetail){
        viewModelScope.launch {
            planRepository.addFavPlace(placesDetail)
        }
    }

    fun deleteFavDetails(placesDetail: PlacesDetail){
        viewModelScope.launch {
            planRepository.deleteFavDetails(placesDetail)
        }
    }


}