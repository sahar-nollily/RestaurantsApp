package com.bignerdranch.android.restaurantsapp.viewmodel.plan

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.restaurantsapp.data.Plan
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import com.bignerdranch.android.restaurantsapp.repository.IPlanRepository
import com.bignerdranch.android.restaurantsapp.test.Event
import com.bignerdranch.android.restaurantsapp.test.Resource
import kotlinx.coroutines.launch

class PlanViewModel @ViewModelInject constructor(
    private val planRepository: IPlanRepository
): ViewModel() {
    private val _plan = MutableLiveData<Event<Resource<Plan>>>()
    val getPlan: LiveData<Event<Resource<Plan>>> = _plan
    fun getPlan() : LiveData<List<Plan>> = planRepository.getPlan()
    fun getFavPlace(planID: String) : LiveData<List<PlacesDetail>> = planRepository.getFavPlace(planID)
    fun getFavDetails(restaurantID: String) = planRepository.getFavDetails(restaurantID)


    fun addPlan(planName: String, color: String,
                date: String, planDescription: String){
        if(planName.isEmpty() || color.isEmpty() || date.isEmpty() || planDescription.isEmpty()) {
            _plan.postValue(Event(Resource.error("The fields must not be empty", null)))
            return
        }
        val plan = Plan(0,planName, color, date, planDescription)
        _plan.postValue(Event(Resource.success(plan)))
        viewModelScope.launch {
            planRepository.addPlan(plan)
            _plan.postValue(Event(Resource.success(plan)))
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

    fun setTimeNotification(favID: String, time: String){
        viewModelScope.launch {
            planRepository.setTimeNotification(favID,time)
        }
    }

    fun deleteFavDetails(placesDetail: PlacesDetail){
        viewModelScope.launch {
            planRepository.deleteFavDetails(placesDetail)
        }
    }
}