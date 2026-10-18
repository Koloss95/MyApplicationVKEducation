package com.example.myapplicationvkeducation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AppsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        AppsUiState(apps = sampleApps)
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
}