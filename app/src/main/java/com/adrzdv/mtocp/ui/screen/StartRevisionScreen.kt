package com.adrzdv.mtocp.ui.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.foundation.background
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.adrzdv.mtocp.App
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.ui.component.CustomSnackbarHost
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartRevisionScreen(
    orderViewModel: OrderViewModel,
    autocompleteViewModel: AutocompleteViewModel,
    orderTypes: List<String>,
    navController: NavController,
    onBackClick: () -> Unit
) {
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

    val isInputEnabled = selectedOrderType.isNotBlank()
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        showExitDialog = true
    }

    fun generateOrder(): Boolean {
        if (orderNumber.isBlank()
            || orderRoute.isBlank()
            || dateStart.isBlank()
            || dateEnd.isBlank()
        ) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = MessageCodes.BLANK_FIELDS_ERROR.errorTitle,
                )
            }
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
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.header_start_revision),
                        style = AppTypography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            showExitDialog = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_32),
                            contentDescription = stringResource(R.string.back_text)
                        )
                    }
                })
        }, snackbarHost = {
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
                    selectedRevision = selectedOrderType,
                    onRevisionSelected = {
                        selectedOrderType = it
                        orderViewModel.setSelectedType(OrdersTypes.getFromString(it))
                        autocompleteViewModel.setOrderType(OrdersTypes.getFromString(it))
                    }
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                AutocompleteTextField(
                    query = query,
                    suggestions = suggestions,
                    onQueryChanged = { input ->
                        autocompleteViewModel.onQueryChanged(input)
                    },
                    onSuggestionSelected = { selected ->
                        orderViewModel.setObjectNumber(selected)
                    },
                    enabled = isInputEnabled
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = orderNumber,
                    textStyle = CustomTypography.bodyLarge,
                    onValueChange = { orderNumber = it },
                    label = {
                        Text(
                            text = stringResource(R.string.order_number_hint),
                            style = AppTypography.labelMedium
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.OUTLINE_GREEN.color,
                        unfocusedBorderColor = Color(0xFFCCCCCC),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                OutlinedTextField(
                    value = orderRoute,
                    textStyle = CustomTypography.bodyLarge,
                    onValueChange = { orderRoute = it },
                    label = {
                        Text(
                            stringResource(R.string.order_route_hint),
                            style = AppTypography.labelMedium
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.OUTLINE_GREEN.color,
                        unfocusedBorderColor = Color(0xFFCCCCCC),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                // Date/time start
                OutlinedTextField(
                    value = dateStart,
                    textStyle = CustomTypography.bodyLarge,
                    onValueChange = {},
                    label = {
                        Text(
                            stringResource(R.string.order_start_date_hint),
                            style = AppTypography.labelMedium
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            showDateTimePicker { selected ->
                                try {
                                    val start = format.parse(selected)
                                    val now = Calendar.getInstance()

                                    now.add(Calendar.HOUR_OF_DAY, -1)

                                    if (start != null && start.after(now.time)) {
                                        dateStart = selected
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = MessageCodes.DATE_ERROR.errorTitle
                                            )
                                        }
                                    }
                                } catch (e: ParseException) {
                                    e.printStackTrace()
                                }
                            }
                        },
                    enabled = false,
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFCCCCCC),
                        unfocusedBorderColor = Color(0xFFCCCCCC),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                // Date/time end
                OutlinedTextField(
                    value = dateEnd,
                    textStyle = CustomTypography.bodyLarge,
                    onValueChange = {},
                    label = {
                        Text(
                            stringResource(R.string.order_end_date_hint),
                            style = AppTypography.labelMedium
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            showDateTimePicker { selected ->
                                try {
                                    val start = format.parse(dateStart)
                                    val end = format.parse(selected)

                                    if (start != null && end != null) {
                                        if (end.after(start)) {
                                            dateEnd = selected
                                        } else {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = MessageCodes.DATE_ERROR.errorTitle
                                                )
                                            }
                                        }
                                    }
                                } catch (e: ParseException) {
                                    e.printStackTrace()
                                }
                            }
                        },
                    enabled = false,
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFCCCCCC),
                        unfocusedBorderColor = Color(0xFFCCCCCC),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                if (showExitDialog) {
                    ConfirmDialog(
                        title = stringResource(R.string.exit_text),
                        message = "Вы уверены, что хотите закончить?\n" +
                                "Несохраненные данные будут утеряны",
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


