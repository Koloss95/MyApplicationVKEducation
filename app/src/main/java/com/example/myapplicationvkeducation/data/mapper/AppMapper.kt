package com.example.myapplicationvkeducation.data.mapper

import com.example.myapplicationvkeducation.data.dto.AppDetailsDto
import com.example.myapplicationvkeducation.data.dto.AppDto
import com.example.myapplicationvkeducation.domain.model.AppInfo
import javax.inject.Inject

class AppMapper @Inject constructor() {
    fun toDomain(dto: AppDto): AppInfo = AppInfo(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        category = dto.category,
        iconUrl = dto.iconUrl
    )

    fun toDomain(dto: AppDetailsDto): AppInfo = AppInfo(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        category = dto.category,
        iconUrl = dto.iconUrl,
        developer = dto.developer,
        ageRating = dto.ageRating,
        sizeMb = dto.size,
        screenshotUrls = dto.screenshotUrlList
    )
}
