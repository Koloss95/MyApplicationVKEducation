package com.example.myapplicationvkeducation.domain.repository

import com.example.myapplicationvkeducation.domain.model.AppInfo

interface AppsRepository {
    suspend fun getApps(): List<AppInfo>
    suspend fun getAppById(appId: String): AppInfo?
}
