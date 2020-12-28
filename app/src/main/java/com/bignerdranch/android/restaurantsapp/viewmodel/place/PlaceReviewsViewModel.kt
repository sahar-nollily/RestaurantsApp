package com.bignerdranch.android.restaurantsapp.viewmodel.place

import androidx.databinding.BaseObservable
import com.bignerdranch.android.restaurantsapp.data.PlaceReview
import java.text.SimpleDateFormat


class PlaceReviewsViewModel (
    placeReview: PlaceReview
): BaseObservable(){
    val name: String = placeReview.user.name
    val rating: String = placeReview.rating
    val text: String = placeReview.text
    val mSDF =  SimpleDateFormat("MMMM d, yyyy");
    val formatter = SimpleDateFormat("yyyy-mm-dd hh:mm");
    val reviewTime: String = mSDF.format(formatter.parse(placeReview.reviewTime))
}