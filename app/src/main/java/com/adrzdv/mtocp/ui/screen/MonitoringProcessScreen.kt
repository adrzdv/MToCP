package com.adrzdv.mtocp.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain
import com.adrzdv.mtocp.ui.component.CoachItemCardReadOnly
import com.adrzdv.mtocp.ui.component.CompactMenuButton
import com.adrzdv.mtocp.ui.component.ConfirmDialog
import com.adrzdv.mtocp.ui.component.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.InfoBlockWithLabel
import com.adrzdv.mtocp.ui.component.ParameterSelectionBottomSheet
import com.adrzdv.mtocp.ui.component.ServiceInfoBlock
import com.adrzdv.mtocp.ui.component.buttons.FloatingSaveButton
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.AdditionalParamViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider
import java.time.format.DateTimeFormatter

private enum class MenuItem {
    CHECK_ADD_PARAMS,
    CHECK_DINING_CAR,
    IMPORT_DATA,
    EXPORT_DATA
}

private data class MenuElementData(
    val name: MenuItem,
    val iconRes: Int,
    val onClick: () -> Unit,
    val isEnable: Boolean
)

@Composable
private fun ServiceRowBlock(
    painter: Painter,
    title: String,
    isState: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = if (isState) AppColors.DARK_GREEN.color else AppColors.MATERIAL_RED.color,
                modifier = Modifier.size(24.dp)
            )
        }
        Column {
            Text(
                text = title,
                style = AppTypography.bodyMedium
            )
        }
    }
}

@Composable
private fun InfoBlock(
    isCCTVUsing: Boolean,
    isProgressiveUsing: Boolean,
    isAdditionalParamsChecked: Boolean,
    isDinnerChecked: Boolean,
    isDinnerGoing: Boolean,
    items: List<MenuElementData>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ServiceRowBlock(
                    painter = painterResource(R.drawable.ic_person_14),
                    title = stringResource(R.string.progress_using),
                    isState = isProgressiveUsing
                )

                ServiceRowBlock(
                    painter = painterResource(R.drawable.ic_camera_14),
                    title = stringResource(R.string.cctv_using),
                    isState = isCCTVUsing
                )

                HorizontalDivider()

                ServiceRowBlock(
                    painter = painterResource(R.drawable.ic_dinner_24),
                    title = stringResource(R.string.dinner_going),
                    isState = isDinnerChecked
                )

                ServiceRowBlock(
                    painter = painterResource(R.drawable.ic_list_24_white),
                    title = stringResource(R.string.additional_params_checked),
                    isState = isAdditionalParamsChecked
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                userScrollEnabled = false
            ) {
                items(items) { bttn ->
                    CompactMenuButton(
                        onClick = bttn.onClick,
                        icon = painterResource(id = bttn.iconRes),
                        isEnabled = if (bttn.name.equals(MenuItem.CHECK_DINING_CAR)) isDinnerGoing
                        else bttn.isEnable
                    )
                }
            }
        }
    }
}

@Composable
fun MonitoringProcessScreen(
    orderViewModel: OrderViewModel,
    navController: NavController
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coaches = orderViewModel.collector!!.objectsMap.values.toList()
    var isAdditionalParamsChecked by remember { mutableStateOf(false) }
    val isDinnerChecked by remember { mutableStateOf(false) }
    val isCCTVUsing = orderViewModel.isTrainHasCamera
    val isProgressiveUsing = orderViewModel.isTrainUsingProgressive
    val train = orderViewModel.collector as? TrainDomain
    val isDinnerGoing = train!!.isDinnerCar
    var showBottomSheet by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }
    var showSaveDialog by remember { mutableStateOf(false) }
    val paramsViewModel: AdditionalParamViewModel =
        viewModel(factory = ViewModelFactoryProvider.provideFactory())

    BackHandler(
        enabled = true
    ) {
        showExitDialog = true
    }

    val buttons = listOf(
        MenuElementData(
            MenuItem.CHECK_ADD_PARAMS,
            R.drawable.ic_list_24_white,
            {
                showBottomSheet = true
            },
            true
        ),
        MenuElementData(
            MenuItem.CHECK_DINING_CAR,
            R.drawable.ic_dinner_24,
            {
                /* dinner car revision */
            },
            true
        ),
        MenuElementData(
            MenuItem.IMPORT_DATA,
            R.drawable.ic_import_24_white,
            { /* import data */ },
            true
        ),
        MenuElementData(
            MenuItem.EXPORT_DATA,
            R.drawable.ic_export_24_white,
            { /* export data */ },
            true
        )
    )

    Scaffold(
        containerColor = AppColors.LIGHT_GRAY.color,
        floatingActionButton = {
            FloatingSaveButton(
                onClick = {
                    showSaveDialog = true
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
                .padding(horizontal = 12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(6.dp)
            ) {
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                InfoBlockWithLabel(
                    stringResource(R.string.main_info_block_string),
                    listOf(
                        Pair(
                            stringResource(R.string.order_number_hint),
                            orderViewModel.orderNumber + " от "
                                    + orderViewModel.dateStart.format(formatter)
                        ),
                        Pair(
                            stringResource(R.string.object_number),
                            train.toString()
                        ),
                        Pair(
                            stringResource(R.string.worker_depot),
                            train.depot.shortName
                        )
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(6.dp)
            ) {
                ServiceInfoBlock(
                    stringResource(R.string.static_block_string),
                    content = {
                        InfoBlock(
                            isCCTVUsing = isCCTVUsing,
                            isProgressiveUsing = isProgressiveUsing,
                            isDinnerChecked = isDinnerChecked,
                            isAdditionalParamsChecked = isAdditionalParamsChecked,
                            isDinnerGoing = isDinnerGoing,
                            items = buttons
                        )
                    }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(coaches) { coach ->
                        (coach as? PassengerCar)?.let { passengerCar ->
                            CoachItemCardReadOnly(
                                coach = passengerCar,
                                onItemClick = {
                                    navController.navigate("monitoringCoach/${coach.number}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        val paramsInOrder = orderViewModel.collector?.additionalParams?.values?.toList()
        if (!paramsInOrder.isNullOrEmpty()) {
            paramsViewModel.setParams(paramsInOrder)
        } else {
            LaunchedEffect(Unit) {
                paramsViewModel.loadTrainParams()
            }
        }
        ParameterSelectionBottomSheet(
            paramsViewModel = paramsViewModel,
            onSave = {
                showBottomSheet = false
                isAdditionalParamsChecked = true
            },
            onDismiss = {
                showBottomSheet = false
            },
            callback = { params ->
                orderViewModel.collector?.additionalParams = params
            }
        )
    }

    if (showExitDialog) {
        ConfirmDialog(
            title = stringResource(R.string.exit_text),
            message = stringResource(R.string.ask_continue_string) + "\n" +
                    stringResource(R.string.return_home_screen),
            onConfirm = {
                navController.navigate("startRevision") {
                    popUpTo(0) { inclusive = true }
                }
                showExitDialog = false

            },
            onDismiss = {
                showExitDialog = false
            }
        )
    }

    if (showSaveDialog) {
        ConfirmDialog(
            title = stringResource(R.string.save_string),
            message = stringResource(R.string.ask_continue_string),
            onConfirm = {
                navController.navigate("resultScreen") {
                    popUpTo("monitoringProcess") { inclusive = true }
                }
                showSaveDialog = false
            },
            onDismiss = {
                showSaveDialog = false
            }
        )
    }
}