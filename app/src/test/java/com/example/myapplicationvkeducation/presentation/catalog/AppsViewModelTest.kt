package com.example.myapplicationvkeducation.presentation.catalog

import androidx.lifecycle.ViewModelStore
import com.example.myapplicationvkeducation.domain.model.AppInfo
import com.example.myapplicationvkeducation.domain.repository.AppsRepository
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppsViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var store: ViewModelStore

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        store = ViewModelStore()
    }

    @After
    fun tearDown() {
        store.clear()
        Dispatchers.resetMain()
    }

    @Test
    fun retryReplacesConnectionErrorWithLoadedCatalog() = runTest(dispatcher) {
        var requests = 0
        val viewModel = createViewModel {
            requests++
            if (requests == 1) throw IOException("No connection")
            listOf(APP)
        }
        assertTrue(viewModel.uiState.value.isLoading)

        runCurrent()
        assertFalse(viewModel.uiState.value.isLoading)
        assertNotNull(viewModel.uiState.value.errorMessage)
        assertTrue(viewModel.uiState.value.apps.isEmpty())

        viewModel.retry()
        runCurrent()

        assertEquals(2, requests)
        assertFalse(viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.errorMessage)
        assertEquals(listOf(APP), viewModel.uiState.value.apps)
    }

    @Test
    fun emptyCatalogIsSuccessfulAndDoesNotShowAnError() = runTest(dispatcher) {
        val viewModel = createViewModel { emptyList() }

        runCurrent()

        assertFalse(viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.errorMessage)
        assertTrue(viewModel.uiState.value.apps.isEmpty())
    }

    @Test
    fun clearingViewModelCancelsPendingLoadWithoutReportingAnError() = runTest(dispatcher) {
        var requestCancelled = false
        val viewModel = createViewModel {
            try {
                awaitCancellation()
            } finally {
                requestCancelled = true
            }
        }
        runCurrent()
        assertTrue(viewModel.uiState.value.isLoading)

        store.clear()
        runCurrent()

        assertTrue(requestCancelled)
        assertNull(viewModel.uiState.value.errorMessage)
    }

    private fun createViewModel(load: suspend () -> List<AppInfo>): AppsViewModel {
        val repository = object : AppsRepository {
            override suspend fun getApps(): List<AppInfo> = load()

            override suspend fun getAppById(appId: String): AppInfo? =
                error("Catalog ViewModel must not request app details")
        }
        return AppsViewModel(repository).also { store.put("catalog", it) }
    }

    private companion object {
        val APP = AppInfo(
            id = "591b7524-c7c2-4fbd-8659-86845e87e6f2",
            name = "Notes",
            description = "Keep your ideas",
            category = "Tools",
            iconUrl = "https://example.org/notes.png"
        )
    }
}
