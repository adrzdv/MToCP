package com.adrzdv.mtocp.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.InnerWorkerItemCard
import com.adrzdv.mtocp.ui.component.buttons.SplitButton
import com.adrzdv.mtocp.ui.component.dialogs.AddWorkerDialog
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.InnerWorkerViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import kotlinx.coroutines.launch

@Composable
fun AddCrewScreen(
    orderViewModel: OrderViewModel,
    navController: NavController,
    depotViewModel: DepotViewModel,
    workerViewModel: InnerWorkerViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val workers = workerViewModel.workers
    var showDialog by remember { mutableStateOf(false) }
    var checkedQualityPassport by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        containerColor = AppColors.LIGHT_GRAY.color,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                contentColor = Color.White,
                containerColor = AppColors.MAIN_GREEN.color,
                onClick = {
                    orderViewModel.addQualityPassport(checkedQualityPassport)
                    if (orderViewModel.checkCrew()) {
                        navController.navigate("addCoaches")
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = MessageCodes.CREW_ERROR.errorTitle
                            )
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_save_32_white),
                        contentDescription = stringResource(R.string.save_string)
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.save_string),
                        style = AppTypography.labelLarge
                    )
                }
            )
        },
        snackbarHost = {
            CustomSnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_outline_person_edit_24),
                    tint = AppColors.MAIN_GREEN.color,
                    modifier = Modifier.size(72.dp),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = stringResource(R.string.quality_passport),
                            style = AppTypography.labelLarge
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Checkbox(
                            checked = checkedQualityPassport,
                            colors = CheckboxDefaults.colors(
                                checkedColor = AppColors.MAIN_GREEN.color,
                                checkmarkColor = AppColors.OFF_WHITE.color
                            ),
                            onCheckedChange = { checkedQualityPassport = it }
                        )
                    }
                    SplitButton(
                        actions = mapOf(
                            stringResource(R.string.add_string) to
                                    Pair(
                                        painterResource(R.drawable.ic_add_person),
                                        { showDialog = true }),
                            stringResource(R.string.clean_string) to
                                    Pair(
                                        painterResource(R.drawable.ic_clear_list),
                                        {
                                            workerViewModel.cleanWorkers()
                                            orderViewModel.clearCrew()
                                        })
                        )
                    )
                }
            }
            HorizontalDivider()
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(workers) { worker ->
                    InnerWorkerItemCard(worker = worker) {
                        workerViewModel.removeWorker(worker)
                        orderViewModel.deleteCrewWorker(worker)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddWorkerDialog(
            orderViewModel,
            depotViewModel,
            onDismiss = {
                showDialog = false
            },
            innerWorkerViewModel = workerViewModel
        )
    }
}