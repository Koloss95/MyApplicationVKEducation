package com.example.myapplicationvkeducation.data.source

import com.example.myapplicationvkeducation.data.dto.AppDetailsDto
import com.example.myapplicationvkeducation.data.dto.AppDto
import com.example.myapplicationvkeducation.data.network.CatalogApi
import javax.inject.Inject
import retrofit2.HttpException

class RemoteAppsDataSource @Inject constructor(
    private val api: CatalogApi
) {
    suspend fun getApps(): List<AppDto> = api.getApps()

    suspend fun getAppById(appId: String): AppDetailsDto? {
        return try {
            api.getAppById(appId)
        } catch (exception: HttpException) {
            // A missing app is different from a connection or server failure.
            if (exception.code() == 404) null else throw exception
        }
    }
}
