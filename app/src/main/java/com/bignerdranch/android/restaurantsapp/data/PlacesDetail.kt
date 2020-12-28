package com.bignerdranch.android.restaurantsapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys = [ForeignKey(
    entity = Plan::class,
    parentColumns = arrayOf("planID"),
    childColumns = arrayOf("planID"),
    onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["planID", "placeID"], unique = true)]
)
data class PlacesDetail(
    @PrimaryKey(autoGenerate = true) val favID: Int = 0,
    @SerializedName("id") var placeID: String,
    var planID: Int= 0,
    val name : String= "",
    val is_closed: Boolean= false,
    val location: RestaurantLocation,
    val phone: String= "",
    val url: String= "",
    val coordinates: PlacesLocation,
    val categories: List<Category>,
    var note: String= "",
    var time: String= "no",
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