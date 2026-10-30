package com.example.myapplicationvkeducation.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object AppsDestination

@Serializable
data class AppDetailsDestination(
    val appId: Int
)