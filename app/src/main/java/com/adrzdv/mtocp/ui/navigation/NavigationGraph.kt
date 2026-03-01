package com.adrzdv.mtocp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.ui.viewmodel.service.ViewModelLocator

@Composable
fun NavigationGraph(
    appDependencies: AppDependencies,
    version: String
) {
    val navController = rememberNavController()
    val viewModelLocator = remember { ViewModelLocator(appDependencies) }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        splashDestination(navController, appDependencies)
        mainMenuDestination(navController, appDependencies, version)
        authUserDestination(navController, viewModelLocator)
        appSettingsDestination(navController, appDependencies)
        catalogsDestination(navController, viewModelLocator)
        newRevisionDestination(navController)
        requestsDestination(navController, appDependencies)
        trainRevisionStartDestination(navController, viewModelLocator, appDependencies)

        //Нужно подумать над целесообразностью такого экрана
        composable(Screen.Help.route) {
            //HelpScreen(navController = navController)
        }
    }
}