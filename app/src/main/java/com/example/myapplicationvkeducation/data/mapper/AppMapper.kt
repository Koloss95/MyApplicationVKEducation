package com.example.myapplicationvkeducation.data.mapper

import com.example.myapplicationvkeducation.data.dto.AppDto
import com.example.myapplicationvkeducation.domain.model.AppInfo

class AppMapper {

    fun toDomain(dto: AppDto): AppInfo {
        return AppInfo(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            category = dto.category,
            iconKey = dto.iconKey
        )
    }
}