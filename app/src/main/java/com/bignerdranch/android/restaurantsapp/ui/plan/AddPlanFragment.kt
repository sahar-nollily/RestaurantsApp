package com.bignerdranch.android.restaurantsapp.ui.plan

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.restaurantsapp.util.CheckNetwork
import com.bignerdranch.android.restaurantsapp.R
import com.bignerdranch.android.restaurantsapp.data.Plan
import com.bignerdranch.android.restaurantsapp.databinding.FragmentAddPlanBinding
import com.bignerdranch.android.restaurantsapp.ui.DatePickerFragment
import com.bignerdranch.android.restaurantsapp.viewmodel.plan.PlanViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.util.*

private const val REQUEST_DATE = 0
private const val DIALOG_DATE = "DialogDate"

@AndroidEntryPoint
class AddPlanFragment : Fragment() , DatePickerFragment.Callbacks{

    private val args by navArgs<AddPlanFragmentArgs>()

    private val planViewModel:PlanViewModel by viewModels()
    private lateinit var binding :FragmentAddPlanBinding
    private lateinit var checkNetwork: CheckNetwork


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_plan, container, false)

        setColor()
        checkNetwork = CheckNetwork(context)

        binding.planDateTextView.setOnClickListener {
                DatePickerFragment.newInstance(Date()).apply {
                    setTargetFragment(this@AddPlanFragment, REQUEST_DATE)
                    show(this@AddPlanFragment.requireFragmentManager(), DIALOG_DATE)
            }
        }

            if(args.CRUD == "add"){
                binding.updateLinearLayout.visibility = View.GONE
                binding.createButton.visibility = View.VISIBLE
                binding.createButton.setOnClickListener {
                    val name = binding.planNameTextView.text.toString()
                    val color = binding.color.text.toString()
                    val date = binding.planDateTextView.text.toString()
                    val description = binding.planDescriptionTextView.text.toString()
                    checkInputValidation(name, description)
                    if(checkInputValidation(name, description)){
                        val plan = Plan(0, name, color, date, description)
                        planViewModel.addPlan(plan)
                        val action =
                            AddPlanFragmentDirections.actionAddPlanFragmentToUserPlansFragment()
                        findNavController().navigate(action)
                    }
                }
            }

            if(args.CRUD == "update"){
                binding.updateLinearLayout.visibility = View.VISIBLE
                binding.createButton.visibility = View.GONE
                val plan = args.plan
                if(plan != null){
                    binding.planNameTextView.setText(plan.planName)
                    binding.planDateTextView.setText(plan.date)
                    binding.color.setText(plan.color)
                    binding.planDateTextView.setText(plan.date)
                    binding.planDescriptionTextView.setText(plan.planDescription)
                    binding.updateButton.setOnClickListener {
                        val name = binding.planNameTextView.text.toString()
                        val color = binding.color.text.toString()
                        val date = binding.planDateTextView.text.toString()
                        val description = binding.planDescriptionTextView.text.toString()
                        checkInputValidation(name, description)
                        if(checkInputValidation(name, description)){
                            val plan = Plan(plan.planID, name, color, date, description)
                            planViewModel.updatePlan(plan)
                            val action =
                                AddPlanFragmentDirections.actionAddPlanFragmentToUserPlansFragment()
                            findNavController().navigate(action)
                        }
                    }
                    binding.deleteTextView.setOnClickListener {
                        val dialog = AlertDialog.Builder(context)
                        dialog.setTitle("Confirm")
                                .setMessage("Are you sure ? ")
                                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialoginterface, i ->

                                })
                                .setPositiveButton("Ok", DialogInterface.OnClickListener { dialoginterface, i ->
                                    planViewModel.deletePlan(plan)
                                    val action =
                                            AddPlanFragmentDirections.actionAddPlanFragmentToUserPlansFragment()
                                    findNavController().navigate(action)
                                }).show()
                    }
                }
            }


        return binding.root
    }

    private fun checkInputValidation(name: String, description: String): Boolean{
        return if(name.isEmpty() && description.isEmpty()){
            binding.planNameTextView.error = "required"
            binding.planDescriptionTextView.error = "required"
            false
        }else if(name.isEmpty()){
            binding.planNameTextView.error = "required"
            false
        }else if(description.isEmpty()){
            binding.planDescriptionTextView.error = "required"
            false
        }else{
            true
        }
    }

    private fun setColor() {
        binding.cyan.setOnClickListener {
            binding.color.text = "cyan"
            binding.cyan.text = " ✔"
            binding.purple.text = ""
            binding.gray.text = ""
            binding.green.text = ""
            binding.yellow.text = ""
            binding.orange.text = ""
            binding.pink.text = ""
        }
        binding.purple.setOnClickListener {
            binding.color.text = "purple"
            binding.cyan.text = ""
            binding.purple.text = " ✔"
            binding.gray.text = ""
            binding.green.text = ""
            binding.yellow.text = ""
            binding.orange.text = ""
            binding.pink.text = ""
        }
        binding.gray.setOnClickListener {
            binding.color.text = "gray"
            binding.cyan.text = ""
            binding.purple.text = ""
            binding.gray.text = " ✔"
            binding.green.text = ""
            binding.yellow.text = ""
            binding.orange.text = ""
            binding.pink.text = ""
        }
        binding.green.setOnClickListener {
            binding.color.text = "green"
            binding.cyan.text = ""
            binding.purple.text = ""
            binding.gray.text = ""
            binding.green.text = " ✔"
            binding.yellow.text = ""
            binding.orange.text = ""
            binding.pink.text = ""
        }
        binding.yellow.setOnClickListener {
            binding.color.text = "yellow"
            binding.cyan.text = ""
            binding.purple.text = ""
            binding.gray.text = ""
            binding.green.text = ""
            binding.yellow.text = " ✔"
            binding.orange.text = ""
            binding.pink.text = ""
        }
        binding.orange.setOnClickListener {
            binding.color.text = "orange"
            binding.cyan.text = ""
            binding.purple.text = ""
            binding.gray.text = ""
            binding.green.text = ""
            binding.yellow.text = ""
            binding.orange.text = " ✔"
            binding.pink.text = ""
        }
        binding.pink.setOnClickListener {
            binding.color.text = "pink"
            binding.cyan.text = ""
            binding.purple.text = ""
            binding.gray.text = ""
            binding.green.text = ""
            binding.yellow.text = ""
            binding.orange.text = ""
            binding.pink.text = " ✔"
        }

    }

    override fun onDateSelected(_date: Date) {
        val date= DateFormat.getDateInstance(DateFormat.MEDIUM).format(_date)
        binding.planDateTextView.setText(date)
    }

}