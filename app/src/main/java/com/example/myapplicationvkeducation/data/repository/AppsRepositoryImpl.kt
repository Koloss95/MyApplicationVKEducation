package com.example.myapplicationvkeducation.data.repository

import com.example.myapplicationvkeducation.data.mapper.AppMapper
import com.example.myapplicationvkeducation.data.source.RemoteAppsDataSource
import com.example.myapplicationvkeducation.domain.model.AppInfo
import com.example.myapplicationvkeducation.domain.repository.AppsRepository
import javax.inject.Inject

class AppsRepositoryImpl @Inject constructor(
    private val dataSource: RemoteAppsDataSource,
    private val mapper: AppMapper
) : AppsRepository {
    override suspend fun getApps(): List<AppInfo> =
        dataSource.getApps().map { mapper.toDomain(it) }

    override suspend fun getAppById(appId: String): AppInfo? =
        dataSource.getAppById(appId)?.let { mapper.toDomain(it) }
}
