package com.adrzdv.mtocp.ui.screen.monitoring.train

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.ui.component.buttons.SplitButton
import com.adrzdv.mtocp.ui.component.newelements.AppDatePicker
import com.adrzdv.mtocp.ui.component.newelements.AppTimePicker
import com.adrzdv.mtocp.ui.component.newelements.AutocompleteField
import com.adrzdv.mtocp.ui.component.newelements.BlancInfoBlock
import com.adrzdv.mtocp.ui.component.newelements.DatePickerReadOnlyField
import com.adrzdv.mtocp.ui.component.newelements.DropdownField
import com.adrzdv.mtocp.ui.component.newelements.InfoBlock
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.cards.WorkerCard
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.state.order.PickerField
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
    //trainOrderViewModel: TrainOrderViewModel? = null
) {
    val state by trainOrderViewModel.orderState.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    trainOrderViewModel.createOrder()
    trainOrderViewModel.createInitialState()
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState().apply { is24hour = true }
    val snackbarHostState = remember { SnackbarHostState() }
    val query by autocompleteViewModel.query.collectAsState()
    val suggestions by autocompleteViewModel.filteredItems.collectAsState()


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
                value = query,
                source = suggestions,
                onValueChange = { input ->
                    autocompleteViewModel.onQueryChange(input)
                },
                onValueSelected = { selected ->
                    trainOrderViewModel.onTrainSelected(selected)
                },
                label = stringResource(R.string.search_train),
                isError = !state.emptyTrainError.isNullOrEmpty(),
                enabled = true,
                error = state.emptyTrainError
            )

            InputTextField(
                value = state.route,
                onValueChange = { trainOrderViewModel.onOrderRouteChange(it) },
                isError = state.routeError?.isNotEmpty() == true,
                errorText = state.routeError,
                label = stringResource(R.string.order_route)
            )

            BlancInfoBlock {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.quality_passport),
                        color = AppColors.MAIN_COLOR.color,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )
                    Checkbox(
                        checked = state.isQualityPassport,
                        colors = CheckboxDefaults.colors(
                            checkedColor = AppColors.MAIN_COLOR.color,
                            uncheckedColor = AppColors.MAIN_COLOR.color,
                            checkmarkColor = AppColors.SURFACE_COLOR.color
                        ),
                        onCheckedChange = { trainOrderViewModel.onQualityPassportChange(it) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.train_crew),
                        color = AppColors.MAIN_COLOR.color,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )
                    SplitButton(
                        actions = mapOf(
                            stringResource(R.string.add_string) to Pair(
                                painterResource(R.drawable.ic_add_person),
                                { }
                            ),
                            stringResource(R.string.clean_string) to Pair(
                                painterResource(R.drawable.ic_clear_list),
                                { }
                            )
                        )
                    )
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.crewList.values.toList()) { worker ->
                        WorkerCard(
                            worker = worker,
                            onDeleteClick = { }
                        )
                    }
                }
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
}