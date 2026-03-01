package com.adrzdv.mtocp.ui.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.ui.screen.InfoCatalogScreen
import com.adrzdv.mtocp.ui.screen.NewRevisionScreen
import com.adrzdv.mtocp.ui.screen.RegisterScreen
import com.adrzdv.mtocp.ui.screen.RequestWebScreen
import com.adrzdv.mtocp.ui.screen.ServiceScreen
import com.adrzdv.mtocp.ui.screen.SplashScreen
import com.adrzdv.mtocp.ui.screen.StartMenuScreen
import com.adrzdv.mtocp.ui.screen.monitoring.train.InitDataTrainMonitoringScreen
import com.adrzdv.mtocp.ui.viewmodel.model.ServiceViewModel
import com.adrzdv.mtocp.ui.viewmodel.service.ViewModelLocator
import com.adrzdv.mtocp.util.DirectoryHandler

fun NavGraphBuilder.splashDestination(
    navController: NavHostController,
    appDependencies: AppDependencies
) {
    composable(Screen.Splash.route) {
        val hasToken = appDependencies
            .userDataStorage
            .getToken()?.isNotBlank()

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
    appDependencies: AppDependencies
) {
    composable(Screen.Settings.route) { backStackEntry ->
        val serviceScreenVM: ServiceViewModel = viewModel(backStackEntry)
        val context = LocalContext.current

        val filePickerLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            handleFilePickerResult(result, context, serviceScreenVM, appDependencies)
        }
        ServiceScreen(
            serviceScreenVM = serviceScreenVM,
            onCleanRepositoryClick = { onResult ->
                onCleanRepository(onResult)
            },
            onDeleteProfile = {
                onDeleteProfile(navController, appDependencies)
            },
            onLoadCatalog = {
                filePickerLauncher.launch(createFilePickerIntent())
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
            revisionTypes = RevisionType.getListOfTypes(),
            violationViewModel = viewModelLocator.getViolationViewModel(backStackEntry),
            trainInfoViewModel = viewModelLocator.getTrainInfoViewModel(backStackEntry),
            kriCoachViewModel = viewModelLocator.getKriCoachViewModel(backStackEntry),
            depotViewModel = viewModelLocator.getDepotViewModel(backStackEntry),
            companyViewModel = viewModelLocator.getCompanyViewModel(backStackEntry)
        )
    }
}

fun NavGraphBuilder.newRevisionDestination(
    navController: NavHostController
) {
    composable(Screen.NewRevision.route) { backStackEntry ->
        NewRevisionScreen(
            navController = navController,
            onBackClick = { navController.popBackStack() },
            onTrainRevisionClick = { navController.navigate(Screen.StartTrainRevision.route) },
            onTicketOfficeRevisionClick = { },
            onCoachRevisionClick = {})
    }
}

fun NavGraphBuilder.requestsDestination(
    navController: NavHostController,
    appDependencies: AppDependencies
) {
    composable(Screen.Request.route) { backStackEntry ->
        val username = appDependencies
            .userDataStorage
            .getUsername() ?: ""

        RequestWebScreen(
            username = username,
            onBackClick = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.trainRevisionStartDestination(
    navController: NavHostController,
    viewModelLocator: ViewModelLocator,
    appDependencies: AppDependencies
) {
    composable(Screen.StartTrainRevision.route) { backStackEntry ->
        InitDataTrainMonitoringScreen(
            viewModelLocator.getTrainOrderViewModel(backStackEntry),
            navController = navController
        )
    }
}

private fun handleFilePickerResult(
    result: ActivityResult,
    context: Context,
    serviceScreenVM: ServiceViewModel,
    appDependencies: AppDependencies
) {
    if (result.resultCode != Activity.RESULT_OK) return
    val fileUri: Uri = result.data?.data ?: return
    appDependencies.importManager.importFromJson(context, fileUri) { message ->
        if (message == MessageCodes.SUCCESS.messageTitle) {
            serviceScreenVM.showMessage(message)
        } else {
            serviceScreenVM.showErrorMessage(message)
        }
    }
}

private fun onCleanRepository(onResult: (Boolean) -> Unit) {
    val success = DirectoryHandler.cleanDirectories()
    onResult(success)
}

private fun onDeleteProfile(navController: NavHostController, appDependencies: AppDependencies) {
    appDependencies.userDataStorage.deleteToken()
    navController.navigate(Screen.Register.route) {
        popUpTo(Screen.MainMenu.route) { inclusive = true }
    }
}

private fun createFilePickerIntent(): Intent {
    return Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "*/*"
        putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/json", "text/json"))
        addCategory(Intent.CATEGORY_OPENABLE)
    }
}