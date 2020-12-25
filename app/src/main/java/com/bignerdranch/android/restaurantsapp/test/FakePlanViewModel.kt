package com.bignerdranch.android.restaurantsapp.test

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.restaurantsapp.data.Plan
import kotlinx.coroutines.launch

class FakePlanViewModel @ViewModelInject constructor(
    private val planTest: PlanTest
): ViewModel() {

    val getPlan : LiveData<List<Plan>> = planTest.getPlan()

    private val _plan = MutableLiveData<Event<Resource<Plan>>>()
    val plan: LiveData<Event<Resource<Plan>>> = _plan

    fun addPlan(plan: Plan){
        viewModelScope.launch {
            planTest.addPlan(plan)
        }
    }

    fun deletePlan(plan: Plan){
        viewModelScope.launch {
            viewModelScope.launch {
                planTest.deletePlan(plan)
            }
        }
    }


    fun addPlanTest(planName: String, color: String,
                           date: String, planDescription: String) {
        if(planName.isEmpty() || color.isEmpty() || date.isEmpty() || planDescription.isEmpty()) {
            _plan.postValue(Event(Resource.error("The fields must not be empty", null)))
            return
        }
        val plan = Plan(0,planName, color, date, planDescription)
        addPlan(plan)
        _plan.postValue(Event(Resource.success(plan)))
    }

}
