package com.example.myapplicationvkeducation.presentation.details

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplicationvkeducation.R

@Composable
fun AppDetailsScreen(
    appId: Int,
    viewModel: AppDetailsViewModel,
    onBackClick: () -> Unit,
    onShowcaseAppClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val app = uiState.app
    val context = LocalContext.current

    var descriptionExpanded by rememberSaveable(appId) {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppDetailsToolbar(
                onBackClick = onBackClick,
                onShareClick = {
                    app?.let { selectedApp ->
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "${selectedApp.name}\n${selectedApp.description}"
                            )
                        }

                        context.startActivity(
                            Intent.createChooser(
                                shareIntent,
                                "Поделиться приложением"
                            )
                        )
                    }
                }
            )
            if (app != null) {
                AppDetailsHeader(
                    app = app,
                    developer = "Учебная студия",
                    ageRating = 3,
                    sizeMb = 25
                )
                InstallButton(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Это учебный каталог: установка не выполняется",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                SimilarApps(
                    apps = uiState.similarApps,
                    onDownloadClick = { selectedApp ->
                        Toast.makeText(
                            context,
                            "Учебный каталог: скачивание «${selectedApp.name}» не выполняется",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                ScreenshotsList(
                    screenshots = listOf(
                        R.drawable.screenshot_1,
                        R.drawable.screenshot_2,
                        R.drawable.screenshot_3
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                AppDescription(
                    description = """
        ${app.description}

        ${app.name} — приложение из нашего учебного каталога.
        Категория: ${app.category}.

        Здесь приведено демонстрационное описание. В настоящем
        каталоге этот раздел рассказывает о возможностях
        приложения, его особенностях и способах использования.
    """.trimIndent(),
                    expanded = descriptionExpanded,
                    onToggleClick = {
                        descriptionExpanded = !descriptionExpanded
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider()

                Developer(
                    name = "Учебная студия",
                    onClick = {
                        Toast.makeText(
                            context,
                            "Разработчик приложений учебного каталога",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            } else {
                Text(text = "Приложение не найдено")
            }

            Showcase(
                apps = uiState.showcaseApps,
                onAppClick = onShowcaseAppClick
            )
        }
    }
}
