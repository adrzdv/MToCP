package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.ConfirmDialog
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.CustomTypography
import com.adrzdv.mtocp.ui.viewmodel.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Spacer(modifier = Modifier.height(0.dp))
                    Text(
                        text = stringResource(R.string.revision_string),
                        style = CustomTypography.titleLarge
                    )
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
                }
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
        }
    }

    if (showExitDialog) {
        ConfirmDialog(
            title = "Выход",
            message = "Вы уверены, что хотите закончить?\n" +
                    "Несохраненные данные будут утеряны",
            onConfirm = {
                showExitDialog = false
                onBackClick()
            },
            onDismiss = { showExitDialog = false }
        )
    }
}