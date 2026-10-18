package com.example.myapplicationvkeducation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Showcase(
    apps: List<AppInfo>,
    onAppClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    require(apps.size == 9) {
        "Для витрины необходимо передать ровно 9 приложений"
    }


    val pages = apps.chunked(3)


    val pagerState = rememberPagerState(
        pageCount = { pages.size }
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Витрина",
            style = MaterialTheme.typography.headlineSmall
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            pageSpacing = 16.dp
        ) { pageIndex ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (app in pages[pageIndex]) {
                    AppCard(
                        app = app,
                        onClick = { onAppClick(app.id) }
                    )
                }
            }
        }

        Text(
            text = "Страница ${pagerState.currentPage + 1} из ${pages.size}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}