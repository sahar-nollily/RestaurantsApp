package com.bignerdranch.android.restaurantsapp.ui.restaurant

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bignerdranch.android.restaurantsapp.CheckNetwork
import com.bignerdranch.android.restaurantsapp.ConnectivityLiveData
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.databinding.DialogAddNoteBinding
import com.bignerdranch.android.restaurantsapp.databinding.FragmentRestaurantsDetailBinding
import com.bignerdranch.android.restaurantsapp.databinding.ViewPagerItemBinding
import com.bignerdranch.android.restaurantsapp.databinding.WeatherListItemBinding
import com.bignerdranch.android.restaurantsapp.viewmodel.restaurant.RestaurantDetailViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.restaurant.RestaurantsViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.ForecastWeatherViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.WeathersViewModel
import com.bignerdranch.android.restaurantsapp.network.restaurants.RestaurantDetail
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

class RestaurantsDetailFragment : Fragment(), OnMapReadyCallback {

    val args by navArgs<RestaurantsDetailFragmentArgs>()

    private val restaurantsViewModel: RestaurantsViewModel by lazy {
        ViewModelProvider(this).get(RestaurantsViewModel::class.java)
    }

    private val weathersViewModel: WeathersViewModel by lazy {
        ViewModelProvider(this).get(WeathersViewModel::class.java)
    }

    private val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }

    private lateinit var restaurantDetail: RestaurantDetail
    private lateinit var weatherBinding:WeatherListItemBinding
    private lateinit var checkNetwork: CheckNetwork


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentRestaurantsDetailBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_restaurants_detail, container, false)

        checkNetwork = CheckNetwork(context)

        if(args.isSave || checkNetwork.isNetworkAvailable()){
            if(args.isSave){
                planViewModel.getFavDetails(args.restaurantId).observe(viewLifecycleOwner, Observer {
                restaurantDetail = it
                    binding.restaurantDetailViewModel = RestaurantDetailViewModel(it)
                    binding.viewPager.adapter= ImageAdapter(it.photos)
                    binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                    binding.indicator.setViewPager(binding.viewPager)
                })
            }
            else{
                restaurantsViewModel.restaurantDetails("Bearer $RESTAURANT_API_KEY",args.restaurantId).observe(viewLifecycleOwner,
                    Observer {
                        restaurantDetail = it
                        binding.restaurantDetailViewModel = RestaurantDetailViewModel(it)
                        binding.viewPager.adapter= ImageAdapter(it.photos)
                        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        binding.indicator.setViewPager(binding.viewPager)
                    })
            }

            if(checkNetwork.isNetworkAvailable()){
                weathersViewModel.getForecast(WEATHER_API_KEY,args.latLang,"2",0).observe(viewLifecycleOwner, Observer {
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

                weathersViewModel.getForecast(WEATHER_API_KEY,args.latLang,"2",1).observe(viewLifecycleOwner, Observer {
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

                binding.favPlace.setOnClickListener {
                    addNote()
                }
            }

            binding.restaurantShareTextView.setOnClickListener {
                ShareCompat.IntentBuilder
                    .from(requireActivity())
                    .setType("text/plain")
                    .setChooserTitle("Share this text with: ")
                    .setText("Look to this beautiful place ${restaurantDetail.url}")
                    .startChooser();
            }

            binding.restaurantPhoneTextView.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:"+restaurantDetail.phone)
                startActivity(intent)
            }

        }


        return binding.root
    }

    private inner class ImageHolder(private val binding: ViewPagerItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: String){
            if(checkNetwork.isNetworkAvailable()){
                Glide.with(binding.imageView).load(image).apply(
                    RequestOptions()).into(binding.imageView)
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
            val action = RestaurantsDetailFragmentDirections.actionRestaurantsDetailFragmentToUserPlansFragment("add",args.restaurantId,note)
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
            val restaurantLocation = LatLng(restaurantDetail.coordinates.latitude,restaurantDetail.coordinates.longitude)
            p0.addMarker(MarkerOptions().position(restaurantLocation).title(restaurantDetail.name))
            p0.animateCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 14F))
        }
    }

}