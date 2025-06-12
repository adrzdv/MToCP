package com.adrzdv.mtocp.ui.screen

import ViolationCatalogScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.CompanyViewModel
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCatalogScreen(
    onBackClick: () -> Unit,
    violationViewModel: ViolationViewModel,
    revisionTypes: List<String>,
    depotViewModel: DepotViewModel,
    companyViewMode: CompanyViewModel
) {

    //Navigation controller, drawer state (opened/closed), and val for coroutine
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    //Main elements of menu
    val menuItems = listOf(
        MenuElementItem("violations", "Классификатор", painterResource(R.drawable.ic_catalog)),
        MenuElementItem(
            "depot",
            "Структурные подразделения",
            painterResource(R.drawable.ic_contract)
        ),
        MenuElementItem("company", "Перевозчики", painterResource(R.drawable.ic_train))
    )

    val currentTitle = menuItems.find { it.route == currentRoute }?.title ?: ""

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(240.dp)
            ) {
                Spacer(Modifier.height(16.dp))
                menuItems.forEach() { menuItem ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = menuItem.title,
                                style = AppTypography.labelSmall
                            )
                        },
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
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = AppColors.MAIN_GREEN.color,
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            unselectedContainerColor = Color.Transparent,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(0.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            contentColor = AppColors.LIGHT_GRAY.color,
            topBar = {
                TopAppBar(
                    title = {
                        Spacer(modifier = Modifier.height(0.dp))
                        Text(
                            text = currentTitle,
                            style = AppTypography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = stringResource(R.string.menu_string)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onBackClick) {
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
                navController = navController,
                startDestination = "violations",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("violations") {
                    ViolationCatalogScreen(
                        viewModel = violationViewModel,
                        revisionTypes = revisionTypes
                    )
                }
                composable("depot") {
                    DepotCatalogScreen(
                        viewModel = depotViewModel
                    )
                }

                composable("company") {
                    CompanyCatalogScreen(
                        viewModel = companyViewMode
                    )
                }
            }
        }
    }
}


data class MenuElementItem(
    val route: String,
    val title: String,
    val icon: Painter
)