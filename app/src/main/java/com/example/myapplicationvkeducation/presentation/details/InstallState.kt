package com.example.myapplicationvkeducation.presentation.details

sealed interface InstallState {
    data object NotInstalled : InstallState

    data class Installing(
        val progress: Int
    ) : InstallState

    data object Installed : InstallState
}