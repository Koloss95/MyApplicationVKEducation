package com.example.myapplicationvkeducation.presentation.details

import com.example.myapplicationvkeducation.domain.model.AppInfo

data class AppDetailsUiState(
    val app: AppInfo? = null,
    val similarApps: List<AppInfo> = emptyList(),
    val showcaseApps: List<AppInfo> = emptyList()
)