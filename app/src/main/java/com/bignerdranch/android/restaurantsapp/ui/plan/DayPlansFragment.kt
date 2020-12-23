package com.bignerdranch.android.restaurantsapp.ui.plan

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.restaurantsapp.CheckNetwork
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.database.plan.Plan
import com.bignerdranch.android.restaurantsapp.databinding.FragmentDayPlansBinding
import com.bignerdranch.android.restaurantsapp.databinding.FragmentUserPlansBinding
import com.bignerdranch.android.restaurantsapp.databinding.UserPlansItemBinding
import com.bignerdranch.android.restaurantsapp.network.restaurants.RestaurantDetail
import com.bignerdranch.android.restaurantsapp.ui.restaurant.RESTAURANT_API_KEY
import com.bignerdranch.android.restaurantsapp.viewmodel.plan.PlanViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.plan.PlansViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.restaurant.RestaurantDetailViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.restaurant.RestaurantsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class DayPlansFragment : Fragment() {

    val args by navArgs<DayPlansFragmentArgs>()

    private val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }

    private var adapter = PlanAdapter(emptyList())
    private lateinit var checkNetwork: CheckNetwork

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentUserPlansBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_plans, container,false)
        checkNetwork = CheckNetwork(context)
        binding.textView.text = args.plan.planName
        binding.planDescriptionTextView.text = args.plan.planDescription
        planViewModel.getFavPlace(args.plan.planID.toString()).observe(viewLifecycleOwner, Observer {
            adapter.setDate(it)
            if(it.isEmpty()){
                binding.addPlanTextView.visibility = View.VISIBLE
                binding.addPlanButton.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                binding.planDescriptionTextView.visibility = View.VISIBLE

            }else{
                binding.addPlanTextView.visibility = View.GONE
                binding.addPlanButton.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.planDescriptionTextView.visibility = View.VISIBLE
            }
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@DayPlansFragment.adapter
        }

        return binding.root
    }

    private inner class PlanHolder(private val binding: FragmentDayPlansBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(restaurantDetail: RestaurantDetail){
            binding.restaurantDetailViewModel = RestaurantDetailViewModel(restaurantDetail)
            if(checkNetwork.isNetworkAvailable()){
                Glide.with(binding.imageView).load(restaurantDetail.imageUrl).apply(
                    RequestOptions().transforms(
                        CenterCrop(), RoundedCorners(20)
                    )).into(binding.imageView)
            }
        }
    }

    private inner class PlanAdapter(private var plans: List<RestaurantDetail>): RecyclerView.Adapter<PlanHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanHolder {
            val binding: FragmentDayPlansBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.fragment_day_plans,parent,false)
            return PlanHolder(binding)
        }

        override fun getItemCount()= plans.size
        override fun onBindViewHolder(holder: PlanHolder, position: Int) {
            val plan = plans[position]
            val latLng = "${plan.coordinates.latitude}, ${plan.coordinates.longitude}"
            holder.bind(plan)

            holder.itemView.setOnClickListener {
                val action = DayPlansFragmentDirections.actionDayPlansFragmentToRestaurantsDetailFragment(plan.favID,true,latLng)
                findNavController().navigate(action)
            }


        }

        fun setDate(plans: List<RestaurantDetail>){
            this.plans = plans
            notifyDataSetChanged()
        }

    }

}