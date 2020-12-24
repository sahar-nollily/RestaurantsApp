package com.bignerdranch.android.restaurantsapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.databinding.FragmentMainBinding
import com.bignerdranch.android.restaurantsapp.databinding.MainListItemBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class MainFragment : Fragment() {
    private lateinit var binding : FragmentMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var latuitude : Double = 0.0
    var longitude: Double= 0.0
    private val places = listOf("all","restaurant","museum","shopping","coffeeshop","park")
    private var adapter = PlacesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        latuitude = sharedPref?.getString(getString(R.string.latitude),"0.0")?.toDouble() ?: 0.0
        longitude = sharedPref?.getString(getString(R.string.longitude),"0.0")?.toDouble() ?: 0.0
        if(latuitude!= 0.0 && longitude!= 0.0){
            getGeocoder(latuitude,longitude)
        }
        else{
            getLocation()
        }
        binding.setLocationEditTextClickListener {
            val action = MainFragmentDirections.actionMainFragmentToMapsFragment(latuitude.toFloat(),longitude.toFloat())
            findNavController().navigate(action)
        }

        binding.showNote.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToUserPlansFragment()
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainFragment.adapter
        }

        return binding.root
    }

    private inner class PlacesHolder(private val binding: MainListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(places: String){
            val drawable = resources.getIdentifier(places, "drawable", context?.packageName)
            binding.imageView.background = ContextCompat.getDrawable(requireContext(),drawable)
            val textString = resources.getIdentifier(places, "string", context?.packageName)
            binding.textView.text = getString(textString)

        }
    }

    private inner class PlacesAdapter(): RecyclerView.Adapter<PlacesHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesHolder {
            val binding: MainListItemBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.main_list_item,parent,false)

            return PlacesHolder(binding)
        }

        override fun getItemCount(): Int = places.size

        override fun onBindViewHolder(holder: PlacesHolder, position: Int) {
            val places = places[position]
            holder.bind(places)
            holder.itemView.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToRestaurantsApp(latuitude.toString(), longitude.toString(), places)
                findNavController().navigate(action)
            }
        }

    }

    private fun getLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED ){
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {task ->
                val location = task.getResult()
                if(location != null){
                    val latitude = location.latitude
                    val longitude = location.longitude
                    getGeocoder(latitude,longitude)
                }
            }
        }else{
            ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),44)
        }
    }

    private fun getGeocoder(latitude: Double, longitude: Double){
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geocoder.getFromLocation(
                latitude,longitude,1
        )
        this.latuitude = address[0].latitude
        this.longitude = address[0].longitude
        binding.locationEditText.text = "${address[0].countryName}, ${address[0].subAdminArea}, ${address[0].subLocality}"
    }
}