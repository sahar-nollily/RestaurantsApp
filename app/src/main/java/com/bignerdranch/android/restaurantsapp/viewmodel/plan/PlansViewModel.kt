package com.bignerdranch.android.restaurantsapp.viewmodel.plan

import androidx.databinding.BaseObservable
import com.bignerdranch.android.restaurantsapp.data.Plan

class PlansViewModel(
    plan: Plan
    ) : BaseObservable() {
        val name: String = plan.planName
        val date: String = plan.date
        val planDescription: String = plan.planDescription

    }