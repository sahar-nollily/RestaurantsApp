package com.bignerdranch.android.restaurantsapp.database.plan

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Plan(
    @PrimaryKey(autoGenerate = true) val planID: Int=0,
    val planName: String="",
    val color: String= "cyan",
    val date: String="",
    val planDescription: String=""
):Parcelable