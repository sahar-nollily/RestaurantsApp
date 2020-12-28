package com.bignerdranch.android.restaurantsapp.di

import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import com.bignerdranch.android.restaurantsapp.util.ChildWorkerFactory
import com.bignerdranch.android.restaurantsapp.util.WorkManager
import dagger.Binds
import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@Module
@InstallIn(ApplicationComponent::class)
interface WorkManagerModule {
    @Binds
    @IntoMap
    @WorkerKey(WorkManager::class)
    fun bindWorkManager(factory: WorkManager.Factory): ChildWorkerFactory
}