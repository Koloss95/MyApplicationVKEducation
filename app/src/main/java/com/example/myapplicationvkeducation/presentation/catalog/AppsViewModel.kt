package com.example.myapplicationvkeducation.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplicationvkeducation.domain.repository.AppsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AppsViewModel(
    private val repository: AppsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AppsUiState(apps = repository.getApps())
    )

    val uiState: StateFlow<AppsUiState> = _uiState.asStateFlow()

    private val _events = Channel<AppsEvent>(
        capacity = Channel.BUFFERED
    )

    val events = _events.receiveAsFlow()

    fun onLogoClick() {
        viewModelScope.launch {
            _events.send(
                AppsEvent.ShowSnackbar(
                    message = "Добро пожаловать в каталог приложений!"
                )
            )
        }
    }

    companion object {
        fun factory(
            repository: AppsRepository
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AppsViewModel(repository = repository)
            }
        }
    }
}
