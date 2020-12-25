package com.bignerdranch.android.restaurantsapp.database.plan

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.restaurantsapp.data.Plan
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavPlace(placesDetail: PlacesDetail)

    @Query("SELECT * FROM PlacesDetail WHERE planID =:planID ")
    fun getFavPlace(planID: String): LiveData<List<PlacesDetail>>

    @Query("SELECT * FROM PlacesDetail WHERE placeID =:placeID ")
    fun getFavDetails(placeID: String): LiveData<PlacesDetail>

    @Delete
    suspend fun deleteFavDetails(placesDetail: PlacesDetail)

}