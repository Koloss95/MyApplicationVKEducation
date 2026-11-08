package com.example.myapplicationvkeducation.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.myapplicationvkeducation.domain.repository.AppsRepository
import com.example.myapplicationvkeducation.presentation.navigation.AppDetailsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppsRepository
) : ViewModel() {
    private val appId = savedStateHandle.toRoute<AppDetailsDestination>().appId
    private val _uiState = MutableStateFlow(AppDetailsUiState(isLoading = true))
    val uiState: StateFlow<AppDetailsUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null
    private var recommendationsJob: Job? = null

    init {
        retry()
    }

    fun retry() {
        if (loadJob?.isActive == true) return
        recommendationsJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.value = AppDetailsUiState(isLoading = true)
            try {
                val app = repository.getAppById(appId)
                _uiState.value = AppDetailsUiState(app = app)
                if (app != null) retryRecommendations()
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.value = AppDetailsUiState(
                    errorMessage = "Не удалось загрузить приложение. Проверьте подключение и попробуйте ещё раз."
                )
            }
        }
    }

    fun retryRecommendations() {
        if (_uiState.value.app == null || recommendationsJob?.isActive == true) return
        recommendationsJob = viewModelScope.launch {
            _uiState.update {
                it.copy(isRecommendationsLoading = true, recommendationsError = null)
            }
            try {
                val apps = repository.getApps()
                _uiState.update { state ->
                    state.copy(
                        similarApps = apps.filter { it.id != appId },
                        showcaseApps = apps,
                        isRecommendationsLoading = false
                    )
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isRecommendationsLoading = false,
                        recommendationsError = "Не удалось загрузить подборки приложений."
                    )
                }
            }
        }
    }
}
