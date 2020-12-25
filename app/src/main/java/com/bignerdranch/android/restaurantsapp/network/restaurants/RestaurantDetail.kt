package com.bignerdranch.android.restaurantsapp.network.restaurants

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bignerdranch.android.restaurantsapp.database.plan.Plan
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys = [ForeignKey(
        entity = Plan::class,
        parentColumns = arrayOf("planID"),
        childColumns = arrayOf("planID"),
        onDelete = ForeignKey.CASCADE)])
data class RestaurantDetail(
    @PrimaryKey @SerializedName("id") var restaurantID: String,
    var planID: Int= 0,
    val name : String= "",
    val is_closed: Boolean= false,
    val location: RestaurantLocation,
    val phone: String= "",
    val url: String= "",
    val coordinates: PlacesLocation,
    val categories: List<YelpCategory>,
    var note: String= "",
    @SerializedName("image_url") val imageUrl: String= "",
    val photos: List<String>){
    fun isClosed() :String{
        return if(is_closed){
            "Closed"
        }else{
            "Open"
        }
    }
}

data class RestaurantLocation(
    val city: String = "",
    val address1: String = ""
)