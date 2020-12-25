package com.bignerdranch.android.restaurantsapp.ui


import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.restaurantsapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class MapsFragment : Fragment(),OnMapReadyCallback {
    private val args by navArgs<MapsFragmentArgs>()
    private var mGoogleMap: GoogleMap? = null
    private var latitude : Double = 0.0
    private var longitude: Double= 0.0
    private lateinit var autoComplatePlacesEditText: EditText
    private lateinit var chooseLocationButton: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps,container,false)
        latitude = args.latitude.toDouble()
        longitude = args.longitude.toDouble()
        autoComplatePlacesEditText = view.findViewById(R.id.search_edit_text)
        chooseLocationButton = view.findViewById(R.id.choose_location_Button)
        chooseLocationButton.setOnClickListener {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            if (sharedPref != null) {
                with (sharedPref.edit()) {
                    putString(getString(R.string.latitude), latitude.toString())
                    putString(getString(R.string.longitude), longitude.toString())
                    apply()
                }
            }
            val action = MapsFragmentDirections.actionMapsFragmentToMainFragment()
            findNavController().navigate(action)
        }
        Places.initialize(requireContext(),getString(R.string.google_maps_key))
        autoComplatePlacesEditText.isFocusable = false
        autoComplatePlacesEditText.setOnClickListener {
            val fieldList : List<Place.Field> = listOf(Place.Field.ADDRESS,
            Place.Field.LAT_LNG,Place.Field.NAME)
            val intent = Autocomplete
                .IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList)
                .build(requireContext())
            startActivityForResult(intent,100)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        val currentLocation = LatLng(latitude,longitude)
        p0!!.addMarker(MarkerOptions().position(currentLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)))
        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20F))
        mGoogleMap = p0

        mGoogleMap?.setOnMapClickListener{
            mGoogleMap!!.clear()
            latitude = it.latitude
            longitude = it.longitude
            autoComplatePlacesEditText.setText("${it.latitude}, ${it.longitude}")
            mGoogleMap!!.addMarker(MarkerOptions().position(it).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)))
            mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(it))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){
            val place = data?.let { Autocomplete.getPlaceFromIntent(it) }
            autoComplatePlacesEditText.setText(place?.address)
            val latLng = place?.latLng
            latitude = latLng!!.latitude
            longitude = latLng.longitude
            mGoogleMap!!.clear()
            mGoogleMap!!.addMarker(MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)))
            mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            val status = data?.let { Autocomplete.getStatusFromIntent(it) }
            Toast.makeText(requireContext(),status?.statusMessage,Toast.LENGTH_LONG).show()
            Log.d("test", "***************************************${status?.statusMessage}")
        }
    }
}