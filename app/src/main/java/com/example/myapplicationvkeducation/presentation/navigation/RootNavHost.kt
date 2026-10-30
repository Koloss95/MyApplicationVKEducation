package com.example.myapplicationvkeducation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplicationvkeducation.domain.repository.AppsRepository
import com.example.myapplicationvkeducation.presentation.catalog.AppsScreen
import com.example.myapplicationvkeducation.presentation.catalog.AppsViewModel
import com.example.myapplicationvkeducation.presentation.details.AppDetailsScreen
import com.example.myapplicationvkeducation.presentation.details.AppDetailsViewModel

@Composable
fun RootNavHost(
    appsRepository: AppsRepository
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppsDestination
    ) {
        composable<AppsDestination> {
            val appsViewModel: AppsViewModel = viewModel(
                factory = AppsViewModel.factory(
                    repository = appsRepository
                )
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

            val detailsViewModel: AppDetailsViewModel = viewModel(
                factory = AppDetailsViewModel.factory(
                    appId = destination.appId,
                    repository = appsRepository
                )
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
