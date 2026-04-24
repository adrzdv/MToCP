package com.adrzdv.mtocp.ui.navigation

import androidx.activity.compose.LocalActivity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.ui.screen.InfoCatalogScreen
import com.adrzdv.mtocp.ui.screen.RegisterScreen
import com.adrzdv.mtocp.ui.screen.RequestDocumentScreen
import com.adrzdv.mtocp.ui.screen.ServiceScreen
import com.adrzdv.mtocp.ui.screen.SplashScreen
import com.adrzdv.mtocp.ui.screen.StartMenuScreen
import com.adrzdv.mtocp.ui.screen.old.NewRevisionScreen
import com.adrzdv.mtocp.ui.viewmodel.service.ViewModelLocator
import com.adrzdv.mtocp.util.DirectoryHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun NavGraphBuilder.splashDestination(
    navController: NavHostController,
    appDependencies: AppDependencies
) {
    composable(Screen.Splash.route) {
        val hasToken = appDependencies
            .userDataStorage
            .getAccessToken()?.isNotBlank()

        SplashScreen(
            onTimeout = {
                val nextDestination = if (hasToken == true) {
                    Screen.MainMenu.route
                } else {
                    Screen.Register.route
                }
                navController.navigate(nextDestination) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        )
    }
}

fun NavGraphBuilder.mainMenuDestination(
    navController: NavHostController,
    appDependencies: AppDependencies,
    version: String
) {
    composable(Screen.MainMenu.route) {
        val activity = LocalActivity.current
        StartMenuScreen(
            onStartRevisionClick = { navController.navigate(Screen.NewRevision.route) },
            onOpenViolationCatalogClick = { navController.navigate(Screen.Catalog.route) },
            onServiceMenuClick = { navController.navigate(Screen.Settings.route) },
            onRequestWebClick = { navController.navigate(Screen.Request.route) },
            onExitClick = { activity?.finish() },
            userDataStorage = appDependencies.userDataStorage,
            appVersion = version
        )
    }
}

fun NavGraphBuilder.authUserDestination(
    navController: NavHostController,
    viewModelLocator: ViewModelLocator
) {
    composable(Screen.Register.route) { backStackEntry ->
        RegisterScreen(navController, viewModelLocator.getAuthViewModel(backStackEntry))
    }
}

fun NavGraphBuilder.appSettingsDestination(
    navController: NavHostController,
    appDependencies: AppDependencies,
    viewModelLocator: ViewModelLocator
) {
    composable(Screen.Settings.route) { backStackEntry ->
        val serviceScreenVM = viewModelLocator.getServiceViewModel(backStackEntry)
        ServiceScreen(
            serviceScreenVM = serviceScreenVM,
            appDependencies = appDependencies,
            onCleanRepositoryClick = { onResult ->
                onCleanRepository(onResult)
            },
            onDeleteProfile = {
                onDeleteProfile(navController, appDependencies)
            },
            onLoadCatalog = {
                serviceScreenVM.syncDictionaries()
            },
            onBackClick = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.catalogsDestination(
    navController: NavHostController,
    viewModelLocator: ViewModelLocator
) {
    composable(Screen.Catalog.route) { backStackEntry ->
        InfoCatalogScreen(
            navPriorityHost = navController,
            violationViewModel = viewModelLocator.getViolationViewModel(backStackEntry),
            trainInfoViewModel = viewModelLocator.getTrainInfoViewModel(backStackEntry),
            kriCoachViewModel = viewModelLocator.getKriCoachViewModel(backStackEntry),
            depotViewModel = viewModelLocator.getDepotViewModel(backStackEntry)
        )
    }
}

fun NavGraphBuilder.newRevisionDestination(
    navController: NavHostController
) {
    composable(Screen.NewRevision.route) { backStackEntry ->
        NewRevisionScreen(
            onBackClick = { navController.popBackStack() },
            onTrainRevisionClick = { navController.navigate(Screen.StartTrainRevision.route) },
            onTicketOfficeRevisionClick = { },
            onCoachRevisionClick = {})
    }
}

fun NavGraphBuilder.requestsDestination(
    navController: NavHostController,
    appDependencies: AppDependencies,
    viewModelLocator: ViewModelLocator
) {
    composable(Screen.Request.route) { backStackEntry ->
        val username = appDependencies
            .userDataStorage
            .getUsername() ?: ""
        val prefix = appDependencies
            .userDataStorage
            .getUserBranch() ?: ""

        RequestDocumentScreen(
            username = username,
            prefix = prefix,
            requestDocumentViewModel = viewModelLocator.getDocumentViewModel(backStackEntry),
            onBackClick = { navController.popBackStack() }
        )
    }
}

//fun NavGraphBuilder.trainRevisionStartDestination(
//    navController: NavHostController,
//    viewModelLocator: ViewModelLocator,
//    appDependencies: AppDependencies
//) {
//
//}
//
//fun NavGraphBuilder.monitoringTrainInProgressDestination(
//    navController: NavHostController,
//    viewModelLocator: ViewModelLocator,
//    appDependencies: AppDependencies
//) {
//
//}
//
//fun NavGraphBuilder.editTrainScheme(
//    navController: NavHostController,
//    viewModelLocator: ViewModelLocator
//) {
//
//}

private fun onCleanRepository(onResult: (Boolean) -> Unit) {
    val success = DirectoryHandler.cleanDirectories()
    onResult(success)
}

private fun onDeleteProfile(navController: NavHostController, appDependencies: AppDependencies) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            appDependencies.authRepo.logout(
                appDependencies.userDataStorage.getRefreshToken().orEmpty()
            )
        } finally {
            appDependencies.userDataStorage.deleteToken()
        }
    }
    navController.navigate(Screen.Register.route) {
        popUpTo(Screen.MainMenu.route) { inclusive = true }
    }
}
