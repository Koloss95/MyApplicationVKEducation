package com.example.myapplicationvkeducation.presentation.util

import androidx.annotation.DrawableRes
import com.example.myapplicationvkeducation.R

@DrawableRes
fun appIconRes(iconKey: String): Int {
    return when (iconKey) {
        "notes" -> R.drawable.ic_notes
        "music" -> R.drawable.ic_music
        "workout" -> R.drawable.ic_workout
        "weather" -> R.drawable.ic_weather
        "books" -> R.drawable.ic_books
        "recipes" -> R.drawable.ic_recipes
        else -> R.drawable.ic_launcher_foreground
    }
}