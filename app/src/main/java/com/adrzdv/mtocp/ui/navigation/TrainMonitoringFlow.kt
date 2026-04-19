package com.adrzdv.mtocp.ui.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.ui.screen.monitoring.train.EditCoachEntryPoint
import com.adrzdv.mtocp.ui.screen.monitoring.train.InitDataTrainMonitoringScreen
import com.adrzdv.mtocp.ui.screen.monitoring.train.MonitoringTrainInProgress
import com.adrzdv.mtocp.ui.screen.monitoring.train.TrainSchemeEditingScreen
import com.adrzdv.mtocp.ui.viewmodel.service.ViewModelLocator

fun NavGraphBuilder.trainMonitoringGraph(
    navController: NavHostController,
    appCacheRepository: CacheRepository,
    viewModelLocator: ViewModelLocator,
    appDependencies: AppDependencies
) {

    navigation(
        route = "train_revision_graph",
        startDestination = Screen.StartTrainRevision.route
    ) {
        composable(Screen.StartTrainRevision.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("train_revision_graph")
            }

            InitDataTrainMonitoringScreen(
                viewModelLocator.getTrainOrderViewModel(parentEntry),
                viewModelLocator.getTainAutocompleteViewModel(backStackEntry),
                viewModelLocator.getDepotAutocompleteViewModel(backStackEntry),
                navController = navController
            )
        }

        composable(Screen.MonitoringTrainInProgress.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("train_revision_graph")
            }
            MonitoringTrainInProgress(
                navController = navController,
                appCacheRepository = appCacheRepository,
                trainOrderViewModel = viewModelLocator.getTrainOrderViewModel(parentEntry),
            )
        }

        composable(Screen.TrainSchemeEdit.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("train_revision_graph")
            }

            TrainSchemeEditingScreen(
                appDependencies = appDependencies,
                navController = navController,
                trainOrderViewModel = viewModelLocator.getTrainOrderViewModel(parentEntry),
                depotAutocompleteViewModel = viewModelLocator.getDepotAutocompleteViewModel(
                    backStackEntry
                ),
                workerDepotAutocompleteViewModel = viewModelLocator.getWorkerDepotAutocompleteViewModel(
                    backStackEntry
                ),
                companyAutocompleteViewModel = viewModelLocator.getCompanyAutocompleteViewModel(
                    backStackEntry
                )
            )
        }

        composable(
            route = Screen.CoachEdit.route,
            arguments = listOf(
                navArgument("coachId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("selectedType") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val coachId = backStackEntry.arguments?.getString("coachId")
            val selectedType = backStackEntry.arguments?.getString("selectedType")
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("train_revision_graph")
            }

            EditCoachEntryPoint(
                coachId = coachId,
                coachType = selectedType,
                backStackEntry = backStackEntry,
                navController = navController,
                appDependencies = appDependencies,
                appCache = appCacheRepository,
                trainOrderViewModel = viewModelLocator.getTrainOrderViewModel(parentEntry),
                depotAutoCompleteViewModel = viewModelLocator.getDepotAutocompleteViewModel(
                    backStackEntry
                ),
                workerAutoCompleteViewModel = viewModelLocator.getWorkerDepotAutocompleteViewModel(
                    backStackEntry
                ),
                companyAutoCompleteViewModel = viewModelLocator.getCompanyAutocompleteViewModel(
                    backStackEntry
                )
            )
        }
    }
}