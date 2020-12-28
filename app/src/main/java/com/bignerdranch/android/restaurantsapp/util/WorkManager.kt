package com.bignerdranch.android.restaurantsapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.bignerdranch.android.restaurantsapp.repository.PlanRepository
import com.bignerdranch.android.restaurantsapp.repository.WeatherRepository
import javax.inject.Inject


class WorkManager @Inject constructor (private val weatherRepository: WeatherRepository,
                                       private val planRepository: PlanRepository,
                                       val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val name = inputData.getString("name")!!
        val location = inputData.getString("location")
        val getTime = inputData.getString("time")?.split(":")!!
        val favID = inputData.getString("favID")
        val time = getTime[0].toInt()
        if (location != null) {
            val weather = weatherRepository.getForecast(location,"2",0)
            val text = weather.get(time-1).condition.text
            val notificationHelper = NotificationHelper(context, text, name)
            val nb = notificationHelper.channelNotification
            notificationHelper.manager!!.notify(5678, nb.build())
            if (favID != null) {
                planRepository.setTimeNotification(favID,"no")
            }
        }
        return Result.success()
    }

    class Factory @Inject constructor(
            private val weatherRepository: WeatherRepository,
            private val planRepository: PlanRepository
    ): ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return WorkManager(weatherRepository, planRepository, appContext, params)
        }
    }
}