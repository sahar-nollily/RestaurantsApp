package com.bignerdranch.android.restaurantsapp.database.plan

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.restaurantsapp.network.restaurants.RestaurantDetail

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
    suspend fun addFavPlace(restaurantDetail: RestaurantDetail)

    @Query("SELECT * FROM RestaurantDetail WHERE planID =:planID ")
    fun getFavPlace(planID: String): LiveData<List<RestaurantDetail>>

    @Query("SELECT * FROM RestaurantDetail WHERE favID =:favID ")
    fun getFavDetails(favID: String): LiveData<RestaurantDetail>

}