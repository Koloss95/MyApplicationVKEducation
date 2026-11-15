package com.example.myapplicationvkeducation.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplicationvkeducation.domain.model.AppInfo
import com.example.myapplicationvkeducation.presentation.components.AppIcon
import java.text.NumberFormat

@Composable
fun AppDetailsHeader(
    app: AppInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppIcon(
            iconUrl = app.iconUrl,
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = app.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = app.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            app.developer?.takeIf { it.isNotBlank() }?.let { developer ->
                Text(
                    text = developer,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (app.ageRating != null || app.sizeMb != null) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    app.ageRating?.let { ageRating ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "$ageRating+")
                            Text(
                                text = "Возраст",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    app.sizeMb?.let { sizeMb ->
                        val formattedSize = NumberFormat.getNumberInstance().apply {
                            maximumFractionDigits = 1
                        }.format(sizeMb)

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "$formattedSize МБ")
                            Text(
                                text = "Размер",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}
