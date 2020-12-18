package com.bignerdranch.android.restaurantsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.bignerdranch.android.restaurantsapp.viewmodel.restaurant.RestaurantViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.restaurant.RestaurantsViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.WeathersViewModel
import com.bignerdranch.android.restaurantsapp.weather.Weather
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


private const val RESTAURANT_API_KEY = "-U1bfntUuMZ-vKhJTY-Se2qgA85B5bl22meeTZcL0QP53prEaE3lgO2RW5Rg9cbrl19_pHdhrDkvdmDzm37Nk0U1atjEGWVHXn_1Uk7t1mI5lqb36b_c7ro0gZImXnYx"
private const val WEATHER_API_KEY = "803bb8a53fdd48baaa0113628201712"

class RestaurantsListFragment : Fragment() {

    val args by navArgs<RestaurantsListFragmentArgs>()

    private val restaurantsViewModel: RestaurantsViewModel by lazy {
        ViewModelProvider(this).get(RestaurantsViewModel::class.java)
    }

    private val weathersViewModel: WeathersViewModel by lazy {
        ViewModelProvider(this).get(WeathersViewModel::class.java)
    }
    private var adapter = RestaurantAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRestaurantsListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_restaurants_list,container,false)
        restaurantsViewModel.getRestaurants("Bearer $RESTAURANT_API_KEY","all", args.latitude, args.longitude).observe(viewLifecycleOwner,
            Observer{
                adapter.setData(it)
            }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RestaurantsListFragment.adapter
        }

        weathersViewModel.getForecast(WEATHER_API_KEY,"${args.latitude}, ${args.longitude}","7",1).observe(viewLifecycleOwner, Observer {
            Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
            Log.d("TEST","*********************************************************"+it.toString())

        })
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