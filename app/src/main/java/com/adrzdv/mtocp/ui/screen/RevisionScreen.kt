package com.adrzdv.mtocp.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.ConfirmDialog
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.CustomTypography
import com.adrzdv.mtocp.ui.viewmodel.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.CoachViewModel
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevisionScreen(
    orderViewModel: OrderViewModel,
    depotViewModel: DepotViewModel,
    autocompleteViewModel: AutocompleteViewModel,
    orderTypes: List<String>,
    onBackClick: () -> Unit
) {
    val navRevisionController = rememberNavController()
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navRevisionController.currentBackStackEntryAsState()
    var showExitDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    val currentRoute =
        navRevisionController.currentBackStackEntryAsState().value?.destination?.route
    val title = when (currentRoute) {
        "startRevision" -> stringResource(R.string.header_start_revision)
        "addCrew" -> stringResource(R.string.masters_object)
        "addCoaches" -> stringResource(R.string.train_scheme)
        "monitoringProcess" -> stringResource(R.string.revision_string)
        "monitoringCoach/{coachNumber}" -> navBackStackEntry?.arguments?.getString("coachNumber")
            ?: ""

        else -> ""
    }

    Scaffold(
        containerColor = AppColors.LIGHT_GRAY.color,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = CustomTypography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (currentRoute == "monitoringProcess") {
                            showResetDialog = true
                        } else {
                            val popped = navRevisionController.popBackStack()
                            if (!popped) {
                                showExitDialog = true
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_32),
                            contentDescription = stringResource(R.string.back_text)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            showExitDialog = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home_24_white),
                            contentDescription = stringResource(R.string.back_text)
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = AppColors.MAIN_GREEN.color,
                    scrolledContainerColor = AppColors.MAIN_GREEN.color,
                    titleContentColor = AppColors.OFF_WHITE.color,
                    navigationIconContentColor = AppColors.OFF_WHITE.color,
                    actionIconContentColor = AppColors.OFF_WHITE.color
                )
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navRevisionController,
            startDestination = "startRevision",
            modifier = Modifier
                .padding(innerPadding)
                .background(color = AppColors.OFF_WHITE.color)
        ) {
            composable("startRevision") {
                StartRevisionScreen(
                    orderViewModel = orderViewModel,
                    autocompleteViewModel = autocompleteViewModel,
                    orderTypes = orderTypes,
                    navController = navRevisionController,
                    onBackClick = onBackClick
                )
            }
            composable("addCrew") {
                AddCrewScreen(
                    orderViewModel = orderViewModel,
                    navController = navRevisionController,
                    depotViewModel = depotViewModel,
                )
            }
            composable("addCoaches") {
                AddCoachScreen(
                    orderViewModel = orderViewModel,
                    depotViewModel = depotViewModel,
                    navController = navRevisionController
                )
            }
            composable("monitoringProcess") {
                MonitoringProcessScreen(
                    orderViewModel = orderViewModel,
                    navController = navRevisionController
                )
            }
            composable(
                route = "monitoringCoach/{coachNumber}",
                arguments = listOf(navArgument("coachNumber") { type = NavType.StringType })
            ) { navBackStackEntry ->
                val coachNumber =
                    navBackStackEntry.arguments?.getString("coachNumber") ?: return@composable
                MonitoringCoachScreen(
                    coachNumber = coachNumber,
                    orderViewModel = orderViewModel,
                    depotViewModel = depotViewModel,
                    navController = navRevisionController
                )
            }
        }
    }

    if (showExitDialog) {
        ConfirmDialog(
            title = stringResource(R.string.exit_text),
            message = stringResource(R.string.ask_exit_string) + "\n" +
                    stringResource(R.string.warning_unsaved_data),
            onConfirm = {
                showExitDialog = false
                onBackClick()
            },
            onDismiss = { showExitDialog = false }
        )
    }

    if (showResetDialog) {

        ConfirmDialog(
            title = stringResource(R.string.exit_text),
            message = stringResource(R.string.ask_continue_string) + "\n" +
                    stringResource(R.string.return_home_screen),
            onConfirm = {
                showResetDialog = false
                activity?.finish()
            },
            onDismiss = { showResetDialog = false }
        )

    }
}