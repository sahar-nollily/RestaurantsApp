package com.bignerdranch.android.restaurantsapp

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.restaurantsapp.database.plan.Plan
import com.bignerdranch.android.restaurantsapp.databinding.FragmentUserPlansBinding
import com.bignerdranch.android.restaurantsapp.databinding.UserPlansItemBinding
import com.bignerdranch.android.restaurantsapp.databinding.ViewPagerItemBinding
import com.bignerdranch.android.restaurantsapp.viewmodel.plan.PlanViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.plan.PlansViewModel
import com.bignerdranch.android.restaurantsapp.viewmodel.restaurant.RestaurantViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class UserPlansFragment : Fragment() {

    private val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }
    private var adapter = PlanAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentUserPlansBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_user_plans, container, false)

        planViewModel.getPlan.observe(viewLifecycleOwner, Observer {
            adapter.setDate(it)
            if(it.isEmpty()){
                binding.addPlanTextView.visibility = View.VISIBLE
                binding.addPlanButton.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }else{
                binding.addPlanTextView.visibility = View.GONE
                binding.addPlanButton.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        })

        binding.addPlanButton.setOnClickListener {
            val action = UserPlansFragmentDirections.actionUserPlansFragmentToAddPlanFragment("add")
            findNavController().navigate(action)
        }
        binding.addPlanFloatingUtton.setOnClickListener {
                val action = UserPlansFragmentDirections.actionUserPlansFragmentToAddPlanFragment("add")
                findNavController().navigate(action)
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
            binding.itemView.background = ContextCompat.getDrawable(requireContext(), color)
            binding.editPlan.setOnClickListener {
                val action = UserPlansFragmentDirections.actionUserPlansFragmentToAddPlanFragment("update",plan)
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
            val plan = plans[position]
            holder.bind(plan)
        }

        fun setDate(plans: List<Plan>){
            this.plans = plans
            notifyDataSetChanged()
        }

    }

}