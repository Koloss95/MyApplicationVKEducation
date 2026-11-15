package com.example.myapplicationvkeducation.presentation.catalog

sealed interface AppsEvent {

    data class ShowSnackbar(
        val message: String
    ) : AppsEvent
}
