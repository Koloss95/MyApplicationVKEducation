package com.example.myapplicationvkeducation

import kotlinx.serialization.Serializable

@Serializable
data object AppsDestination

@Serializable
data class AppDetailsDestination(
    val appId: Int
)