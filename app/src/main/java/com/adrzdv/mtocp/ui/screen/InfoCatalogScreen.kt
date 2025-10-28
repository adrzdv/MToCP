package com.adrzdv.mtocp.ui.screen

import ViolationCatalogScreen
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.model.MenuElementItem
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.CompanyViewModel
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.KriCoachViewModel
import com.adrzdv.mtocp.ui.viewmodel.TrainInfoViewModel
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCatalogScreen(
    onBackClick: () -> Unit,
    revisionTypes: List<String>
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val menuItems = listOf(
        MenuElementItem(
            "violations",
            stringResource(R.string.header_catalog),
            painterResource(R.drawable.ic_outline_book_2_24)
        ),
        MenuElementItem(
            "depot",
            stringResource(R.string.header_depot),
            painterResource(R.drawable.ic_outline_home_work_24)
        ),
        MenuElementItem(
            "dinner",
            stringResource(R.string.dinner_departments),
            painterResource(R.drawable.ic_food_waiter)
        ),
        MenuElementItem(
            "company",
            stringResource(R.string.header_company),
            painterResource(R.drawable.ic_carrier)
        ),
        MenuElementItem(
            "train",
            title = stringResource(R.string.trains),
            painterResource(R.drawable.ic_outline_train_24)
        ),
        MenuElementItem(
            route = "kri",
            title = stringResource(R.string.kri),
            icon = painterResource(R.drawable.ic_wagon)
        )
    )

    val currentTitle = menuItems.find { it.route == currentRoute }?.title ?: ""
    val depotViewModel: DepotViewModel = viewModel(
        factory = ViewModelFactoryProvider.provideFactory()
    )
    val companyViewModel: CompanyViewModel = viewModel(
        factory = ViewModelFactoryProvider.provideFactory()
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(240.dp),
                drawerContainerColor = AppColors.BACKGROUND_COLOR.color
            ) {
                Spacer(Modifier.height(16.dp))
                menuItems.forEach { menuItem ->
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
                            selectedContainerColor = AppColors.MAIN_COLOR.color,
                            selectedIconColor = AppColors.SURFACE_COLOR.color,
                            selectedTextColor = AppColors.SURFACE_COLOR.color,
                            unselectedContainerColor = Color.Transparent,
                            unselectedIconColor = AppColors.MAIN_COLOR.color,
                            unselectedTextColor = AppColors.MAIN_COLOR.color
                        ),
                        shape = RoundedCornerShape(0.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            contentColor = AppColors.BACKGROUND_COLOR.color,
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
                        IconButton(onClick = {
                            depotViewModel.resetDinnerFilter()
                            companyViewModel.resetDinnerFilter()
                            onBackClick()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_home_24_white),
                                contentDescription = stringResource(R.string.back_text)
                            )
                        }
                    },
                    colors = TopAppBarColors(
                        containerColor = AppColors.MAIN_COLOR.color,
                        scrolledContainerColor = AppColors.MAIN_COLOR.color,
                        navigationIconContentColor = AppColors.SURFACE_COLOR.color,
                        titleContentColor = AppColors.SURFACE_COLOR.color,
                        actionIconContentColor = AppColors.SURFACE_COLOR.color,
                        subtitleContentColor = AppColors.BACKGROUND_COLOR.color
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
                    val violationViewModel: ViolationViewModel = viewModel(
                        factory = ViewModelFactoryProvider.provideFactory()
                    )
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
                        viewModel = companyViewModel
                    )
                }
                composable("dinner") {
                    DinnerDepotCatalogScreen(
                        viewModel = depotViewModel
                    )
                }
                composable(route = "train") {
                    val trainInfoViewModel: TrainInfoViewModel = viewModel(
                        factory = ViewModelFactoryProvider.provideFactory()
                    )
                    TrainInfoScreen(
                        viewModel = trainInfoViewModel
                    )
                }
                composable(route = "kri") {
                    val kriCoachViewModel: KriCoachViewModel = viewModel(
                        factory = ViewModelFactoryProvider.provideFactory()
                    )
                    KriCoachScreen(viewModel = kriCoachViewModel)
                }
            }
        }
    }
}