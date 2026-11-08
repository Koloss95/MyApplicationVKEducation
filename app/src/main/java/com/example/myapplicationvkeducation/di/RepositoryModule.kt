package com.example.myapplicationvkeducation.di

import com.example.myapplicationvkeducation.data.repository.AppsRepositoryImpl
import com.example.myapplicationvkeducation.domain.repository.AppsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppsRepository(implementation: AppsRepositoryImpl): AppsRepository
}
