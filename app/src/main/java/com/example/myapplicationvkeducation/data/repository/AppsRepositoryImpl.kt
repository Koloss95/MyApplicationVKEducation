package com.example.myapplicationvkeducation.data.repository

import com.example.myapplicationvkeducation.data.mapper.AppMapper
import com.example.myapplicationvkeducation.data.source.RemoteAppsDataSource
import com.example.myapplicationvkeducation.domain.model.AppInfo
import com.example.myapplicationvkeducation.domain.repository.AppsRepository
import javax.inject.Inject
import com.example.myapplicationvkeducation.data.source.FakeRatingsDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class AppsRepositoryImpl @Inject constructor(
    private val dataSource: RemoteAppsDataSource,
    private val mapper: AppMapper,
    private val ratingsDataSource: FakeRatingsDataSource
) : AppsRepository {
    override suspend fun getApps(): List<AppInfo> =
        dataSource.getApps().map { mapper.toDomain(it) }

    override suspend fun getAppById(appId: String): AppInfo? =
        dataSource.getAppById(appId)?.let { mapper.toDomain(it) }

    override suspend fun loadRatingsSequentially(
        ids: List<String>
    ): Map<String, Float> {
        val ratings = mutableMapOf<String, Float>()

        for (id in ids) {
            val rating = ratingsDataSource.getRating(id)
            ratings[id] = rating
        }

        return ratings
    }

    override suspend fun loadRatingsInParallel(
        ids: List<String>
    ): Map<String, Float> = coroutineScope {
        ids.map { id ->
            async {
                id to ratingsDataSource.getRating(id)
            }
        }.awaitAll().toMap()
    }
}
