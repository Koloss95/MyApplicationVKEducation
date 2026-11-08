package com.example.myapplicationvkeducation.domain.model

data class AppInfo(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String,
    val developer: String? = null,
    val ageRating: Int? = null,
    val sizeMb: Double? = null,
    val screenshotUrls: List<String> = emptyList()
)
