package com.bignerdranch.android.restaurantsapp.ui.place


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.restaurantsapp.util.CheckNetwork
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.databinding.FragmentPlacesListBinding
import com.bignerdranch.android.restaurantsapp.databinding.PlaceListItemBinding
import com.bignerdranch.android.restaurantsapp.data.Places
import com.bignerdranch.android.restaurantsapp.viewmodel.place.PlacesViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.WeatherViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.WeathersViewModel
import com.bignerdranch.android.restaurantsapp.data.Weather
import com.bignerdranch.android.restaurantsapp.viewmodel.place.PlaceViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesListFragment : Fragment() {

    val args by navArgs<PlacesListFragmentArgs>()

    private val placesViewModel: PlacesViewModel by viewModels()

    private val weathersViewModel: WeathersViewModel by viewModels()

    private var adapter = PlaceAdapter(emptyList())
    private lateinit var binding: FragmentPlacesListBinding
    private lateinit var checkNetwork: CheckNetwork


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_places_list,container,false)

        binding.animationView.visibility = View.GONE
        checkNetwork = CheckNetwork(context)

        if (checkNetwork.isNetworkAvailable()){
            getDataWhenNetworkAvailable()
        }else{
            getDataWhenNoNetwork()
        }

        binding.locationSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
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
            adapter = this@PlacesListFragment.adapter
        }


        return binding.root
    }

    private inner class PlaceHolder(private val binding: PlaceListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(places: Places, weather: Weather){
            binding.placeViewModel = PlaceViewModel(places)
            binding.weatherViewModel = WeatherViewModel(weather)
            Glide.with(binding.imageView).load(places.imageUrl).apply(
                RequestOptions().transforms(
                CenterCrop(), RoundedCorners(20)
            )).into(binding.imageView)
            Glide.with(binding.icConditionTextView).load("https://"+weather.condition.icon).apply(
                    RequestOptions().transforms(
                            CenterCrop(), RoundedCorners(20)
                    )).into(binding.icConditionTextView)
        }
    }

    private inner class PlaceAdapter(private var places:List<Places>):RecyclerView.Adapter<PlaceHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
            val binding: PlaceListItemBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.place_list_item,parent,false)

            return PlaceHolder(
                binding
            )
        }

        override fun getItemCount(): Int = places.size

        override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
            val place = places[position]
            val latLng = "${place.coordinates.latitude}, ${place.coordinates.longitude}"

            weathersViewModel.getWeather(place.placeID).observe(viewLifecycleOwner,
                Observer {
                    if(it != null){
                        holder.bind(place, it)
                    }
                })

            holder.itemView.setOnClickListener {
                val action =
                    PlacesListFragmentDirections.actionRestaurantsAppToRestaurantsDetailFragment(
                        place.placeID,
                        false,
                        latLng
                    )
                findNavController().navigate(action)
            }
        }

         fun setData(restaurant: List<Places>){
            this.places = restaurant
            notifyDataSetChanged()
        }

    }

    private fun getDataWhenNetworkAvailable(){
        getPlaces(args.places)
    }

    private fun getDataWhenNoNetwork(){
        placesViewModel.getPlace().observe(viewLifecycleOwner, Observer{ 
            if(it.isNotEmpty()){
                adapter.setData(it)
            }
                    Log.d("TEST","********************************************************* no Internet")
                })
    }

    private fun getPlaces(places: String){
        binding.animationView.visibility = View.VISIBLE
        placesViewModel.getPlaces(places, args.latitude, args.longitude).observe(viewLifecycleOwner,
                Observer{places->
                    if(places.isNotEmpty()){
                        adapter.setData(places)
                        binding.animationView.visibility = View.GONE
                        Log.d("TEST","********************************************************* $places")
                    }
                })
    }

}