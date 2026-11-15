package com.example.myapplicationvkeducation.data.network

import com.example.myapplicationvkeducation.data.dto.AppDetailsDto
import com.example.myapplicationvkeducation.data.dto.AppDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {
    @GET("catalog")
    suspend fun getApps(): List<AppDto>

    @GET("catalog/{id}")
    suspend fun getAppById(@Path("id") appId: String): AppDetailsDto
}
