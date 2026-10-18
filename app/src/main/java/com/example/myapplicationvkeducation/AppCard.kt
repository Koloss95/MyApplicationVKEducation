package com.example.myapplicationvkeducation

import android.media.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplicationvkeducation.ui.theme.MyApplicationVKEducationTheme

@Composable
fun AppCard(
    app: AppInfo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Image(
                painter = painterResource(app.iconRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp)
                    .clip(RoundedCornerShape(12.dp))

            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = app.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = app.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun AppCardPreview() {
    MyApplicationVKEducationTheme {
        AppCard(
            app = AppInfo(
                id = 1,
                name = "Заметки",
                description = "Сохраняйте идеи и составляйте списки дел",
                category = "Инструменты",
                iconRes = R.drawable.ic_launcher_foreground
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}