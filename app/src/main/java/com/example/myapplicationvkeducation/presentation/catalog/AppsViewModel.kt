package com.example.myapplicationvkeducation.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationvkeducation.domain.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val repository: AppsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppsUiState(isLoading = true))
    val uiState: StateFlow<AppsUiState> = _uiState.asStateFlow()

    private val _events = Channel<AppsEvent>(capacity = Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private var loadJob: Job? = null

    init {
        retry()
    }

    fun retry() {
        if (loadJob?.isActive == true) return
        loadJob = viewModelScope.launch {
            _uiState.value = AppsUiState(isLoading = true)
            try {
                _uiState.value = AppsUiState(apps = repository.getApps())
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.value = AppsUiState(
                    errorMessage = "Не удалось загрузить каталог. Проверьте подключение и попробуйте ещё раз."
                )
            }
        }
    }

    fun onLogoClick() {
        viewModelScope.launch {
            _events.send(
                AppsEvent.ShowSnackbar(message = "Добро пожаловать в каталог приложений!")
            )
        }
    }
}
