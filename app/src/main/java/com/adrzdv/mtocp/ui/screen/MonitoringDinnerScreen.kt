package com.adrzdv.mtocp.ui.screen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain
import com.adrzdv.mtocp.domain.usecase.DeleteViolationPhotoUseCase
import com.adrzdv.mtocp.mapper.ViolationMapper
import com.adrzdv.mtocp.ui.component.CustomOutlinedTextField
import com.adrzdv.mtocp.ui.component.DropdownMenuField
import com.adrzdv.mtocp.ui.component.ServiceInfoBlock
import com.adrzdv.mtocp.ui.component.buttons.FloatingSaveButton
import com.adrzdv.mtocp.ui.component.dialogs.AddTagDialog
import com.adrzdv.mtocp.ui.component.dialogs.AddViolationToCoachDialog
import com.adrzdv.mtocp.ui.component.dialogs.ChangeAmountDialog
import com.adrzdv.mtocp.ui.component.newelements.cards.ViolationCard
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.snackbar.ErrorSnackbar
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.service.AssistedViewModelFactory
import com.adrzdv.mtocp.ui.viewmodel.model.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.DinnerCoachViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.OrderViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun MonitoringDinnerScreen(
    orderViewModel: OrderViewModel,
    depotViewModel: DepotViewModel,
    navController: NavController
) {
    val dinnerCar = (orderViewModel.collector as? TrainDomain)?.dinnerCar
    val dinnerViewModel: DinnerCoachViewModel = viewModel(
        factory = AssistedViewModelFactory {
            DinnerCoachViewModel(
                initDinner = dinnerCar as DinnerCar,
                onSave = { updated ->
                    orderViewModel.updateRevisionObject(updated)
                    navController.popBackStack()
                },
                deleteViolationPhotoUseCase = DeleteViolationPhotoUseCase()
            )
        }
    )

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        dinnerViewModel.onCameraResult(result)
    }
    val snackbarMessage by dinnerViewModel.snackbarMessage.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val state by dinnerViewModel.state
    var selectedViolationCode by remember { mutableStateOf<Int?>(null) }
    var showAddViolationDialog by remember { mutableStateOf(false) }
    var showAddAmountDialog by remember { mutableStateOf(false) }
    var showAddTagDialog by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val emptyString = stringResource(R.string.empty_string)

    LaunchedEffect(Unit) {
        if (dinnerCar?.revisionDateStart == null) {
            dinnerCar?.revisionDateStart = LocalDateTime.now()
        }
        depotViewModel.resetDinnerFilter()
    }
    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            dinnerViewModel.snackbarShown()
        }
    }

    BackHandler(
        enabled = true
    ) {
        navController.popBackStack()
    }

    Scaffold(
        containerColor = AppColors.LIGHT_GRAY.color,
        floatingActionButton = {
            FloatingSaveButton(
                onClick = {
                    dinnerViewModel.onSave()
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
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ServiceInfoBlock(
                label = stringResource(R.string.crew),
                content = {
                    CustomOutlinedTextField(
                        value = state.idWorker ?: "",
                        onValueChange = {
                            dinnerViewModel.onIdChange(it, emptyString)
                        },
                        isEnabled = true,
                        isError = state.idError != null,
                        errorText = state.idError ?: "",
                        label = stringResource(R.string.worker_id),
                        modifier = Modifier.fillMaxWidth()
                    )
                    CustomOutlinedTextField(
                        value = state.nameWorker ?: "",
                        onValueChange = {
                            dinnerViewModel.onNameChange(it, emptyString)
                        },
                        isEnabled = true,
                        isError = state.nameError != null,
                        errorText = state.nameError ?: "",
                        label = stringResource(R.string.worker_name),
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenuField(
                        label = stringResource(R.string.worker_type),
                        options = WorkerTypes.values().map { it.description },
                        selectedOption = WorkerTypes.entries
                            .firstOrNull { it.description == state.typeWorker }?.description ?: "",
                        onOptionSelected = {
                            dinnerViewModel.onTypeChange(it, emptyString)
                        },
                        isError = state.typeError != null,
                        errorMessage = state.typeError ?: "",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = {
                                dinnerViewModel.setInputsEmpty()
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = AppColors.MAIN_GREEN.color,
                                containerColor = Color.Transparent,
                                disabledContentColor = AppColors.MAIN_GREEN.color.copy(alpha = 0.38f)
                            ),
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_clear_list),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(R.string.clean_string),
                                style = AppTypography.labelLarge,
                                color = Color.Black
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            ServiceInfoBlock(
                label = stringResource(R.string.violations),
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = {
                                showAddViolationDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = AppColors.OFF_WHITE.color,
                                containerColor = AppColors.MAIN_GREEN.color
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_itew_white),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(R.string.add_string),
                                style = AppTypography.labelLarge,
                                color = AppColors.OFF_WHITE.color
                            )
                        }
                        TextButton(
                            onClick = {
                                showBottomSheet = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = AppColors.OFF_WHITE.color,
                                containerColor = AppColors.MAIN_GREEN.color
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_itew_white),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(R.string.addition_params_string),
                                style = AppTypography.labelLarge,
                                color = AppColors.OFF_WHITE.color
                            )
                        }
                        TextButton(
                            onClick = {
                                dinnerViewModel.cleanViolations(orderViewModel.orderNumber)
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = AppColors.OFF_WHITE.color,
                                containerColor = AppColors.MAIN_GREEN.color
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete_24_white),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(R.string.clear_text),
                                style = AppTypography.labelLarge,
                                color = AppColors.OFF_WHITE.color
                            )
                        }
                    }
                }
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(dinnerViewModel.getDisplayViolations()) { item ->
                    ViolationCard(
                        ViolationMapper.fromDomainToDto(item),
                        onChangeValueClick = {
                            selectedViolationCode = item.code
                            showAddAmountDialog = true
                        },
                        onResolveClick = {
                            dinnerViewModel.toggleResolvedViolation(item.code)
                        },
                        onDeleteClick = {
                            dinnerViewModel.deleteViolation(
                                item.code,
                                orderViewModel.orderNumber
                            )

                        },
                        onAddTagClick = {
                            selectedViolationCode = item.code
                            showAddTagDialog = true
                        },
                        onMakeVideoClick = {},
                        onMakePhotoClick = {
                            val intent = dinnerViewModel.buildCameraIntent(context).apply {
                                putExtra("order", orderViewModel.orderNumber)
                                putExtra("coach", dinnerCar?.number)
                                putExtra("violation", item.code)
                                putExtra("violationShort", item.shortName)
                            }
                            launcher.launch(intent)
                        }
                    )
                }
            }
        }

        //temporary solution: in future fix it!!! need to chose existing strings from db (for exmpl)
        if (showAddTagDialog) {
            AddTagDialog(
                onConfirm = { tag ->
                    dinnerViewModel.addTagToViolation(selectedViolationCode!!, tag)
                    showAddTagDialog = false
                },
                onDismiss = { showAddTagDialog = false }
            )
        }
        if (showAddAmountDialog) {
            ChangeAmountDialog(
                onConfirm = { amount ->
                    dinnerViewModel.updateViolationValue(selectedViolationCode!!, amount)
                    showAddAmountDialog = false
                },
                onDismiss = {
                    showAddAmountDialog = false
                }
            )
        }
        if (showAddViolationDialog) {
            AddViolationToCoachDialog(
                revisionType = orderViewModel.revisionType,
                onConfirm = { violation ->
                    dinnerViewModel.addViolation(violation)
                },
                onDismiss = {
                    showAddViolationDialog = false
                },
                onError = {
                    showAddViolationDialog = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            visuals = ErrorSnackbar(MessageCodes.DUPLICATE_ERROR.messageTitle)
                        )
                    }
                }
            )
        }

//        if (showBottomSheet) {
//            val paramsForCoach = dinnerCar?.additionalParams?.values?.toList()
//            if (!paramsForCoach.isNullOrEmpty()) {
//                paramsViewModel.setParams(paramsForCoach)
//            } else {
//                LaunchedEffect(Unit) {
//                    paramsViewModel.loadCoachParams()
//                }
//            }
//            ParameterSelectionBottomSheet(
//                paramsViewModel = paramsViewModel,
//                onSave = {
//                    showBottomSheet = false
//                },
//                onDismiss = {
//                    showBottomSheet = false
//                }, callback = { params ->
//                    passengerCoachViewModel.addAdditionalParams(params)
//                }
//            )
//        }
    }

}