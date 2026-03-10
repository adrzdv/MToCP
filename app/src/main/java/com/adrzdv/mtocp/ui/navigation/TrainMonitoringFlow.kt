package com.adrzdv.mtocp.ui.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.ui.screen.monitoring.train.InitDataTrainMonitoringScreen
import com.adrzdv.mtocp.ui.screen.monitoring.train.MonitoringTrainInProgress
import com.adrzdv.mtocp.ui.screen.monitoring.train.TrainSchemeEditingScreen
import com.adrzdv.mtocp.ui.viewmodel.service.ViewModelLocator

fun NavGraphBuilder.trainMonitoringGraph(
    navController: NavHostController,
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
                )
            )
        }
    }
}