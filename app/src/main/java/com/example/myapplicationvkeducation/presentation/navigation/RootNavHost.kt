package com.example.myapplicationvkeducation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplicationvkeducation.presentation.catalog.AppsScreen
import com.example.myapplicationvkeducation.presentation.catalog.AppsViewModel
import com.example.myapplicationvkeducation.presentation.details.AppDetailsScreen
import com.example.myapplicationvkeducation.presentation.details.AppDetailsViewModel

@Composable
fun RootNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppsDestination
    ) {
        composable<AppsDestination> { backStackEntry ->
            val appsViewModel: AppsViewModel = hiltViewModel(
                viewModelStoreOwner = backStackEntry
            )

            AppsScreen(
                onAppClick = { appId ->
                    navController.navigate(
                        AppDetailsDestination(appId = appId)
                    ) {
                        launchSingleTop = true
                    }
                },
                viewModel = appsViewModel
            )
        }

        composable<AppDetailsDestination> { backStackEntry ->
            val destination =
                backStackEntry.toRoute<AppDetailsDestination>()

            val detailsViewModel: AppDetailsViewModel = hiltViewModel(
                viewModelStoreOwner = backStackEntry
            )

            AppDetailsScreen(
                appId = destination.appId,
                viewModel = detailsViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onShowcaseAppClick = { selectedAppId ->
                    if (selectedAppId != destination.appId) {
                        navController.navigate(
                            AppDetailsDestination(
                                appId = selectedAppId
                            )
                        )
                    }
                }
            )
        }
    }
}
