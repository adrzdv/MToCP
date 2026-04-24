package com.adrzdv.mtocp.ui.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.BuildConfig
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.api.UpdateManager
import com.adrzdv.mtocp.data.model.UpdateConfig
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.snackbar.ErrorSnackbar
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

fun NavGraphBuilder.splashDestination(
    navController: NavHostController,
    appDependencies: AppDependencies
) {
    composable(Screen.Splash.route) {
        val activity = LocalActivity.current
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        var animationFinished by remember { mutableStateOf(false) }
        var updateCheckFinished by remember { mutableStateOf(false) }
        var updateConfig by remember { mutableStateOf<UpdateConfig?>(null) }
        var isDownloading by remember { mutableStateOf(false) }
        var hasProceeded by remember { mutableStateOf(false) }
        val hasToken = appDependencies
            .userDataStorage
            .getAccessToken()?.isNotBlank()

        fun proceedNext() {
            if (hasProceeded) return
            hasProceeded = true

            val nextDestination = if (hasToken == true) {
                Screen.MainMenu.route
            } else {
                Screen.Register.route
            }
            navController.navigate(nextDestination) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }

        LaunchedEffect(Unit) {
            try {
                val response = appDependencies.retrofitHolder.updaterApi.getUpdateInfo()
                val config = response.body()

                if (response.isSuccessful &&
                    config != null &&
                    config.newVersionCode != BuildConfig.VERSION_NAME
                ) {
                    updateConfig = config
                }
            } catch (_: Exception) {
                updateConfig = null
            } finally {
                updateCheckFinished = true
            }
        }

        LaunchedEffect(animationFinished, updateCheckFinished, updateConfig) {
            if (animationFinished && updateCheckFinished && updateConfig == null) {
                proceedNext()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            SplashScreen(
                onTimeout = {
                    animationFinished = true
                }
            )

            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                CustomSnackbarHost(hostState = snackbarHostState)
            }

            updateConfig?.let { config ->
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text(stringResource(R.string.update_available)) },
                    text = {
                        Text(
                            stringResource(R.string.new_version) +
                                ": ${config.newVersionCode}\n\n${config.releaseNotes}"
                        )
                    },
                    confirmButton = {
                        Button(
                            enabled = !isDownloading,
                            onClick = {
                                coroutineScope.launch {
                                    isDownloading = true

                                    val directory = File(context.cacheDir, "update")
                                    if (!directory.exists()) directory.mkdirs()

                                    val apkFile = File(directory, "app.apk")
                                    val success = resolveApkDownloadUrls(config.apkUrl).any { url ->
                                        UpdateManager.downloadApk(url, apkFile)
                                    }

                                    if (success) {
                                        UpdateManager.installApk(context, apkFile)
                                    } else {
                                        snackbarHostState.showSnackbar(
                                            visuals = ErrorSnackbar(
                                                activity?.getString(R.string.download_error)
                                                    ?: "Ошибка при загрузке файла"
                                            )
                                        )
                                        delay(2000)
                                        updateConfig = null
                                        proceedNext()
                                    }
                                }
                            }
                        ) {
                            Text(
                                if (isDownloading) stringResource(R.string.loading)
                                else stringResource(R.string.update)
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            enabled = !isDownloading,
                            onClick = {
                                updateConfig = null
                                proceedNext()
                            }
                        ) {
                            Text(stringResource(R.string.later))
                        }
                    }
                )
            }
        }
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

private fun resolveApkDownloadUrls(apkUrl: String): List<String> {
    val updateBaseUrl = BuildConfig.UPDATE_URL.trimEnd('/')
    val apkFileName = apkUrl.substringBefore('?').substringAfterLast('/')
    val urls = mutableListOf<String>()

    if (updateBaseUrl.isNotBlank() && apkFileName.isNotBlank()) {
        urls += "$updateBaseUrl/$apkFileName"
    }

    urls += apkUrl

    return urls.distinct()
}
