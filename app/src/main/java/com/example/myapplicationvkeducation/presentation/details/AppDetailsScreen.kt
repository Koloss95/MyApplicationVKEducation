package com.example.myapplicationvkeducation.presentation.details

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.example.myapplicationvkeducation.presentation.components.LoadingState
import com.example.myapplicationvkeducation.presentation.components.RetryMessage

@Composable
fun AppDetailsScreen(
    appId: String,
    viewModel: AppDetailsViewModel,
    onBackClick: () -> Unit,
    onShowcaseAppClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val app = uiState.app
    val errorMessage = uiState.errorMessage
    val recommendationsError = uiState.recommendationsError
    val context = LocalContext.current

    var descriptionExpanded by rememberSaveable(appId) {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
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
                            Intent.createChooser(shareIntent, "Поделиться приложением")
                        )
                    }
                },
                shareEnabled = app != null && !uiState.isLoading && errorMessage == null,
                modifier = Modifier.statusBarsPadding().padding(horizontal = 16.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when {
                uiState.isLoading -> LoadingState("Загружаем приложение…")
                errorMessage != null -> RetryMessage(
                    message = errorMessage,
                    onRetry = viewModel::retry
                )
                app == null -> Text("Приложение не найдено")
                else -> {
                    AppDetailsHeader(app = app)

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

                    ScreenshotsList(
                        screenshots = app.screenshotUrls,
                        modifier = Modifier.fillMaxWidth()
                    )

                    AppDescription(
                        description = app.description,
                        expanded = descriptionExpanded,
                        onToggleClick = { descriptionExpanded = !descriptionExpanded },
                        modifier = Modifier.fillMaxWidth()
                    )

                    app.developer?.takeIf { it.isNotBlank() }?.let { developer ->
                        HorizontalDivider()
                        Developer(
                            name = developer,
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "Разработчик: $developer",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }

                    when {
                        uiState.isRecommendationsLoading ->
                            LoadingState("Загружаем рекомендации…")
                        recommendationsError != null -> RetryMessage(
                            message = recommendationsError,
                            onRetry = viewModel::retryRecommendations
                        )
                        else -> {
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

                            Showcase(
                                apps = uiState.showcaseApps,
                                onAppClick = onShowcaseAppClick
                            )
                        }
                    }
                }
            }
        }
    }
}
