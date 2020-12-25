package com.bignerdranch.android.restaurantsapp.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.data.PlaceReview
import com.bignerdranch.android.restaurantsapp.databinding.FragmentPlaceReviewBinding
import com.bignerdranch.android.restaurantsapp.databinding.ReviewListItemBinding
import com.bignerdranch.android.restaurantsapp.util.CheckNetwork
import com.bignerdranch.android.restaurantsapp.viewmodel.place.PlaceReviewsViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.place.PlacesViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceReviewFragment : BottomSheetDialogFragment() {

    val args by navArgs<PlaceReviewFragmentArgs>()

    private val placesViewModel: PlacesViewModel by viewModels()

    private var adapter = ReviewAdapter(emptyList())
    private lateinit var checkNetwork: CheckNetwork

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentPlaceReviewBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_place_review, container, false)
        checkNetwork = CheckNetwork(context)
        if (checkNetwork.isNetworkAvailable()){
            placesViewModel.placeReview(args.placeID).observe(viewLifecycleOwner, Observer {
                adapter.setData(it.reviews)
                binding.reviewCount.text = "${getString(R.string.reviews)} :"+it.total
            })
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PlaceReviewFragment.adapter
        }
        return binding.root
    }

    private inner class ReviewHolder(private val binding: ReviewListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(placeReview: PlaceReview){
            binding.reviewViewModel = PlaceReviewsViewModel(placeReview)
            Glide.with(binding.imageView).load(placeReview.user.UserImage).apply(
                RequestOptions().transforms(
                    CenterCrop(), RoundedCorners(5)
                )).into(binding.imageView)
        }
    }

    private inner class ReviewAdapter(private var reviews:List<PlaceReview>): RecyclerView.Adapter<ReviewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
            val binding: ReviewListItemBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.review_list_item,parent,false)

            return ReviewHolder(
                binding
            )
        }

        override fun getItemCount(): Int = reviews.size

        override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
            val review = reviews[position]
            holder.bind(review)
        }

        fun setData(review: List<PlaceReview>){
            this.reviews = review
            notifyDataSetChanged()
        }

    }


}