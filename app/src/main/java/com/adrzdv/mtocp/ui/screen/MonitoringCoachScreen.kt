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
import com.adrzdv.mtocp.App
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.usecase.DeleteViolationPhotoUseCase
import com.adrzdv.mtocp.domain.usecase.GetDepotByNameUseCase
import com.adrzdv.mtocp.mapper.ViolationMapper
import com.adrzdv.mtocp.ui.component.CustomOutlinedTextField
import com.adrzdv.mtocp.ui.component.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.DropdownMenuField
import com.adrzdv.mtocp.ui.component.ParameterSelectionBottomSheet
import com.adrzdv.mtocp.ui.component.ServiceInfoBlock
import com.adrzdv.mtocp.ui.component.ViolationCard
import com.adrzdv.mtocp.ui.component.buttons.FloatingSaveButton
import com.adrzdv.mtocp.ui.component.dialogs.AddTagDialog
import com.adrzdv.mtocp.ui.component.dialogs.AddViolationToCoachDialog
import com.adrzdv.mtocp.ui.component.dialogs.ChangeAmountDialog
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.AdditionalParamViewModel
import com.adrzdv.mtocp.ui.viewmodel.AssistedViewModelFactory
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.PassengerCoachViewModel
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun MonitoringCoachScreen(
    coachNumber: String,
    orderViewModel: OrderViewModel,
    depotViewModel: DepotViewModel,
    navController: NavController
) {
    val coach = orderViewModel.collector?.objectsMap?.get(coachNumber) as? PassengerCar
    val passengerCoachViewModel: PassengerCoachViewModel = viewModel(
        factory = AssistedViewModelFactory {
            PassengerCoachViewModel(
                initCoach = coach as PassengerCar,
                onSaveCallback = { updated ->
                    orderViewModel.updateRevisionObject(updated)
                    navController.popBackStack()
                },
                getDepotByNameUseCase = GetDepotByNameUseCase(App.getDepotRepository()),
                deleteViolationPhotoUseCase = DeleteViolationPhotoUseCase()
            )
        }
    )
    val paramsViewModel: AdditionalParamViewModel =
        viewModel(factory = ViewModelFactoryProvider.provideFactory())

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        passengerCoachViewModel.onCameraResult(result)
    }
    val snackbarMessage by passengerCoachViewModel.snackbarMessage.collectAsState()

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val state by passengerCoachViewModel.state

    var selectedViolationCode by remember { mutableStateOf<Int?>(null) }

    var showAddViolationDialog by remember { mutableStateOf(false) }
    var showAddAmountDialog by remember { mutableStateOf(false) }
    var showAddTagDialog by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

    val emptyString = stringResource(R.string.empty_string)

    LaunchedEffect(Unit) {
        if (coach?.revisionDateStart == null) {
            coach?.revisionDateStart = LocalDateTime.now()
        }
        depotViewModel.resetDinnerFilter()
    }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            passengerCoachViewModel.snackbarShown()
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
                    passengerCoachViewModel.onSave()
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
                            passengerCoachViewModel.onIdChange(it, emptyString)
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
                            passengerCoachViewModel.onNameChange(it, emptyString)
                        },
                        isEnabled = true,
                        isError = state.nameError != null,
                        errorText = state.nameError ?: "",
                        label = stringResource(R.string.worker_name),
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenuField(
                        label = stringResource(R.string.worker_type),
                        options = WorkerTypes.entries.map { it.description },
                        selectedOption = WorkerTypes.entries
                            .firstOrNull { it.description == state.typeWorker }?.description ?: "",
                        onOptionSelected = {
                            passengerCoachViewModel.onTypeChange(it, emptyString)
                        },
                        isError = state.typeError != null,
                        errorMessage = state.typeError ?: "",
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropdownMenuField(
                        label = stringResource(R.string.worker_depot),
                        options = depotViewModel.filteredDepots.value?.map { it.name }
                            ?: emptyList(),
                        selectedOption = state.depotWorker ?: "",
                        onOptionSelected = {
                            passengerCoachViewModel.onDepotChange(it, emptyString)
                        },
                        isError = state.depotError != null,
                        errorMessage = state.depotError ?: "",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = {
                                passengerCoachViewModel.setInputsEmpty()
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
                                passengerCoachViewModel.cleanViolations(orderViewModel.orderNumber)
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
                items(passengerCoachViewModel.getDisplayViolations()) { item ->
                    ViolationCard(
                        ViolationMapper.fromDomainToDto(item),
                        onChangeValueClick = {
                            selectedViolationCode = item.code
                            showAddAmountDialog = true
                        },
                        onResolveClick = {
                            passengerCoachViewModel.toggleResolvedViolation(item.code)
                        },
                        onDeleteClick = {
                            passengerCoachViewModel.deleteViolation(
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
                            val intent = passengerCoachViewModel.buildCameraIntent(context).apply {
                                putExtra("order", orderViewModel.orderNumber)
                                putExtra("coach", coachNumber)
                                putExtra("violation", item.code)
                                putExtra("violationShort", item.shortName)
                            }
                            launcher.launch(intent)
                        }
                    )
                }
            }
        }

        //FIXME: need to chose existing strings from db (for exmpl)
        if (showAddTagDialog) {
            AddTagDialog(
                onConfirm = { tag ->
                    passengerCoachViewModel.addTagToViolation(selectedViolationCode!!, tag)
                    showAddTagDialog = false
                },
                onDismiss = { showAddTagDialog = false }
            )
        }

        if (showAddAmountDialog) {
            ChangeAmountDialog(
                onConfirm = { amount ->
                    passengerCoachViewModel.updateViolationValue(selectedViolationCode!!, amount)
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
                    passengerCoachViewModel.addViolation(violation)
                },
                onDismiss = {
                    showAddViolationDialog = false
                },
                onError = {
                    showAddViolationDialog = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(MessageCodes.DUPLICATE_ERROR.errorTitle)
                    }
                }
            )
        }

        if (showBottomSheet) {
            val paramsForCoach = coach?.additionalParams?.values?.toList()
            if (!paramsForCoach.isNullOrEmpty()) {
                paramsViewModel.setParams(paramsForCoach)
            } else {
                LaunchedEffect(Unit) {
                    paramsViewModel.loadCoachParams()
                }
            }
            ParameterSelectionBottomSheet(
                paramsViewModel = paramsViewModel,
                onSave = {
                    showBottomSheet = false
                },
                onDismiss = {
                    showBottomSheet = false
                },
                callback = { params ->
                    passengerCoachViewModel.addAdditionalParams(params)
                }
            )
        }
    }
}