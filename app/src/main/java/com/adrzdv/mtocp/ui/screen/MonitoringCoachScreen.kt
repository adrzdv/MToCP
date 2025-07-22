package com.adrzdv.mtocp.ui.screen

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.derivedStateOf
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
import com.adrzdv.mtocp.mapper.ViolationMapper
import com.adrzdv.mtocp.ui.component.CompactMenuButton
import com.adrzdv.mtocp.ui.component.CustomOutlinedTextField
import com.adrzdv.mtocp.ui.component.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.DropdownMenuField
import com.adrzdv.mtocp.ui.component.ServiceInfoBlock
import com.adrzdv.mtocp.ui.component.ViolationCard
import com.adrzdv.mtocp.ui.component.buttons.FloatingSaveButton
import com.adrzdv.mtocp.ui.component.dialogs.AddViolationToCoachDialog
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.CoachViewModel
import com.adrzdv.mtocp.ui.viewmodel.CoachViewModelFactory
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun MonitoringCoachScreen(
    coachNumber: String,
    orderViewModel: OrderViewModel,
    depotViewModel: DepotViewModel,
    navController: NavController
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coachViewModel: CoachViewModel =
        viewModel(factory = remember(coachNumber, orderViewModel, depotViewModel) {
            CoachViewModelFactory(coachNumber, orderViewModel, depotViewModel)
        })
    val violationViewModel: ViolationViewModel =
        viewModel(factory = ViewModelFactoryProvider.provideFactory())

    val coachViolations by remember {
        derivedStateOf {
            coachViewModel.violationMap.values.map {
                ViolationMapper.fromDomainToDto(it)
            }
        }
    }

    var isEmptyWorkerNumber by remember { mutableStateOf(false) }
    var isEmptyWorkerName by remember { mutableStateOf(false) }
    var isEmptyWorkerDepot by remember { mutableStateOf(false) }
    var isPatterWorkerName by remember { mutableStateOf(false) }

    var showSaveDialog by remember { mutableStateOf(false) }
    var showAddViolationDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    coachViewModel.setStartRevisionTime(LocalDateTime.now())

    BackHandler(
        enabled = true
    ) {
        navController.popBackStack()
    }

    Scaffold(
        containerColor = AppColors.LIGHT_GRAY.color,
        floatingActionButton = {
            FloatingSaveButton(onClick = {})
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
            // maybe put it on header or some design-object

            CustomOutlinedTextField(
                value = "Worker_Name",
                onValueChange = {},
                isEnabled = true,
                isError = true,
                errorText = "",
                label = "Worker_name",
                modifier = Modifier.fillMaxWidth()
            )

            CustomOutlinedTextField(
                value = "Worker_Number",
                onValueChange = {},
                isEnabled = true,
                isError = true,
                errorText = "",
                label = "Worker_Number",
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenuField(
                label = "Worker_Type",
                options = listOfNotNull(),
                selectedOption = "",
                onOptionSelected = {},
                isError = true,
                errorMessage = "",
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenuField(
                label = "Worker_Depot",
                options = listOfNotNull(),
                selectedOption = "",
                onOptionSelected = {},
                isError = true,
                errorMessage = "",
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
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

            ServiceInfoBlock(
                label = "Buttons for working with violations",
                content = {
                    Text(text = "Buttons for add violations bla-bla-bla")
                    CompactMenuButton(
                        icon = painterResource(R.drawable.ic_add_itew_white),
                        onClick = { showAddViolationDialog = true },
                        isEnabled = true
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(coachViolations) { item ->
                    ViolationCard(
                        item,
                        onChangeValueClick = {},
                        onResolveClick = {},
                        onDeleteClick = {
                            coachViewModel.deleteViolation(item.code)
                            orderViewModel.deleteViolation(coachNumber, item.code)
                        },
                        onAddTagClick = {},
                        onMakeVideoClick = {},
                        onMakePhotoClick = {})
                }
            }
        }

        if (showSaveDialog) {
            coachViewModel.setEndRevisionTime(LocalDateTime.now())
        }

        if (showAddViolationDialog) {
            AddViolationToCoachDialog(
                objectNumber = coachNumber,
                orderVM = orderViewModel,
                onConfirm = {
                    showAddViolationDialog = false
                    coachViewModel.reloadViolationsFromOrder()
                },
                onDismiss = {
                    showAddViolationDialog = false
                },
                onError = {
                    howAddViolationDialog = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(MessageCodes.DUPLICATE_ERROR.errorTitle)
                    }
                }
            )
        }
    }


}