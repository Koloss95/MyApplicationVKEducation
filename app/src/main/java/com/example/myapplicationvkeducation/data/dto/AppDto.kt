package com.example.myapplicationvkeducation.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AppDto(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String
)
