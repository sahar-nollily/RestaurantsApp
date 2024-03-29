package com.bignerdranch.android.restaurantsapp.ui.plan

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import com.bignerdranch.android.restaurantsapp.data.Plan
import com.bignerdranch.android.restaurantsapp.databinding.DialogAddNoteBinding
import com.bignerdranch.android.restaurantsapp.databinding.FragmentUserPlansBinding
import com.bignerdranch.android.restaurantsapp.databinding.UserPlansItemBinding
import com.bignerdranch.android.restaurantsapp.util.CheckNetwork
import com.bignerdranch.android.restaurantsapp.viewmodel.place.PlacesViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.plan.PlanViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.plan.PlansViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserPlansFragment : Fragment() {

    private val args by navArgs<UserPlansFragmentArgs>()

    private val placesViewModel: PlacesViewModel by viewModels()

    private val planViewModel: PlanViewModel by viewModels()

    private var adapter = PlanAdapter(emptyList())
    private lateinit var checkNetwork: CheckNetwork


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentUserPlansBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_user_plans, container, false)
        checkNetwork = CheckNetwork(context)

        planViewModel.getPlan().observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                binding.addPlanTextView.visibility = View.VISIBLE
                binding.addPlanButton.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                binding.planDescriptionTextView.visibility = View.GONE
            }else{
                binding.addPlanTextView.visibility = View.GONE
                binding.addPlanButton.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.addPlanFloatingUtton.visibility = View.VISIBLE
                binding.planDescriptionTextView.visibility = View.GONE
            }
            adapter.setDate(it)
        })

        binding.addPlanButton.setOnClickListener {
            val action =
                UserPlansFragmentDirections.actionUserPlansFragmentToAddPlanFragment(
                    "add"
                )
            findNavController().navigate(action)
        }
        binding.addPlanFloatingUtton.setOnClickListener {
                val action =
                    UserPlansFragmentDirections.actionUserPlansFragmentToAddPlanFragment(
                        "add"
                    )
                findNavController().navigate(action)
        }

        if(args.CRUD == "add"){
            val builder1 = AlertDialog.Builder(context)
            builder1.setMessage("Pick Plan")
            builder1.setCancelable(true)
            val alert11 = builder1.create()
            alert11.show()
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = this@UserPlansFragment.adapter
        }
        return binding.root
    }

    private inner class PlanHolder(private val binding: UserPlansItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(plan: Plan){
            binding.planViewModel = PlansViewModel(plan)
            val color = resources.getIdentifier(plan.color, "color", context?.packageName)
            binding.itemViewConstraintLayout.background = ContextCompat.getDrawable(requireContext(), color)
            binding.editPlan.setOnClickListener {
                val action =
                    UserPlansFragmentDirections.actionUserPlansFragmentToAddPlanFragment(
                        "update",
                        plan
                    )
                findNavController().navigate(action)
            }
        }
    }

    private inner class PlanAdapter(private var plans: List<Plan>): RecyclerView.Adapter<PlanHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanHolder {
            val binding: UserPlansItemBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.user_plans_item,parent,false)
            return PlanHolder(binding)
        }

        override fun getItemCount()= plans.size
        override fun onBindViewHolder(holder: PlanHolder, position: Int) {
            if(plans.isNotEmpty()){
                val plan = plans[position]
                holder.bind(plan)

                holder.itemView.setOnClickListener {
                        if(args.CRUD == "add"){
                            val placeDetails = placesViewModel.placeDetails(args.placeID).value
                            if(placeDetails != null){
                                addNote(placeDetails, plan)
                            }
                        }else{
                            val action = UserPlansFragmentDirections.actionUserPlansFragmentToDayPlansFragment(plan)
                            findNavController().navigate(action)
                        }
                }
            }
        }

        fun setDate(plans: List<Plan>){
            this.plans = plans
            notifyDataSetChanged()
        }

    }

    private fun addNote(
        placeDetails: PlacesDetail,
        plan: Plan
    ) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val binding: DialogAddNoteBinding = DataBindingUtil.inflate(layoutInflater,R.layout.dialog_add_note,null,false)
        val dialogView = binding.root
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        binding.addNoteButton.setOnClickListener {
            val note = binding.noteEditText.text.toString()
            placeDetails.placeID = args.placeID
            placeDetails.planID = plan.planID
            placeDetails.time = "no"
            placeDetails.note = note
            planViewModel.addFavPlace(placeDetails)
            val action = UserPlansFragmentDirections.actionUserPlansFragmentToDayPlansFragment(plan)
            val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.userPlansFragment, true)
                    .build()
            findNavController().navigate(action,navOptions)
            alertDialog.dismiss()
        }
    }

}