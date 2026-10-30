package com.example.myapplicationvkeducation.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplicationvkeducation.domain.repository.AppsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppDetailsViewModel(
    appId: Int,
    repository: AppsRepository
) : ViewModel() {

    private val apps = repository.getApps()

    private val _uiState = MutableStateFlow(
        AppDetailsUiState(
            app = repository.getAppById(appId),
            similarApps = apps.filter { it.id != appId },
            showcaseApps = apps
        )
    )

    val uiState: StateFlow<AppDetailsUiState> =
        _uiState.asStateFlow()

    companion object {
        fun factory(
            appId: Int,
            repository: AppsRepository
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AppDetailsViewModel(
                    appId = appId,
                    repository = repository
                )
            }
        }
    }
}