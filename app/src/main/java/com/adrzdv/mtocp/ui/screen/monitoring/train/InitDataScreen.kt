package com.adrzdv.mtocp.ui.screen.monitoring.train

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.AppBar
import com.adrzdv.mtocp.ui.component.newelements.AppDatePicker
import com.adrzdv.mtocp.ui.component.newelements.AppTimePicker
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.screen.component.InitDataScreenContent
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainOrderViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun InitDataTrainMonitoringScreen(
    trainOrderViewModel: TrainOrderViewModel,
    autocompleteViewModel: AutocompleteViewModel,
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        trainOrderViewModel.createOrder()
        trainOrderViewModel.createInitialState()
    }

    val state by trainOrderViewModel.orderState.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState().apply { is24hour = true }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = AppColors.SURFACE_COLOR.color,
        topBar = {
            AppBar(
                title = {
                    Spacer(modifier = Modifier.height(0.dp))
                    Text(
                        text = stringResource(id = R.string.start_revision_text),
                        style = AppTypography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back_32_white),
                            contentDescription = stringResource(R.string.menu_string)
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
            navController = navController,
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
    }
}