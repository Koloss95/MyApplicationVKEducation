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
import kotlinx.coroutines.flow.update
import kotlin.system.measureTimeMillis

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val repository: AppsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppsUiState(isLoading = true))
    val uiState: StateFlow<AppsUiState> = _uiState.asStateFlow()

    private val _events = Channel<AppsEvent>(capacity = Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private var loadJob: Job? = null

    private var ratingsJob: Job? = null

    init {
        retry()
    }

    fun retry() {
        if (loadJob?.isActive == true) return

        ratingsJob?.cancel()
        ratingsJob = null

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

    fun loadRatingsSequentially() {
        loadRatings(inParallel = false)
    }

    fun loadRatingsInParallel() {
        loadRatings(inParallel = true)
    }

    private fun loadRatings(inParallel: Boolean) {
        val state = _uiState.value

        if (
            state.isLoading ||
            state.apps.isEmpty() ||
            ratingsJob?.isActive == true
        ) {
            return
        }

        val ids = state.apps.map { it.id }

        ratingsJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isRatingsLoading = true,
                    ratingsError = null
                )
            }

            try {
                var ratings: Map<String, Float> = emptyMap()

                val elapsedMs = measureTimeMillis {
                    ratings = if (inParallel) {
                        repository.loadRatingsInParallel(ids)
                    } else {
                        repository.loadRatingsSequentially(ids)
                    }
                }

                _uiState.update {
                    it.copy(
                        ratings = ratings,
                        isRatingsLoading = false,
                        sequentialTimeMs = if (!inParallel) {
                            elapsedMs
                        } else {
                            it.sequentialTimeMs
                        },
                        parallelTimeMs = if (inParallel) {
                            elapsedMs
                        } else {
                            it.parallelTimeMs
                        }
                    )
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isRatingsLoading = false,
                        ratingsError = "Не удалось загрузить рейтинги. Попробуйте ещё раз."
                    )
                }
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
