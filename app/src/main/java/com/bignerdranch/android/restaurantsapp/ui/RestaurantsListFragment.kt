package com.bignerdranch.android.restaurantsapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.databinding.FragmentRestaurantsListBinding
import com.bignerdranch.android.restaurantsapp.databinding.RestaurantListItemBinding
import com.bignerdranch.android.restaurantsapp.viewmodel.restaurant.RestaurantViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.restaurant.RestaurantsViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.WeatherViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.WeathersViewModel
import com.bignerdranch.android.restaurantsapp.weather.Weather
import com.bignerdranch.android.restaurantsapp.yelp.Restaurant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


const val RESTAURANT_API_KEY = "-U1bfntUuMZ-vKhJTY-Se2qgA85B5bl22meeTZcL0QP53prEaE3lgO2RW5Rg9cbrl19_pHdhrDkvdmDzm37Nk0U1atjEGWVHXn_1Uk7t1mI5lqb36b_c7ro0gZImXnYx"
const val WEATHER_API_KEY = "803bb8a53fdd48baaa0113628201712"

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
        if(isNetworkAvailable()){
            getPlaces(args.places)
            Log.d("TEST","********************************************************* there is an Internet")
            print("********************************************************* there is an Internet")

        }
        else{
            restaurantsViewModel.getRestaurant().observe(viewLifecycleOwner,
                    Observer{
                        adapter.setData(it)
                    }
            )
            Log.d("TEST","********************************************************* no Internet")
            print("********************************************************* no Internet")
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RestaurantsListFragment.adapter
        }

        binding.locationSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getPlaces("${args.places} $query" )
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    getPlaces("${args.places} $newText")
                }
                return true
            }
        })

        return binding.root
    }

    inner private class RestaurantHolder(private val binding: RestaurantListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(restaurant: Restaurant, weather:Weather){
            binding.restaurantViewModel = RestaurantViewModel(restaurant)
            binding.weatherViewModel = WeatherViewModel(weather)
            Glide.with(binding.imageView).load(restaurant.imageUrl).apply(
                RequestOptions().transforms(
                CenterCrop(), RoundedCorners(20)
            )).into(binding.imageView)
            Glide.with(binding.icConditionTextView).load("https://"+weather.condition.icon).apply(
                    RequestOptions().transforms(
                            CenterCrop(), RoundedCorners(20)
                    )).into(binding.icConditionTextView)

        }
    }

    private inner class RestaurantAdapter(private var restaurant:List<Restaurant>):RecyclerView.Adapter<RestaurantHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
            val binding: RestaurantListItemBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.restaurant_list_item,parent,false)

            return RestaurantHolder(
                binding
            )
        }

        override fun getItemCount(): Int = restaurant.size

        override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
            val restaurants = restaurant[position]
            val latLng = "${restaurants.coordinates.latitude}, ${restaurants.coordinates.longitude}"
            val weather = weathersViewModel.getWeather(WEATHER_API_KEY,latLng).value
            if (weather != null) {
                holder.bind(restaurants, weather)
            }
            holder.itemView.setOnClickListener {
                val action = RestaurantsListFragmentDirections.actionRestaurantsAppToRestaurantsDetailFragment(restaurants.restaurantID, latLng)
                findNavController().navigate(action)
            }
        }

         fun setData(restaurant: List<Restaurant>){
            this.restaurant = restaurant
            notifyDataSetChanged()
        }

    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun getPlaces(places: String){
        restaurantsViewModel.getRestaurants("Bearer $RESTAURANT_API_KEY",places, args.latitude, args.longitude).observe(viewLifecycleOwner,
                Observer{
                    if(it.isNotEmpty()){
                        adapter.setData(it)
                        Log.d("TEST","********************************************************* $it")
                    }
                })
    }
}
