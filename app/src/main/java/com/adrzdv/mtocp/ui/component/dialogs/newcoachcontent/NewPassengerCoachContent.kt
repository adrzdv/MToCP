package com.adrzdv.mtocp.ui.component.dialogs.newcoachcontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.ui.component.newelements.AutocompleteField
import com.adrzdv.mtocp.ui.component.newelements.ClearIcon
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.RoundedUnborderedButton
import com.adrzdv.mtocp.ui.state.coach.NewPassengerCoachState
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel

@Composable
fun NewPassengerCoachContent(
    innerPadding: PaddingValues,
    state: NewPassengerCoachState,
    onStateChange: (NewPassengerCoachState) -> Unit,
    workerDepotAutocompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    depotAutocompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
) {
    val depotQuery by depotAutocompleteViewModel.query.collectAsState()
    val workerDepotQuery by workerDepotAutocompleteViewModel.query.collectAsState()
    val depotSuggestions by depotAutocompleteViewModel.suggestions.collectAsState()
    val workerDepotSuggestions by workerDepotAutocompleteViewModel.suggestions.collectAsState()

    LaunchedEffect(Unit) {
        workerDepotAutocompleteViewModel.resetQuery()
        depotAutocompleteViewModel.resetQuery()
    }

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
                onStateChange(
                    state.copy(
                        number = it
                    )
                )
            },
            label = stringResource(R.string.coach_number),
            trailingIcon = {
                if (state.number.isNotEmpty()) {
                    ClearIcon {
                        onStateChange(
                            state.copy(number = "")
                        )
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
                onStateChange(
                    state.copy(
                        selectedType = it
                    )
                )
            },
            onValueSelected = {
                onStateChange(
                    state.copy(
                        selectedType = it
                    )
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
                        onStateChange(
                            state.copy(
                                isCopyDepot = it
                            )
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
                    onCheckedChange = {
                        onStateChange(
                            state.copy(
                                isTrailing = it
                            )
                        )
                    }
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
                onStateChange(
                    state.copy(
                        selectedDepot = it
                    )
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
                onStateChange(
                    state.copy(
                        route = it
                    )
                )
            },
            trailingIcon = {
                state.route?.let {
                    ClearIcon {
                        onStateChange(
                            state.copy(route = "")
                        )
                    }
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
                onCheckedChange = { onStateChange(state.copy(isWorkerAddSelected = it)) }
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
                    onStateChange(
                        state.copy(
                            workerId = it
                        )
                    )
                },
                label = stringResource(R.string.worker_id),
                trailingIcon = {
                    if (state.workerId?.isNotEmpty() == true) {
                        ClearIcon {
                            onStateChange(
                                state.copy(
                                    workerId = ""
                                )
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
                    onStateChange(
                        state.copy(
                            workerName = it
                        )

                    )
                },
                label = stringResource(R.string.worker_name),
                trailingIcon = {
                    if (state.workerName?.isNotEmpty() == true) {
                        ClearIcon {
                            onStateChange(
                                state.copy(workerName = "")
                            )
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
                    onStateChange(
                        state.copy(
                            workerPosition = it
                        )
                    )
                },
                onValueSelected = {
                    onStateChange(
                        state.copy(
                            workerPosition = it
                        )
                    )
                },
                trailingIcon = {
                    if (state.workerPosition?.isNotEmpty() == true) {
                        ClearIcon {
                            onStateChange(
                                state.copy(
                                    workerPosition = ""
                                )
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
                    onStateChange(
                        state.copy(
                            workerDepot = it
                        )
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
                onStateChange(
                    state.copy(
                        number = "",
                        selectedDepot = null,
                        selectedType = "",
                        route = null,
                        isCopyDepot = true,
                        isTrailing = false,
                        numberError = null,
                        selectedTypeError = null,
                        selectedDepotError = null,
                        routeError = null
                    )
                )
            }
        )
    }
}