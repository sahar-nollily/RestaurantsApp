package com.bignerdranch.android.restaurantsapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.restaurantsapp.repository.WeatherRepository
import com.bignerdranch.android.restaurantsapp.viewmodel.weather.WeathersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val name = intent.getStringExtra("name")!!
            val notificationHelper = name?.let { NotificationHelper(context, it) }
            val nb = notificationHelper?.channelNotification
            notificationHelper?.manager!!.notify(5678, nb?.build())
        }
}