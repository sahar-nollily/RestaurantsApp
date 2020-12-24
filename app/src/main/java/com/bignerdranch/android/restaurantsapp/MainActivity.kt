package com.bignerdranch.android.restaurantsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bignerdranch.android.restaurantsapp.databinding.ActivityMainBinding
import com.bignerdranch.android.restaurantsapp.util.ConnectivityLiveData

class MainActivity : AppCompatActivity() {
    private lateinit var connectivityLiveData: ConnectivityLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        connectivityLiveData= ConnectivityLiveData(application)
        connectivityLiveData.observe(this, Observer {isAvailable->
            if (isAvailable){
                binding.noConnection.visibility = View.GONE
            }else{
                binding.noConnection.visibility = View.VISIBLE
            }
        })
    }

}