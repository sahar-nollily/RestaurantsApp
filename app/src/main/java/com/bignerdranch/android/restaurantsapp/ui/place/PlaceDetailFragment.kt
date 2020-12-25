package com.bignerdranch.android.restaurantsapp.ui.place

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bignerdranch.android.restaurantsapp.util.CheckNetwork
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.databinding.DialogAddNoteBinding
import com.bignerdranch.android.restaurantsapp.databinding.FragmentPlaceDetailBinding
import com.bignerdranch.android.restaurantsapp.databinding.ViewPagerItemBinding
import com.bignerdranch.android.restaurantsapp.databinding.WeatherListItemBinding
import com.bignerdranch.android.restaurantsapp.network.places.PlacesDetail
import com.bignerdranch.android.restaurantsapp.viewmodel.place.PlaceDetailViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.place.PlacesViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.ForecastWeatherViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.WeathersViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.plan.PlanViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.util.*

class PlaceDetailFragment : Fragment(), OnMapReadyCallback {

    val args by navArgs<PlaceDetailFragmentArgs>()

    private val placesViewModel: PlacesViewModel by lazy {
        ViewModelProvider(this).get(PlacesViewModel::class.java)
    }

    private val weathersViewModel: WeathersViewModel by lazy {
        ViewModelProvider(this).get(WeathersViewModel::class.java)
    }

    private val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }

    private lateinit var placesDetail: PlacesDetail
    private lateinit var weatherBinding:WeatherListItemBinding
    private lateinit var checkNetwork: CheckNetwork


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentPlaceDetailBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_place_detail, container, false)

        checkNetwork = CheckNetwork(context)

        if(args.isSave || checkNetwork.isNetworkAvailable()){
            if(args.isSave){
                planViewModel.getFavDetails(args.restaurantId).observe(viewLifecycleOwner, Observer {
                    placesDetail = it
                    binding.placeDetailViewModel = PlaceDetailViewModel(it)
                    binding.viewPager.adapter= ImageAdapter(it.photos)
                    binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                    binding.indicator.setViewPager(binding.viewPager)
                })
                binding.favPlace.visibility = View.GONE
            }
            else{
                placesViewModel.placeDetails("Bearer ${getString(R.string.RESTAURANT_API_KEY)}",args.restaurantId).observe(viewLifecycleOwner,
                    Observer {
                        placesDetail = it
                        binding.placeDetailViewModel = PlaceDetailViewModel(it)
                        binding.viewPager.adapter= ImageAdapter(it.photos)
                        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        binding.indicator.setViewPager(binding.viewPager)
                    })
                binding.favPlace.visibility = View.VISIBLE
                binding.favPlace.setOnClickListener {
                    addNote()
                }
            }

            if(checkNetwork.isNetworkAvailable()){
                weathersViewModel.getForecast(getString(R.string.WEATHER_API_KEY),args.latLang,"2",0).observe(viewLifecycleOwner, Observer {
                    val getCurrentTime: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                    val simpleDateFormat = SimpleDateFormat("HH:mm")
                    val currentTime = simpleDateFormat.parse(getCurrentTime)!!

                    for(i in 0 .. 23){
                        val forecastTime = simpleDateFormat.parse(it[i].time.split(" ")[1])
                        if(currentTime == forecastTime || currentTime < forecastTime){
                            weatherBinding= DataBindingUtil.inflate(LayoutInflater.from(context),
                                R.layout.weather_list_item,container,false)
                            weatherBinding.forecastWeatherViewModel = ForecastWeatherViewModel(it[i])
                            Glide.with(weatherBinding.icWeather).load("https://"+it[i].condition.icon).apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )).into(weatherBinding.icWeather)
                            binding.weatherLinearLayout.addView(weatherBinding.root)
                        }
                    }

                })

                weathersViewModel.getForecast(getString(R.string.WEATHER_API_KEY),args.latLang,"2",1).observe(viewLifecycleOwner, Observer {
                    for(i in  0 .. 6){
                        weatherBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                            R.layout.weather_list_item,container,false)
                        weatherBinding.forecastWeatherViewModel = ForecastWeatherViewModel(it[i])
                        Glide.with(weatherBinding.icWeather).load("https://"+it[i].condition.icon).apply(
                            RequestOptions().transforms(
                                CenterCrop(), RoundedCorners(20)
                            )).into(weatherBinding.icWeather)
                        binding.weatherLinearLayout.addView(weatherBinding.root)
                    }

                })
            }

            binding.restaurantShareTextView.setOnClickListener {
                ShareCompat.IntentBuilder
                    .from(requireActivity())
                    .setType("text/plain")
                    .setChooserTitle("Share this text with: ")
                    .setText("Look to this beautiful place ${placesDetail.url}")
                    .startChooser();
            }

            binding.restaurantPhoneTextView.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:"+placesDetail.phone)
                startActivity(intent)
            }

        }


        return binding.root
    }

    private inner class ImageHolder(private val binding: ViewPagerItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: String){
            if(checkNetwork.isNetworkAvailable()){
                Glide.with(binding.imageView).load(image).apply(
                    RequestOptions().transforms(
                            CenterCrop(), RoundedCorners(1)
                    )).into(binding.imageView)
            }
        }
    }

    private inner class ImageAdapter(private var images: List<String>): RecyclerView.Adapter<ImageHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
            val binding: ViewPagerItemBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.view_pager_item,parent,false)

            return ImageHolder(binding)
        }

        override fun getItemCount()= images.size

        override fun onBindViewHolder(holder: ImageHolder, position: Int) {
            val image = images[position]
            holder.bind(image)
        }

    }

    private fun addNote(){
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val binding: DialogAddNoteBinding = DataBindingUtil.inflate(layoutInflater,R.layout.dialog_add_note,null,false)
        val dialogView = binding.root
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        binding.addNoteButton.setOnClickListener {
            val note = binding.noteEditText.text.toString()
            val action = PlaceDetailFragmentDirections.actionRestaurantsDetailFragmentToUserPlansFragment("add",args.restaurantId,note)
            findNavController().navigate(action)
            alertDialog.dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_direction) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }


    override fun onMapReady(p0: GoogleMap?) {
        p0?.setOnMapClickListener{
            if(::placesDetail.isInitialized){
                val restaurantLocation = LatLng(placesDetail.coordinates.latitude,placesDetail.coordinates.longitude)
                p0.addMarker(MarkerOptions().position(restaurantLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)).title(placesDetail.name))
                p0.animateCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 14F))
            }else{
                Toast.makeText(requireContext(),getString(R.string.toast_no_Internet),Toast.LENGTH_LONG).show()
            }
        }
    }

}