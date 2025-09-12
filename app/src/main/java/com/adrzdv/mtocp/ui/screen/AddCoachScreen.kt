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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain
import com.adrzdv.mtocp.ui.component.CoachItemCard
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.InfoBlockWithLabel
import com.adrzdv.mtocp.ui.component.buttons.SplitButton
import com.adrzdv.mtocp.ui.component.dialogs.AddCoachDialog
import com.adrzdv.mtocp.ui.component.dialogs.AddDinnerCarDialog
import com.adrzdv.mtocp.ui.component.dialogs.ConfirmDialog
import com.adrzdv.mtocp.ui.component.snackbar.ErrorSnackbar
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.RevisionObjectViewModel
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddCoachScreen(
    orderViewModel: OrderViewModel,
    navController: NavController,
    depotViewModel: DepotViewModel,
    coachViewModel: RevisionObjectViewModel<PassengerCar> = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }
    var showAddDinnerDialog by remember { mutableStateOf(false) }
    val coaches = coachViewModel.revObjects
    val train = orderViewModel.collector as? TrainDomain
    val trainScheme by orderViewModel.trainScheme.observeAsState("-")
    var isHasDinnerCar by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var showWarningDialog by remember { mutableStateOf(false) }

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
                    showWarningDialog = true
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
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_outline_train_24),
                    tint = AppColors.MAIN_GREEN.color,
                    modifier = Modifier.size(72.dp),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.weight(1f))
                SplitButton(
                    actions = mapOf(
                        stringResource(R.string.add_string) to
                                Pair(
                                    painterResource(R.drawable.ic_add_itew_white),
                                    { showDialog = true }),
                        stringResource(R.string.dinner_add) to
                                Pair(
                                    painterResource(R.drawable.ic_dinner_24),
                                    { showAddDinnerDialog = true }),
                        stringResource(R.string.clear_text) to
                                Pair(
                                    painterResource(R.drawable.ic_clear_list),
                                    {
                                        if (isHasDinnerCar) {
                                            orderViewModel.removeDinnerCar()
                                            orderViewModel.toggleDinnerCar(false)
                                            isHasDinnerCar = false
                                        }
                                        orderViewModel.clearRevisionObjects()
                                        coachViewModel.cleanMap()
                                        orderViewModel.updateTrainScheme()
                                    })
                    ),
                )
            }



            if (train != null) {
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val formattedDate = orderViewModel.dateEnd.format(formatter)
                val train = orderViewModel.collector as TrainDomain

                val propList =
                    listOf(
                        stringResource(R.string.order_text)
                                to "${orderViewModel.orderNumber} от $formattedDate",
                        stringResource(R.string.route) to (orderViewModel.route ?: "-"),
                        stringResource(R.string.object_data) to "${train.number} ${train.route ?: ""}",
                        stringResource(R.string.scheme) to (trainScheme ?: "-")
                    )

                InfoBlockWithLabel(
                    stringResource(R.string.info),
                    propList
                )

            }

            HorizontalDivider()

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(coaches) { coach ->
                    CoachItemCard(coach = coach) {
                        coachViewModel.removeRevObject(coach)
                        orderViewModel.deleteRevisionObject(coach)
                        orderViewModel.updateTrainScheme()
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddCoachDialog(
            orderViewModel,
            depotViewModel,
            onDismiss = {
                showDialog = false
            },
            coachViewModel
        )
    }

    if (showAddDinnerDialog) {
        AddDinnerCarDialog(
            orderViewModel,
            depotViewModel,
            onDismiss = {
                showAddDinnerDialog = false
                isHasDinnerCar = false
            },
            onConfirm = {
                showAddDinnerDialog = false
                isHasDinnerCar = true
            }
        )
    }

    if (showWarningDialog) {

        ConfirmDialog(
            title = stringResource(R.string.save_string),
            message = stringResource(R.string.ask_continue_string) + "\n" +
                    stringResource(R.string.warning_unchange_data),
            onConfirm = {
                if (orderViewModel.collector?.objectsMap?.isEmpty() == true
                ) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            visuals = ErrorSnackbar(MessageCodes.EMPTY_COUNT.messageTitle)
                        )
                    }
                    showWarningDialog = false
                } else {
                    showWarningDialog = false
                    navController.navigate("monitoringProcess")
                }

            },
            onDismiss = { showWarningDialog = false }
        )

    }
}