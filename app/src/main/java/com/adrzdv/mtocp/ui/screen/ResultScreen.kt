package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.CustomSnackbarHost
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel

@Composable
fun ResultScreen(
    orderViewModel: OrderViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val innerNavigation = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    val navColors = NavigationBarItemColors(
        selectedIconColor = AppColors.OFF_WHITE.color,
        unselectedIconColor = AppColors.OFF_WHITE.color,
        selectedTextColor = AppColors.OFF_WHITE.color,
        unselectedTextColor = AppColors.OFF_WHITE.color,
        selectedIndicatorColor = AppColors.OUTLINE_GREEN.color,
        disabledIconColor = AppColors.LIGHT_GRAY.color,
        disabledTextColor = AppColors.LIGHT_GRAY.color
    )

    Scaffold(
        containerColor = AppColors.LIGHT_GRAY.color,
        bottomBar = {
            NavigationBar(
                containerColor = AppColors.LIGHT_GREEN.color
            ) {
                NavigationBarItem(
                    selected = currentDestination == "mainRes",
                    onClick = { innerNavigation.navigate("mainRes") },
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    label = { Text(stringResource(R.string.res_main)) },
                    colors = navColors
                )
                NavigationBarItem(
                    selected = currentDestination == "fullViolationReport",
                    onClick = { innerNavigation.navigate("fullViolationReport") },
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
                    label = { Text(stringResource(R.string.violations)) },
                    colors = navColors
                )
                NavigationBarItem(
                    selected = currentDestination == "coachRes",
                    onClick = { innerNavigation.navigate("coachRes") },
                    icon = {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(R.string.res_stat_coaches)) },
                    colors = navColors
                )
                NavigationBarItem(
                    selected = currentDestination == "additionalRes",
                    onClick = { innerNavigation.navigate("additionalRes") },
                    icon = { Icon(Icons.Default.Build, contentDescription = null) },
                    label = { Text(stringResource(R.string.additional)) },
                    colors = navColors
                )
            }
        },
        snackbarHost = {
            CustomSnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = innerNavigation,
            startDestination = "mainRes",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("mainRes") {
                ResultScreenMain(
                    orderViewModel = orderViewModel,
                    snackbarHostState = snackbarHostState
                )
            }
            composable("fullViolationReport") {
                ResultScreenFullViolationList(
                    orderViewModel = orderViewModel
                )
            }

            composable("coachRes") {
                ResultScreenCoachData(
                    orderViewModel = orderViewModel
                )
            }

            composable("additionalRes") {
                ResultScreenStatsParam(
                    orderViewModel = orderViewModel
                )
            }
        }
    }
}