package com.adrzdv.mtocp.ui.screen.monitoring.train

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.db.entity.TrainEntity
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.ui.component.AppBar
import com.adrzdv.mtocp.ui.component.dialogs.AddWorkerDialog
import com.adrzdv.mtocp.ui.component.dialogs.sys.AppIconTitleDialog
import com.adrzdv.mtocp.ui.component.newelements.AppDatePicker
import com.adrzdv.mtocp.ui.component.newelements.AppTimePicker
import com.adrzdv.mtocp.ui.component.newelements.RoundedUnborderedButton
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.snackbar.ErrorSnackbar
import com.adrzdv.mtocp.ui.navigation.Screen
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainOrderViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//TODO: Refactor to stateless pattern
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun InitDataTrainMonitoringScreen(
    trainOrderViewModel: TrainOrderViewModel,
    autocompleteViewModel: AutocompleteViewModel<TrainEntity>,
    depotAutocompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    navController: NavHostController
) {
    val state by trainOrderViewModel.orderState.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState().apply { is24hour = true }
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage by trainOrderViewModel.snackbarMessage.collectAsState()
    var showContinueDialog by remember { mutableStateOf(false) }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(
                visuals = ErrorSnackbar(message)
            )
            trainOrderViewModel.snackbarShown()
        }
    }

    Scaffold(
        containerColor = AppColors.SURFACE_COLOR.color,
        topBar = {
            AppBar(
                title = stringResource(R.string.start_revision_text),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back_32_white),
                            contentDescription = stringResource(R.string.menu_string)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (trainOrderViewModel.onSave()) {
                                showContinueDialog = true
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_save_32_white),
                            contentDescription = stringResource(R.string.save_string)
                        )
                    }
                }
            )
        },
        snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) }

    ) { innerPadding ->
        InitDataScreenContent(
            state = state,
            trainOrderViewModel = trainOrderViewModel,
            autocompleteViewModel = autocompleteViewModel,
            innerPadding = innerPadding,
            formatter = formatter
        )

        if (state.showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { trainOrderViewModel.onHidePickDate() },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val millis = datePickerState.selectedDateMillis
                            if (millis != null) {
                                val instant = Instant.ofEpochMilli(millis)
                                val zone = ZoneId.systemDefault()
                                val date = LocalDateTime.ofInstant(instant, zone)
                                trainOrderViewModel.onDateSelected(
                                    year = date.year,
                                    month = date.monthValue,
                                    day = date.dayOfMonth
                                )
                            }
                        }
                    ) {
                        Text(stringResource(R.string.ok_string))
                    }
                }
            ) {
                AppDatePicker(
                    state = datePickerState
                )
            }
        }

        if (state.showTimePicker) {
            TimePickerDialog(
                title = { Text(stringResource(R.string.choose_time)) },
                onDismissRequest = { trainOrderViewModel.onHidePickDate() },
                confirmButton = {
                    TextButton(
                        onClick = {
                            trainOrderViewModel.onTimeSelected(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                        }
                    ) {
                        Text(stringResource(R.string.ok_string))
                    }
                }
            ) {
                AppTimePicker(
                    state = timePickerState
                )
            }
        }

        if (state.showDialogs) {
            AddWorkerDialog(
                onWorkerAdd = { worker -> trainOrderViewModel.onAddPersonCrew(worker) },
                onDismiss = {
                    trainOrderViewModel.onDismissDialog()
                    depotAutocompleteViewModel.onQueryChange("")
                },
                depotAutocompleteViewModel = depotAutocompleteViewModel,
            )
        }

        if (showContinueDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                AppIconTitleDialog(
                    icon = rememberVectorPainter(Icons.Default.QuestionMark),
                    dismissButton = {
                        RoundedUnborderedButton(
                            onClick = {
                                showContinueDialog = false
                                navController.navigate(Screen.MonitoringTrainInProgress.route)
                            },
                            text = stringResource(R.string.no_string)
                        )
                    },
                    confirmButton = {
                        RoundedUnborderedButton(
                            onClick = {
                                showContinueDialog = false
                                navController.navigate(Screen.TrainSchemeEdit.route)
                            },
                            text = stringResource(R.string.yes_string)
                        )
                    }
                ) {
                    Text(
                        stringResource(R.string.ask_train_scheme),
                        style = AppTypography.bodyMedium,
                        color = AppColors.MAIN_COLOR.color
                    )
                }
            }
        }
    }
}