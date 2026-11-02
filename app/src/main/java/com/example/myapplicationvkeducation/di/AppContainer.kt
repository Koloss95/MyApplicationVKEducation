package com.example.myapplicationvkeducation.di

import com.example.myapplicationvkeducation.data.mapper.AppMapper
import com.example.myapplicationvkeducation.data.repository.AppsRepositoryImpl
import com.example.myapplicationvkeducation.data.source.LocalAppsDataSource
import com.example.myapplicationvkeducation.domain.repository.AppsRepository

class AppContainer {

    val appsRepository: AppsRepository = AppsRepositoryImpl(
        dataSource = LocalAppsDataSource(),
        mapper = AppMapper()
    )
}