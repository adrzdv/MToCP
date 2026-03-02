package com.adrzdv.mtocp.ui.screen.monitoring.train

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.ui.component.newelements.AppDatePicker
import com.adrzdv.mtocp.ui.component.newelements.AppTimePicker
import com.adrzdv.mtocp.ui.component.newelements.AutocompleteField
import com.adrzdv.mtocp.ui.component.newelements.BlancInfoBlock
import com.adrzdv.mtocp.ui.component.newelements.DatePickerReadOnlyField
import com.adrzdv.mtocp.ui.component.newelements.DropdownField
import com.adrzdv.mtocp.ui.component.newelements.InfoBlock
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.state.order.PickerField
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.model.TrainOrderViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitDataTrainMonitoringScreen(
    trainOrderViewModel: TrainOrderViewModel,
    navController: NavHostController
    //trainOrderViewModel: TrainOrderViewModel? = null
) {
    val state by trainOrderViewModel.orderState.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    trainOrderViewModel.createOrder()
    trainOrderViewModel.createInitialState()
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState().apply { is24hour = true }
    val snackbarHostState = remember { SnackbarHostState() }

//    if (trainOrderViewModel != null) {
//        val state by trainOrderViewModel.orderState.collectAsState()
//        trainOrderViewModel.createOrder()
//        trainOrderViewModel.createInitialState()
//    }

    Scaffold(
        containerColor = AppColors.SURFACE_COLOR.color,
        topBar = {
            TopAppBar(
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
                },
                colors = TopAppBarColors(
                    containerColor = AppColors.MAIN_COLOR.color,
                    scrolledContainerColor = AppColors.MAIN_COLOR.color,
                    navigationIconContentColor = AppColors.SURFACE_COLOR.color,
                    titleContentColor = AppColors.SURFACE_COLOR.color,
                    actionIconContentColor = AppColors.SURFACE_COLOR.color,
                    subtitleContentColor = AppColors.SURFACE_COLOR.color
                )
            )
        },
        snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InfoBlock(
                text = stringResource(R.string.revision_start_description)
            )
            InputTextField(
                value = state.orderNumber,
                onValueChange = { trainOrderViewModel.onNumberChange(it) },
                isError = state.numberError?.isNotEmpty() == true,
                errorText = state.numberError,
                label = stringResource(R.string.order_number_hint)
            )
            DropdownField(
                source = RevisionType.getMovableTypes(),
                selected = state.orderConditions?.revisionTypeTitle ?: "",
                isError = state.conditionsError?.isNotEmpty() == true,
                errorMessage = state.conditionsError,
                onOptionSelected = {
                    trainOrderViewModel.onConditionsChange(it)
                },
                label = stringResource(R.string.label_choose_revtype)
            )
            DatePickerReadOnlyField(
                value = state.dateStart.format(formatter),
                label = stringResource(R.string.order_start_date_hint),
                isError = state.dateStartError?.isNotEmpty() == true,
                error = state.dateStartError,
                onClick = {
                    trainOrderViewModel.onPickDate(PickerField.START_DATE)
                }
            )
            DatePickerReadOnlyField(
                value = state.dateEnd.format(formatter),
                label = stringResource(R.string.order_end_date_hint),
                isError = state.dateEndError?.isNotEmpty() == true,
                error = state.dateEndError,
                onClick = {
                    trainOrderViewModel.onPickDate(PickerField.END_DATE)
                }
            )
            AutocompleteField(
                value = "",
                source = emptyList(),
                onValueChange = {},
                onValueSelected = {},
                label = stringResource(R.string.search_train),
                isError = false,
                enabled = true,
                error = ""
            )

            BlancInfoBlock(
            )
        }
    }

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

@Preview
@Composable
fun PreviewInitDataTrainRevisionScreen() {
    //InitDataTrainMonitoringScreen()
}