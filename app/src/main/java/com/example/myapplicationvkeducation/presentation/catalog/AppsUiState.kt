package com.example.myapplicationvkeducation.presentation.catalog

import com.example.myapplicationvkeducation.domain.model.AppInfo

data class AppsUiState(
    val apps: List<AppInfo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
