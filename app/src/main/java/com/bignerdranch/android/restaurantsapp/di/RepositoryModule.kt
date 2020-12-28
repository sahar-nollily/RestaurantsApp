package com.bignerdranch.android.restaurantsapp.di

import com.bignerdranch.android.restaurantsapp.repository.IPlanRepository
import com.bignerdranch.android.restaurantsapp.repository.PlanRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPlanRepository(
        planRepository: PlanRepository
    ): IPlanRepository

}