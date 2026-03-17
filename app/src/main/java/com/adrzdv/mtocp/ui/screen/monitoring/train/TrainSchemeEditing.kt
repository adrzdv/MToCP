package com.adrzdv.mtocp.ui.screen.monitoring.train

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import com.adrzdv.mtocp.ui.component.AppBar
import com.adrzdv.mtocp.ui.component.dialogs.AddNewCoach
import com.adrzdv.mtocp.ui.component.dialogs.CoachSelectionDialog
import com.adrzdv.mtocp.ui.component.newelements.FloatingButton
import com.adrzdv.mtocp.ui.component.newelements.InfoBlock
import com.adrzdv.mtocp.ui.component.newelements.cards.CoachCard
import com.adrzdv.mtocp.ui.navigation.Screen
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainOrderViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TrainSchemeEditingScreen(
    appDependencies: AppDependencies,
    navController: NavHostController,
    trainOrderViewModel: TrainOrderViewModel,
    depotAutocompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    workerDepotAutocompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    companyAutocompleteViewModel: AutocompleteViewModel<CompanyWithBranch>
) {
    val state by trainOrderViewModel.orderState.collectAsState()
    var showAddCoachDialog by remember { mutableStateOf(false) }
    var showCoachTypeSelectionDialog by remember { mutableStateOf(false) }
    var selectedCoachType by remember { mutableStateOf<CoachTypes?>(null) }

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.train_scheme),
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.MonitoringTrainInProgress.route)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_save_32_white),
                            contentDescription = stringResource(R.string.save_string)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back_32_white),
                            contentDescription = stringResource(R.string.menu_string)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingButton(
                onClick = {
                    showCoachTypeSelectionDialog = true
                },
                icon = {
                    Icon(
                        painterResource(R.drawable.ic_add_itew_white),
                        contentDescription = stringResource(R.string.add_string),
                        tint = Color.Unspecified,
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                InfoBlock(
                    text = stringResource(R.string.add_car_scheme_description)
                )
            }
            items(state.coachList.values.toList()) {
                CoachCard(
                    it,
                    onDeleteClick = {
                        trainOrderViewModel.removeCoachInOrder(it.id)
                    }
                )
            }
        }
    }

    if (showCoachTypeSelectionDialog) {
        CoachSelectionDialog(
            onAddClick = { selected ->
                selectedCoachType = selected
                showCoachTypeSelectionDialog = false
                showAddCoachDialog = true
            },
            onDismiss = { showCoachTypeSelectionDialog = false }
        )
    }

    if (showAddCoachDialog && selectedCoachType != null) {
        AddNewCoach(
            selectedType = selectedCoachType!!,
            onConfirm = {
                trainOrderViewModel.addCoachInOrder(it)
                showAddCoachDialog = false
            },
            onDismiss = {
                showAddCoachDialog = false
            },
            depotAutocompleteViewModel = depotAutocompleteViewModel,
            workerDepotAutocompleteViewModel = workerDepotAutocompleteViewModel,
            companyAutocompleteViewModel = companyAutocompleteViewModel,
            appDependencies = appDependencies
        )
    }
}