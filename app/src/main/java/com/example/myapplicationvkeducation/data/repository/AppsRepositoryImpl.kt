package com.example.myapplicationvkeducation.data.repository

import com.example.myapplicationvkeducation.data.mapper.AppMapper
import com.example.myapplicationvkeducation.data.source.LocalAppsDataSource
import com.example.myapplicationvkeducation.domain.model.AppInfo
import com.example.myapplicationvkeducation.domain.repository.AppsRepository

class AppsRepositoryImpl(
    private val dataSource: LocalAppsDataSource,
    private val mapper: AppMapper
) : AppsRepository {

    override fun getApps(): List<AppInfo> {
        return dataSource.getApps().map { dto ->
            mapper.toDomain(dto)
        }
    }

    override fun getAppById(appId: Int): AppInfo? {
        val dto = dataSource.getApps().find { app ->
            app.id == appId
        } ?: return null

        return mapper.toDomain(dto)
    }
}