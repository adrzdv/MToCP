package com.adrzdv.mtocp.ui.component.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.mapper.toUI
import com.adrzdv.mtocp.ui.component.AppBar
import com.adrzdv.mtocp.ui.component.newelements.AutocompleteField
import com.adrzdv.mtocp.ui.component.newelements.ClearIcon
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.RoundedUnborderedButton
import com.adrzdv.mtocp.ui.model.statedtoui.CoachUi
import com.adrzdv.mtocp.ui.state.NewCoachState
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCoach(
    onConfirm: (CoachUi) -> Unit,
    onDismiss: () -> Unit,
    depotAutocompleteViewModel: AutocompleteViewModel,
    workerDepotAutocompleteViewModel: AutocompleteViewModel,
    appDependencies: AppDependencies
) {
    var state by remember { mutableStateOf(NewCoachState()) }
    val depotQuery by depotAutocompleteViewModel.query.collectAsState()
    val workerDepotQuery by workerDepotAutocompleteViewModel.query.collectAsState()
    val depotSuggestions by depotAutocompleteViewModel.filteredItems.collectAsState()
    val workerDepotSuggestions by workerDepotAutocompleteViewModel.filteredItems.collectAsState()
    val stringProvider = appDependencies.stringProvider

    LaunchedEffect(Unit) {
        depotAutocompleteViewModel.resetQuery()
        workerDepotAutocompleteViewModel.resetQuery()
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.SURFACE_COLOR.color),
            contentColor = AppColors.SURFACE_COLOR.color,
            shape = RectangleShape
        ) {
            Scaffold(
                containerColor = AppColors.SURFACE_COLOR.color,
                contentColor = AppColors.SURFACE_COLOR.color,
                topBar = {
                    AppBar(
                        title = stringResource(R.string.new_coach),
                        actions = {
                            IconButton(
                                onClick = {
                                    val validatedState = state.validate(stringProvider)
                                    state = validatedState
                                    if (validatedState.isSaveEnabled) {
                                        onConfirm(validatedState.toUI())
                                    }
                                }
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_save_32_white),
                                    contentDescription = stringResource(R.string.save_string),
                                    tint = AppColors.SURFACE_COLOR.color
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = onDismiss
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = stringResource(R.string.cancel),
                                    tint = AppColors.SURFACE_COLOR.color
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .background(AppColors.SURFACE_COLOR.color),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    InputTextField(
                        value = state.number,
                        onValueChange = {
                            state = state.copy(
                                number = it
                            )
                        },
                        label = stringResource(R.string.coach_number),
                        trailingIcon = {
                            if (state.number.isNotEmpty()) {
                                ClearIcon {
                                    state = state.copy(number = "")
                                }
                            }
                        },
                        isError = state.isValidationStarted && state.numberError != null,
                        errorText = if (state.isValidationStarted) state.numberError else null,
                        isEnabled = true,
                        readOnly = false
                    )
                    AutocompleteField(
                        value = state.selectedType,
                        source = PassengerCoachType.entries.map { it.passengerCoachTitle }.toList(),
                        onValueChange = {
                            state = state.copy(
                                selectedType = it
                            )
                        },
                        onValueSelected = {
                            state = state.copy(
                                selectedType = it
                            )
                        },
                        trailingIcon = null,
                        label = stringResource(R.string.coach_type),
                        isError = state.isValidationStarted && state.selectedTypeError != null,
                        enabled = true,
                        error = if (state.isValidationStarted) state.selectedTypeError else null
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Checkbox(
                                checked = if (state.isTrailing) {
                                    false
                                } else {
                                    state.isCopyDepot
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = AppColors.MAIN_COLOR.color,
                                    uncheckedColor = AppColors.MAIN_COLOR.color,
                                    checkmarkColor = AppColors.SURFACE_COLOR.color
                                ),
                                onCheckedChange = {
                                    state = state.copy(
                                        isCopyDepot = it
                                    )
                                },
                                enabled = !state.isTrailing
                            )
                            Text(
                                text = stringResource(R.string.copy_train_depot),
                                color = AppColors.MAIN_COLOR.color
                            )
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Checkbox(
                                checked = state.isTrailing,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = AppColors.MAIN_COLOR.color,
                                    uncheckedColor = AppColors.MAIN_COLOR.color,
                                    checkmarkColor = AppColors.SURFACE_COLOR.color
                                ),
                                onCheckedChange = { state = state.copy(isTrailing = it) }
                            )

                            Text(
                                text = stringResource(R.string.trailing_string),
                                color = AppColors.MAIN_COLOR.color
                            )
                        }
                    }
                    AutocompleteField(
                        value = depotQuery,
                        source = depotSuggestions,
                        onValueChange = {
                            depotAutocompleteViewModel.onQueryChange(it)
                        },
                        onValueSelected = {
                            state = state.copy(
                                selectedDepot = it
                            )
                        },
                        trailingIcon = {
                            if (depotQuery.isNotEmpty()) {
                                ClearIcon { depotAutocompleteViewModel.onQueryChange("") }
                            }
                        },
                        label = stringResource(R.string.depot_string),
                        isError = state.isValidationStarted && state.selectedDepotError != null,
                        enabled = !state.isCopyDepot || state.isTrailing,
                        error = if (state.isValidationStarted) state.selectedDepotError else null
                    )

                    InputTextField(
                        value = state.route ?: "",
                        onValueChange = {
                            state = state.copy(
                                route = it
                            )
                        },
                        trailingIcon = {
                            state.route?.let {
                                ClearIcon { state = state.copy(route = "") }
                            }
                        },
                        label = stringResource(R.string.trailing_route),
                        isError = state.isValidationStarted && state.routeError != null,
                        errorText = if (state.isValidationStarted) state.routeError else null,
                        isEnabled = state.isTrailing,
                        readOnly = false
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Checkbox(
                            checked = state.isWorkerAddSelected,
                            colors = CheckboxDefaults.colors(
                                checkedColor = AppColors.MAIN_COLOR.color,
                                uncheckedColor = AppColors.MAIN_COLOR.color,
                                checkmarkColor = AppColors.SURFACE_COLOR.color
                            ),
                            onCheckedChange = { state = state.copy(isWorkerAddSelected = it) }
                        )

                        Text(
                            text = stringResource(R.string.add_worker_data),
                            color = AppColors.MAIN_COLOR.color
                        )
                    }

                    if (state.isWorkerAddSelected) {

                        InputTextField(
                            value = state.workerId ?: "",
                            onValueChange = {
                                state = state.copy(
                                    workerId = it
                                )
                            },
                            label = stringResource(R.string.worker_id),
                            trailingIcon = {
                                if (state.workerId?.isNotEmpty() == true) {
                                    ClearIcon {
                                        state = state.copy(
                                            workerId = ""
                                        )
                                    }
                                }
                            },
                            isError = state.isValidationStarted && state.workerId != null,
                            errorText = if (state.isValidationStarted) state.workerIdError else null,
                            isEnabled = true,
                            readOnly = false
                        )

                        InputTextField(
                            value = state.workerName ?: "",
                            onValueChange = {
                                state = state.copy(
                                    workerName = it
                                )
                            },
                            label = stringResource(R.string.worker_name),
                            trailingIcon = {
                                if (state.workerName?.isNotEmpty() == true) {
                                    ClearIcon {
                                        state = state.copy(workerName = "")
                                    }
                                }
                            },
                            isError = state.isValidationStarted && state.workerNameError != null,
                            errorText = if (state.isValidationStarted) state.workerNameError else null,
                            isEnabled = true,
                            readOnly = false
                        )

                        AutocompleteField(
                            value = state.workerPosition ?: "",
                            source = WorkerTypes.entries.map { it.description }.toList(),
                            onValueChange = {
                                state = state.copy(
                                    workerPosition = it
                                )
                            },
                            onValueSelected = {
                                state = state.copy(
                                    workerPosition = it
                                )
                            },
                            trailingIcon = {
                                if (state.workerPosition?.isNotEmpty() == true) {
                                    ClearIcon {
                                        state = state.copy(
                                            workerPosition = ""
                                        )
                                    }
                                }
                            },
                            label = stringResource(R.string.worker_type),
                            isError = state.isValidationStarted && state.workerPositionError != null,
                            enabled = true,
                            error = if (state.isValidationStarted) state.workerPositionError else null
                        )

                        AutocompleteField(
                            value = workerDepotQuery,
                            source = workerDepotSuggestions,
                            onValueChange = {
                                workerDepotAutocompleteViewModel.onQueryChange(it)
                            },
                            onValueSelected = {
                                state = state.copy(
                                    workerDepot = it
                                )
                            },
                            trailingIcon = {
                                if (depotQuery.isNotEmpty()) {
                                    ClearIcon { workerDepotAutocompleteViewModel.onQueryChange("") }
                                }
                            },
                            label = stringResource(R.string.depot_string),
                            isError = state.isValidationStarted && state.workerDepotError != null,
                            enabled = true,
                            error = if (state.isValidationStarted) state.workerDepotError else null
                        )
                    }

                    RoundedUnborderedButton(
                        text = stringResource(R.string.clean_string),
                        onClick = {
                            state = state.copy(
                                number = "",
                                selectedDepot = "",
                                selectedType = "",
                                route = "",
                                isCopyDepot = true,
                                isTrailing = false,
                                numberError = "",
                                selectedTypeError = "",
                                selectedDepotError = "",
                                routeError = ""
                            )
                        }
                    )
                }
            }
        }
    }
}