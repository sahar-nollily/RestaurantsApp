package com.bignerdranch.android.restaurantsapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class CheckNetwork(context: Context?) {
    private val context = context
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}