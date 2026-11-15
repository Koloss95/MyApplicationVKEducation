package com.example.myapplicationvkeducation.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.example.myapplicationvkeducation.R

@Composable
fun AppIcon(
    iconUrl: String,
    modifier: Modifier = Modifier
) {
    val placeholder = painterResource(R.drawable.ic_launcher_foreground)

    AsyncImage(
        model = iconUrl,
        contentDescription = null,
        placeholder = placeholder,
        error = placeholder,
        fallback = placeholder,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
