package com.bignerdranch.android.restaurantsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.Yelp.Restaurant
import com.bignerdranch.android.restaurantsapp.databinding.FragmentRestaurantsListBinding
import com.bignerdranch.android.restaurantsapp.databinding.ListItemViewBinding
import com.bignerdranch.android.restaurantsapp.viewmodel.RestaurantViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.RestaurantsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


private const val API_KEY = "-U1bfntUuMZ-vKhJTY-Se2qgA85B5bl22meeTZcL0QP53prEaE3lgO2RW5Rg9cbrl19_pHdhrDkvdmDzm37Nk0U1atjEGWVHXn_1Uk7t1mI5lqb36b_c7ro0gZImXnYx"

class RestaurantsListFragment : Fragment() {

    val args by navArgs<RestaurantsListFragmentArgs>()

    private val restaurantsViewModel: RestaurantsViewModel by lazy {
        ViewModelProvider(this).get(RestaurantsViewModel::class.java)
    }
    private var adapter = RestaurantAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRestaurantsListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_restaurants_list,container,false)
        restaurantsViewModel.getRestaurants("Bearer $API_KEY","all", args.latitude, args.longitude).observe(viewLifecycleOwner,
            Observer{
                adapter.setData(it)
            }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RestaurantsListFragment.adapter
        }
        return binding.root
    }

    private class RestaurantHolder(private val binding: ListItemViewBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(restaurant: Restaurant){
            binding.viewModel = RestaurantViewModel(restaurant)
            Glide.with(binding.imageView).load(restaurant.imageUrl).apply(
                RequestOptions().transforms(
                CenterCrop(), RoundedCorners(20)
            )).into(binding.imageView)

        }
    }

    private inner class RestaurantAdapter(private var restaurant:List<Restaurant>):RecyclerView.Adapter<RestaurantHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
            val binding: ListItemViewBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.list_item_view,parent,false)

            return RestaurantHolder(
                binding
            )
        }

        override fun getItemCount(): Int = restaurant.size

        override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
            val restaurants = restaurant[position]
            holder.bind(restaurants)
        }

         fun setData(restaurant: List<Restaurant>){
            this.restaurant = restaurant
            notifyDataSetChanged()
        }

    }

}