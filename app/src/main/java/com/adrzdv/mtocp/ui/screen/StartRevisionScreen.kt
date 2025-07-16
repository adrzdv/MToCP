package com.adrzdv.mtocp.ui.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.OrdersTypes
import com.adrzdv.mtocp.ui.component.AutocompleteTextField
import com.adrzdv.mtocp.ui.component.ConfirmDialog
import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.ui.component.CustomOutlinedTextField
import com.adrzdv.mtocp.ui.component.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.ReadOnlyDatePickerField
import com.adrzdv.mtocp.ui.component.RevisionTypeDropdown
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.theme.CustomTypography
import com.adrzdv.mtocp.ui.viewmodel.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import kotlinx.coroutines.launch
import java.text.ParseException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun StartRevisionScreen(
    orderViewModel: OrderViewModel,
    autocompleteViewModel: AutocompleteViewModel,
    orderTypes: List<String>,
    navController: NavController,
    onBackClick: () -> Unit
) {
    //Exception flags
    var isOrderNumberError by remember { mutableStateOf(false) }
    var isRouteError by remember { mutableStateOf(false) }
    var isDateStartError by remember { mutableStateOf(false) }
    var isDateEndError by remember { mutableStateOf(false) }
    var isDateStartTimeError by remember { mutableStateOf(false) }
    var isDateEndTimeError by remember { mutableStateOf(false) }
    var isTypeError by remember { mutableStateOf(false) }
    var isObjectNumberError by remember { mutableStateOf(false) }
    //Other val/var
    val query by autocompleteViewModel.query.observeAsState("")
    val suggestions by autocompleteViewModel.filteredItems.observeAsState(emptyList())
    val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val localDateTimeFormater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var orderNumber by rememberSaveable { mutableStateOf(orderViewModel.orderNumber ?: "") }
    var orderRoute by rememberSaveable { mutableStateOf(orderViewModel.route ?: "") }
    var dateStart by rememberSaveable {
        mutableStateOf(
            orderViewModel.dateStart?.format(
                localDateTimeFormater
            ) ?: ""
        )
    }
    var dateEnd by rememberSaveable {
        mutableStateOf(
            orderViewModel.dateEnd?.format(
                localDateTimeFormater
            ) ?: ""
        )
    }
    var selectedOrderType by rememberSaveable {
        mutableStateOf(orderViewModel.selectedType?.subscription ?: "")
    }

    var selectedObjectNumber by rememberSaveable {
        mutableStateOf(orderViewModel.collector?.number ?: "")
    }

    val isInputEnabled = selectedOrderType.isNotBlank()
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        showExitDialog = true
    }

    fun checkStartDate(
        selectedDate: String
    ): Boolean {
        val start = format.parse(selectedDate)
        val now = Calendar.getInstance()
        now.add(Calendar.HOUR_OF_DAY, -1)
        if (start != null && start.after(now.time)) {
            return true
        }
        return false
    }

    fun checkEndDate(
        startDate: String,
        selectedEnd: String
    ): Boolean {
        return try {
            val start = format.parse(startDate)
            val end = format.parse(selectedEnd)

            if (start != null && end != null) {
                if (end.after(start)) {
                    val durationMillis = end.time - start.time
                    val durationHours = durationMillis / (1000 * 60 * 60)
                    return durationHours <= 16
                }
            }
            false
        } catch (e: ParseException) {
            false
        }
    }

    fun generateOrder(): Boolean {

        isOrderNumberError = orderNumber.isBlank()
        isRouteError = orderRoute.isBlank()
        isDateStartError = dateStart.isBlank()
        isDateEndError = dateEnd.isBlank()
        isTypeError = selectedOrderType.isEmpty()
        isObjectNumberError = selectedObjectNumber.isEmpty()

        if (isOrderNumberError
            || isRouteError
            || isDateStartError
            || isDateEndError
            || isTypeError
            || isObjectNumberError
        ) {
            return false
        }
        try {
            val start = LocalDateTime.parse(dateStart, localDateTimeFormater)
            val end = LocalDateTime.parse(dateEnd, localDateTimeFormater)
            orderViewModel.setOrderNumber(orderNumber)
            orderViewModel.setDateStart(start)
            orderViewModel.setDateEnd(end)
            orderViewModel.setRoute(orderRoute)
            orderViewModel.createOrder()
            return true

        } catch (e: Exception) {
            e.printStackTrace()
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = MessageCodes.ORDER_CREATE_ERROR.errorTitle,
                )
            }
            return false
        }
    }

    fun showDateTimePicker(onDateTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()

        DatePickerDialog(
            context,
            { _, year, month, day ->
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        val selected =
                            "%02d.%02d.%04d %02d:%02d".format(day, month + 1, year, hour, minute)
                        onDateTimeSelected(selected)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Scaffold(
        containerColor = AppColors.LIGHT_GRAY.color,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = AppColors.MAIN_GREEN.color,
                contentColor = Color.White,
                text = {
                    Text(
                        text = stringResource(R.string.save_string),
                        style = AppTypography.labelLarge
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_save_32_white),
                        contentDescription = stringResource(R.string.save_string)
                    )
                },
                onClick = {
                    if (generateOrder()) {
                        navController.navigate("addCrew")
                    }
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.order_data_hint),
                    style = CustomTypography.labelMedium
                )
                RevisionTypeDropdown(
                    revisionTypes = orderTypes,
                    isError = isTypeError,
                    errorMessage = stringResource(R.string.empty_string),
                    selectedRevision = selectedOrderType,
                    onRevisionSelected = {
                        selectedOrderType = it
                        isTypeError = false
                        orderViewModel.setSelectedType(OrdersTypes.getFromString(it))
                        autocompleteViewModel.setOrderType(OrdersTypes.getFromString(it))
                    }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AutocompleteTextField(
                    query = query,
                    suggestions = suggestions,
                    onQueryChanged = { input ->
                        autocompleteViewModel.onQueryChanged(input)
                    },
                    onSuggestionSelected = { selected ->
                        selectedObjectNumber = selected
                        orderViewModel.setObjectNumber(selected)
                        isObjectNumberError = false
                    },
                    enabled = isInputEnabled,
                    isError = isObjectNumberError,
                    errorMessage = stringResource(R.string.empty_string)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                CustomOutlinedTextField(
                    value = orderNumber,
                    onValueChange = {
                        orderNumber = it
                        isOrderNumberError = false
                    },
                    isError = isOrderNumberError,
                    errorText = stringResource(R.string.empty_string),
                    label = stringResource(R.string.order_number_hint),
                    modifier = Modifier.fillMaxWidth()
                )

                CustomOutlinedTextField(
                    value = orderRoute,
                    onValueChange = {
                        orderRoute = it
                        isRouteError = false
                    },
                    isError = isRouteError,
                    errorText = stringResource(R.string.empty_string),
                    label = stringResource(R.string.order_route_hint),
                    modifier = Modifier.fillMaxWidth()
                )

                ReadOnlyDatePickerField(
                    value = dateStart,
                    errorBlankMessage = stringResource(R.string.empty_string),
                    errorTimeMessage = MessageCodes.DATE_ERROR.errorTitle,
                    labelText = stringResource(R.string.order_start_date_hint),
                    isBlankError = isDateStartError,
                    isFormatError = isDateStartTimeError,
                    onClick = {
                        showDateTimePicker { selected ->
                            try {
                                isDateStartError = false
                                isDateStartTimeError = false
                                if (checkStartDate(selected)) {
                                    dateStart = selected
                                } else {
                                    isDateStartTimeError = true
                                }
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                ReadOnlyDatePickerField(
                    value = dateEnd,
                    errorBlankMessage = stringResource(R.string.empty_string),
                    errorTimeMessage = MessageCodes.DATE_ERROR.errorTitle,
                    labelText = stringResource(R.string.order_end_date_hint),
                    isBlankError = isDateEndError,
                    isFormatError = isDateEndTimeError,
                    onClick = {
                        showDateTimePicker { selected ->
                            try {
                                isDateEndError = false
                                isDateEndTimeError = false
                                if (checkEndDate(dateStart, selected)
                                ) {
                                    dateEnd = selected
                                } else {
                                    isDateEndTimeError = true
                                }
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                if (showExitDialog) {
                    ConfirmDialog(
                        title = stringResource(R.string.exit_text),
                        message = stringResource(R.string.ask_exit_string) + "\n" +
                                stringResource(R.string.warning_unsaved_data),
                        onConfirm = {
                            showExitDialog = false
                            onBackClick()
                        },
                        onDismiss = { showExitDialog = false }
                    )
                }
            }

        }
    }
}