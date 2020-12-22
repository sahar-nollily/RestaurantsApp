package com.bignerdranch.android.restaurantsapp.database.plan

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlanDao{

    @Query("SELECT * FROM `Plan`")
    fun getPlan(): LiveData<List<Plan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlan(plan: Plan)

    @Update
    suspend fun updatePlan(plan: Plan)

    @Delete
    suspend fun deletePlan(plan: Plan)

}