package com.bignerdranch.android.restaurantsapp.ui.restaurant

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
import com.bignerdranch.android.restaurantsapp.util.CheckNetwork
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.databinding.FragmentRestaurantsListBinding
import com.bignerdranch.android.restaurantsapp.databinding.RestaurantListItemBinding
import com.bignerdranch.android.restaurantsapp.viewmodel.restaurant.RestaurantViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.restaurant.RestaurantsViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.WeatherViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.WeathersViewModel
import com.bignerdranch.android.restaurantsapp.network.weather.Weather
import com.bignerdranch.android.restaurantsapp.network.restaurants.Restaurant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class RestaurantsListFragment : Fragment() {

    val args by navArgs<RestaurantsListFragmentArgs>()

    private val restaurantsViewModel: RestaurantsViewModel by lazy {
        ViewModelProvider(this).get(RestaurantsViewModel::class.java)
    }

    private val weathersViewModel: WeathersViewModel by lazy {
        ViewModelProvider(this).get(WeathersViewModel::class.java)
    }

    private var adapter = RestaurantAdapter(emptyList())
    private lateinit var binding: FragmentRestaurantsListBinding
    private lateinit var checkNetwork: CheckNetwork


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_restaurants_list,container,false)

        checkNetwork = CheckNetwork(context)

        if (checkNetwork.isNetworkAvailable()){
            getDataWhenNetworkAvailable()
        }else{
            getDataWhenNoNetwork()
        }

        binding.locationSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (checkNetwork.isNetworkAvailable()){
                    getPlaces("${args.places} $query" )
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (checkNetwork.isNetworkAvailable()){
                    getPlaces("${args.places} $newText" )
                }
                return true
            }
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RestaurantsListFragment.adapter
        }


        return binding.root
    }

    private inner class RestaurantHolder(private val binding: RestaurantListItemBinding): RecyclerView.ViewHolder(binding.root){

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

            val weather = weathersViewModel.getWeather(restaurants.restaurantID).value

            if(weather != null){
                holder.bind(restaurants, weather)
            }

            holder.itemView.setOnClickListener {
                val action =
                    RestaurantsListFragmentDirections.actionRestaurantsAppToRestaurantsDetailFragment(
                        restaurants.restaurantID,
                        false,
                        latLng
                    )
                findNavController().navigate(action)
            }
        }

         fun setData(restaurant: List<Restaurant>){
            this.restaurant = restaurant
            notifyDataSetChanged()
        }

    }

    private fun getDataWhenNetworkAvailable(){
        getPlaces(args.places)
    }

    private fun getDataWhenNoNetwork(){
        restaurantsViewModel.getRestaurant().observe(viewLifecycleOwner, Observer{ adapter.setData(it)
                    Log.d("TEST","********************************************************* no Internet")
                })
    }

    private fun getPlaces(places: String){
        restaurantsViewModel.getRestaurants("Bearer ${getString(R.string.RESTAURANT_API_KEY)}",places, args.latitude, args.longitude).observe(viewLifecycleOwner,
                Observer{restaurants->
                    if(restaurants.isNotEmpty()){
                        adapter.setData(restaurants)
                        Log.d("TEST","********************************************************* $restaurants")
                    }
                })
    }
}