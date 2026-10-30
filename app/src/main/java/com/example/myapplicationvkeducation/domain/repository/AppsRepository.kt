package com.example.myapplicationvkeducation.domain.repository

import com.example.myapplicationvkeducation.domain.model.AppInfo

interface AppsRepository {

    fun getApps(): List<AppInfo>

    fun getAppById(appId: Int): AppInfo?
}