package com.example.myapplicationvkeducation.data.source

import javax.inject.Inject
import kotlinx.coroutines.delay

class FakeRatingsDataSource @Inject constructor() {

    suspend fun getRating(appId: String): Float {
        require(appId.isNotBlank()) {
            "Идентификатор приложения не должен быть пустым"
        }

        delay(1500)

        return (30..50).random() / 10f
    }
}