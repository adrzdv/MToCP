package com.adrzdv.mtocp.ui.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adrzdv.mtocp.App
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.data.api.RetrofitClient
import com.adrzdv.mtocp.data.repository.AuthRepositoryImpl
import com.adrzdv.mtocp.data.repository.UserDataStorage
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.ui.screen.InfoCatalogScreen
import com.adrzdv.mtocp.ui.screen.NewRevisionScreen
import com.adrzdv.mtocp.ui.screen.RegisterScreen
import com.adrzdv.mtocp.ui.screen.RequestWebScreen
import com.adrzdv.mtocp.ui.screen.ServiceScreen
import com.adrzdv.mtocp.ui.screen.SplashScreen
import com.adrzdv.mtocp.ui.screen.StartMenuScreen
import com.adrzdv.mtocp.ui.viewmodel.AssistedViewModelFactory
import com.adrzdv.mtocp.ui.viewmodel.AuthViewModel
import com.adrzdv.mtocp.ui.viewmodel.ServiceViewModel
import com.adrzdv.mtocp.util.DirectoryHandler

@Composable
fun NavigationGraph(
    activity: Activity,
    prefs: SharedPreferences,
    hasToken: Boolean,
    version: String,
    userDataStorage: UserDataStorage,
    username: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = {
                val nextDestination = if (hasToken) {
                    Screen.MainMenu.route
                } else {
                    Screen.Register.route
                }
                navController.navigate(nextDestination) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.MainMenu.route) {
            StartMenuScreen(
                onStartRevisionClick = { navController.navigate(Screen.NewRevision.route) },
                onOpenViolationCatalogClick = { navController.navigate(Screen.Catalog.route) },
                onServiceMenuClick = { navController.navigate(Screen.Settings.route) },
                onRequestWebClick = { },
                onExitClick = { activity.finish() },
                appVersion = version
            )
        }

        composable(Screen.Register.route) { backStackEntry ->
            val registerVM: AuthViewModel = ViewModelProvider(
                backStackEntry,
                AssistedViewModelFactory {
                    AuthViewModel(
                        AuthRepositoryImpl(
                            RetrofitClient.authApi,
                            userDataStorage
                        )
                    )
                }
            )[AuthViewModel::class.java]
            RegisterScreen(navController = navController, viewModel = registerVM)
        }

        composable(Screen.Settings.route) { backStackEntry ->
            val serviceVM: ServiceViewModel = viewModel(backStackEntry)
            val context = LocalContext.current
            val filePickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val fileUri: Uri = result.data?.data ?: return@rememberLauncherForActivityResult
                    App.getImportManager().importFromJson(context, fileUri) { message ->
                        if (message == MessageCodes.SUCCESS.messageTitle) {
                            serviceVM.showMessage(message)
                        } else {
                            serviceVM.showErrorMessage(message)
                        }
                    }
                }
            }
            ServiceScreen(
                serviceVM = serviceVM,
                onCleanRepositoryClick = { onResult ->
                    val success = DirectoryHandler.cleanDirectories()
                    onResult(success)
                },
                onDeleteProfile = {
                    val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                    val userDataStorage = UserDataStorage(prefs)
                    userDataStorage.deleteToken()
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.MainMenu.route) { inclusive = true }
                    }
                },
                onLoadCatalog = {
                    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                        type = "*/*"
                        putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/json", "text/json"))
                        addCategory(Intent.CATEGORY_OPENABLE)
                    }
                    filePickerLauncher.launch(Intent.createChooser(intent, "Выберете файл:"))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Help.route) {
            //HelpScreen(navController = navController)
        }
        composable(Screen.Catalog.route) {
            InfoCatalogScreen(
                navPriorityHost = navController,
                revisionTypes = RevisionType.getListOfTypes()
            )
        }
        composable(Screen.Request.route) {
            RequestWebScreen(
                username = username,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.NewRevision.route) {
            NewRevisionScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() },
                onTrainRevisionClick = {},
                onTicketOfficeRevisionClick = { },
                onCoachRevisionClick = {})
        }

    }

}