package com.example.myapplicationvkeducation.data.repository

import com.example.myapplicationvkeducation.data.mapper.AppMapper
import com.example.myapplicationvkeducation.data.source.RemoteAppsDataSource
import com.example.myapplicationvkeducation.di.NetworkModule
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class NetworkAppsRepositoryTest {
    private lateinit var server: MockWebServer
    private lateinit var client: OkHttpClient
    private lateinit var repository: AppsRepositoryImpl

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        client = NetworkModule.provideOkHttpClient()
        // Exercise the production converter settings, changing only the server address.
        val retrofit = NetworkModule.provideRetrofit(client, NetworkModule.provideJson())
            .newBuilder()
            .baseUrl(server.url("/"))
            .build()
        repository = AppsRepositoryImpl(
            RemoteAppsDataSource(NetworkModule.provideCatalogApi(retrofit)),
            AppMapper()
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
        client.connectionPool.evictAll()
        client.dispatcher.executorService.shutdown()
    }

    @Test
    fun catalogPreservesUuidAndIgnoresAdditionalServerFields() = runTest {
        server.enqueue(jsonResponse(
            """
            [{
              "id": "$APP_ID",
              "name": "Notes",
              "description": "Keep your ideas",
              "category": "Tools",
              "iconUrl": "https://example.org/notes.png",
              "futureField": {"enabled": true}
            }]
            """.trimIndent()
        ))

        val apps = repository.getApps()

        assertEquals(1, apps.size)
        assertEquals(APP_ID, apps.single().id)
        assertEquals("Notes", apps.single().name)
        assertEquals("https://example.org/notes.png", apps.single().iconUrl)
        assertGetRequest("/catalog")
    }

    @Test
    fun detailsRequestMapsDeveloperSizeAndScreenshots() = runTest {
        server.enqueue(jsonResponse(
            """
            {
              "id": "$APP_ID",
              "name": "Notes",
              "description": "Full description from the details endpoint",
              "category": "Tools",
              "iconUrl": "https://example.org/notes.png",
              "developer": "Notes Team",
              "ageRating": 12,
              "size": 42.75,
              "screenshotUrlList": [
                "https://example.org/screen-1.png",
                "https://example.org/screen-2.png"
              ]
            }
            """.trimIndent()
        ))

        val app = requireNotNull(repository.getAppById(APP_ID))

        assertEquals(APP_ID, app.id)
        assertEquals("Full description from the details endpoint", app.description)
        assertEquals("Notes Team", app.developer)
        assertEquals(12, app.ageRating)
        assertEquals(42.75, requireNotNull(app.sizeMb), 0.0001)
        assertEquals(
            listOf("https://example.org/screen-1.png", "https://example.org/screen-2.png"),
            app.screenshotUrls
        )
        assertGetRequest("/catalog/$APP_ID")
    }

    @Test
    fun missingAppReturnsNull() = runTest {
        server.enqueue(MockResponse().setResponseCode(404))

        assertNull(repository.getAppById(APP_ID))
        assertGetRequest("/catalog/$APP_ID")
    }

    @Test
    fun catalogServerFailureIsPropagatedInsteadOfReturningSampleApps() = runTest {
        server.enqueue(MockResponse().setResponseCode(500))

        try {
            repository.getApps()
            fail("A failed catalog request must not return a successful list")
        } catch (exception: HttpException) {
            assertEquals(500, exception.code())
        }
        assertGetRequest("/catalog")
    }

    @Test
    fun detailsServerFailureIsNotReportedAsMissingApp() = runTest {
        server.enqueue(MockResponse().setResponseCode(500))

        try {
            repository.getAppById(APP_ID)
            fail("Only a 404 response should be converted to a missing app")
        } catch (exception: HttpException) {
            assertEquals(500, exception.code())
        }
        assertGetRequest("/catalog/$APP_ID")
    }

    private fun assertGetRequest(path: String) {
        val request = requireNotNull(server.takeRequest(1, TimeUnit.SECONDS))
        assertEquals("GET", request.method)
        assertEquals(path, request.path)
    }

    private fun jsonResponse(body: String): MockResponse = MockResponse()
        .setHeader("Content-Type", "application/json")
        .setBody(body)

    private companion object {
        const val APP_ID = "591b7524-c7c2-4fbd-8659-86845e87e6f2"
    }
}
