package com.example.myapplicationvkeducation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplicationvkeducation.ui.theme.MyApplicationVKEducationTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle


@Composable
fun AppsScreen(
    onAppClick: (Int) -> Unit = {},
    viewModel: AppsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(
            Lifecycle.State.STARTED
        ) {
            viewModel.events.collect { event ->
                when (event) {
                    is AppsEvent.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }
                }
            }
        }
    }

    AppsContent(
        uiState = uiState,
        onAppClick = onAppClick,
        onLogoClick = viewModel::onLogoClick,
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun AppsContent(
    uiState: AppsUiState,
    onAppClick: (Int) -> Unit = {},
    onLogoClick: () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppsToolbar(onLogoClick = onLogoClick)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = uiState.apps,
                key = { app -> app.id }
            ) { app ->
                AppCard(
                    app = app,
                    onClick = { onAppClick(app.id) }
                )
            }
        }
    }
}

@Composable
private fun AppsToolbar(
    onLogoClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = onLogoClick) {
                Image(
                    painter = painterResource(
                        R.drawable.ic_launcher_foreground
                    ),
                    contentDescription = "О каталоге приложений",
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(
                text = "Каталог приложений",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppsScreenPreview() {
    MyApplicationVKEducationTheme {
        AppsContent(
            uiState = AppsUiState(apps = sampleApps)
        )
    }
}