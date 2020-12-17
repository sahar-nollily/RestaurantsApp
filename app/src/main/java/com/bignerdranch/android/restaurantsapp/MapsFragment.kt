package com.bignerdranch.android.restaurantsapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsFragment : Fragment(),OnMapReadyCallback {
    var mGoogleMap: GoogleMap? = null
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var  latuitude : Double = 0.0
    var longitude: Double= 0.0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        // Check permission
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED ){
            getLocation()
        }else{
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),44)
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        mGoogleMap = p0

        mGoogleMap?.setOnMapClickListener{
            mGoogleMap!!.clear()
            latuitude = it.latitude
            longitude = it.longitude
            mGoogleMap!!.addMarker(MarkerOptions().position(it).title("Marker in Sydney"))
            mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(it))
        }

        mGoogleMap!!.setOnMarkerClickListener {
            val action = MapsFragmentDirections.actionMapsFragmentToRestaurantsApp(latuitude.toString(),longitude.toString())
            findNavController().navigate(action)
            mGoogleMap!!.clear()
            return@setOnMarkerClickListener true

        }

    }

    @SuppressLint("MissingPermission")
    fun getLocation(){
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {task ->
            val location = task.getResult()
            if(location != null){
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val address = geocoder.getFromLocation(
                    location.latitude,location.longitude,1
                )
                latuitude = address[0].latitude
                longitude = address[0].longitude
            }
            val currentLocation = LatLng(latuitude,longitude)
            mGoogleMap!!.addMarker(MarkerOptions().position(currentLocation))
            mGoogleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15F))

        }
    }
}