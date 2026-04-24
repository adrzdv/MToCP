package com.adrzdv.mtocp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.ui.viewmodel.service.ViewModelLocator

@Composable
fun NavigationGraph(
    appDependencies: AppDependencies,
    appCacheRepository: CacheRepository,
    version: String
) {
    val navController = rememberNavController()
    val viewModelLocator = remember { ViewModelLocator(appDependencies, appCacheRepository) }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        trainMonitoringGraph(navController, appCacheRepository, viewModelLocator, appDependencies)

        splashDestination(navController, appDependencies)
        mainMenuDestination(navController, appDependencies, version)
        authUserDestination(navController, viewModelLocator)
        appSettingsDestination(navController, appDependencies, viewModelLocator)
        catalogsDestination(navController, viewModelLocator)
        newRevisionDestination(navController)
        requestsDestination(navController, appDependencies, viewModelLocator)


//        trainRevisionStartDestination(navController, viewModelLocator, appDependencies)
//        monitoringTrainInProgressDestination(navController, viewModelLocator, appDependencies)
//        editTrainScheme(navController, viewModelLocator)


        //Нужно подумать над целесообразностью такого экрана
        composable(Screen.Help.route) {
            //HelpScreen(navController = navController)
        }
    }
}
