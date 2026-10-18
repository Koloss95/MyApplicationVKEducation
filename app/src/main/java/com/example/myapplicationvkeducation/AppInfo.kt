package com.example.myapplicationvkeducation

import androidx.annotation.DrawableRes
import kotlinx.serialization.descriptors.SerialDescriptor

data class AppInfo(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    @DrawableRes val iconRes: Int
)
