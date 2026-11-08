package com.example.myapplicationvkeducation.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AppDetailsDto(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String,
    val developer: String? = null,
    val ageRating: Int? = null,
    val size: Double? = null,
    val screenshotUrlList: List<String> = emptyList()
)
