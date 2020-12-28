package com.bignerdranch.android.restaurantsapp.util

import android.R
import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.core.app.NotificationCompat


class NotificationHelper(context: Context?,val text: String ,val name:String ) : ContextWrapper(context) {
    private var mManager: NotificationManager? = null

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(
            channelID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        manager!!.createNotificationChannel(channel)
    }

    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }

    val channelNotification: NotificationCompat.Builder
        get() = NotificationCompat.Builder(
            applicationContext,
            channelID
        )
            .setContentTitle(name)
            .setContentText("The weather is $text at $name")
            .setSmallIcon(R.drawable.alert_light_frame)

    companion object {
        const val channelID: String = "1"
        const val channelName: String = "Forecast Weather"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }
}