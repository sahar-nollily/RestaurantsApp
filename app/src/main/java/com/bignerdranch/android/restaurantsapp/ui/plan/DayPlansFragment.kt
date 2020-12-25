package com.bignerdranch.android.restaurantsapp.ui.plan

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.restaurantsapp.util.CheckNetwork
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.databinding.FragmentDayPlansBinding
import com.bignerdranch.android.restaurantsapp.databinding.FragmentUserPlansBinding
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import com.bignerdranch.android.restaurantsapp.util.SwipeController
import com.bignerdranch.android.restaurantsapp.viewmodel.plan.PlanViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.place.PlaceDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DayPlansFragment : Fragment() {

    private val args by navArgs<DayPlansFragmentArgs>()

    private val planViewModel: PlanViewModel by viewModels()

    private var adapter = PlaceAdapter(emptyList())
    private lateinit var checkNetwork: CheckNetwork

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentUserPlansBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_plans, container,false)
        checkNetwork = CheckNetwork(context)
        binding.textView.text = args.plan.planName
        binding.planDescriptionTextView.text = args.plan.planDescription
        binding.addPlanTextView.text= getString(R.string.no_places)
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


        val item = object : SwipeController(0, ItemTouchHelper.LEFT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteDate(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@DayPlansFragment.adapter
        }

        return binding.root
    }

    private inner class PlaceHolder(private val binding: FragmentDayPlansBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(placesDetail: PlacesDetail){
            binding.placeDetailViewModel = PlaceDetailViewModel(placesDetail)
            if(checkNetwork.isNetworkAvailable()){
                Glide.with(binding.imageView).load(placesDetail.imageUrl).apply(
                    RequestOptions().transforms(
                        CenterCrop(), RoundedCorners(20)
                    )).into(binding.imageView)
            }
        }
    }

    private inner class PlaceAdapter(private var place: List<PlacesDetail>): RecyclerView.Adapter<PlaceHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
            val binding: FragmentDayPlansBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.fragment_day_plans,parent,false)
            return PlaceHolder(binding)
        }

        override fun getItemCount()= place.size
        override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
            val plan = place[position]
            val latLng = "${plan.coordinates.latitude}, ${plan.coordinates.longitude}"
            holder.bind(plan)

            holder.itemView.setOnClickListener {
                val action = DayPlansFragmentDirections.actionDayPlansFragmentToRestaurantsDetailFragment(plan.placeID,true,latLng)
                findNavController().navigate(action)
            }

        }

        fun setDate(plans: List<PlacesDetail>){
            this.place = plans
            notifyDataSetChanged()
        }

        fun deleteDate(position: Int){
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Confirm")
                    .setMessage("Are you sure ? ")
                    .setNegativeButton("Cancel") { dialoginterface, i ->

                    }
                    .setPositiveButton("Ok") { dialoginterface, i ->
                        val _plans = place as MutableList
                        planViewModel.deleteFavDetails(place[position])
                        _plans.removeAt(position)
                        this.place = _plans
                        notifyDataSetChanged()
                    }.show()
        }
    }


}