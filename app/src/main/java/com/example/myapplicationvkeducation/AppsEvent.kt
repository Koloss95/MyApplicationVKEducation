package com.example.myapplicationvkeducation

sealed interface AppsEvent {

    data class ShowSnackbar(
        val message: String
    ) : AppsEvent
}