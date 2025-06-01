package com.adrzdv.mtocp.ui.screen

import ViolationCatalogScreen
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCatalogScreen(
    onBackClick: () -> Unit,
    violationViewModel: ViolationViewModel,
    revisionTypes: List<String>,
    depotViewModel: DepotViewModel
) {

    //Navigation controller, drawer state (opened/closed), and val for coroutine
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    //Main elements of menu
    val menuItems = listOf(
        MenuElementItem("violations", "Классификатор", Icons.Default.Home),
        MenuElementItem("depot", "Предприятия", Icons.Default.Info)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))
                menuItems.forEach() { menuItem ->
                    NavigationDrawerItem(
                        label = { Text(menuItem.title) },
                        icon = { Icon(menuItem.icon, contentDescription = null) },
                        selected = navController.currentDestination?.route == menuItem.route,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(menuItem.route) {
                                    launchSingleTop = true
                                    popUpTo(0)
                                }
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { "NULL" },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Меню")
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "violations",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("violations") {
                    ViolationCatalogScreen(
                        onBackClick = onBackClick,
                        viewModel = violationViewModel,
                        revisionTypes = revisionTypes
                    )
                }
                composable("depot") {
                    DepotCatalogScreen(
                        onBackClick = onBackClick,
                        viewModel = depotViewModel
                    )
                }
            }
        }
    }
}


data class MenuElementItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)