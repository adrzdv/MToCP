package com.adrzdv.mtocp.ui.component.dialogs.newcoachcontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.domain.model.enums.DinnerCarsType
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.ui.component.newelements.AppCheckBox
import com.adrzdv.mtocp.ui.component.newelements.AutocompleteField
import com.adrzdv.mtocp.ui.component.newelements.ClearIcon
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.RoundedUnborderedButton
import com.adrzdv.mtocp.ui.state.coach.NewDinnerCoachState
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel

@Composable
fun NewDinnerCarContent(
    innerPadding: PaddingValues,
    state: NewDinnerCoachState,
    onStateChange: (NewDinnerCoachState) -> Unit,
    depotAutocompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    companyAutocompleteViewModel: AutocompleteViewModel<CompanyWithBranch>
) {
    val depotQuery by depotAutocompleteViewModel.query.collectAsState()
    val companyQuery by companyAutocompleteViewModel.query.collectAsState()
    val depotSuggestions by depotAutocompleteViewModel.suggestions.collectAsState()
    val companySuggestions by companyAutocompleteViewModel.suggestions.collectAsState()

    LaunchedEffect(state.isRentalCoach) {
        if (state.isRentalCoach) {
            depotAutocompleteViewModel.resetQuery()
        } else {
            companyAutocompleteViewModel.resetQuery()
        }
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .background(AppColors.SURFACE_COLOR.color),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(4f)
            ) {
                InputTextField(
                    value = state.number,
                    onValueChange = {
                        onStateChange(
                            state.copy(number = it)
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
                    source = DinnerCarsType.entries.map { it.description }.toList(),
                    onValueChange = {
                        onStateChange(
                            state.copy(selectedType = it)
                        )
                    },
                    onValueSelected = {
                        onStateChange(
                            state.copy(selectedType = it)
                        )
                    },
                    trailingIcon = null,
                    label = stringResource(R.string.coach_type),
                    isError = state.isValidationStarted && state.selectedTypeError != null,
                    enabled = true,
                    error = if (state.isValidationStarted) state.selectedTypeError else null
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppCheckBox(
                    state.isRentalCoach,
                    stringResource(R.string.is_rent)
                ) {
                    onStateChange(
                        state.copy(isRentalCoach = !state.isRentalCoach)
                    )
                }
            }

        }

        if (state.isRentalCoach) {
            VariativeAutocompleteField(
                state = state,
                onStateChange = {
                    onStateChange(
                        state.copy(selectedCompany = it)
                    )
                },
                value = companyQuery,
                source = companySuggestions,
                onValueChange = { companyAutocompleteViewModel.onQueryChange(it) },
                onClearClick = { companyAutocompleteViewModel.resetQuery() }
            )
        } else {
            VariativeAutocompleteField(
                state = state,
                onStateChange = {
                    onStateChange(
                        state.copy(selectedDepot = it)
                    )
                },
                value = depotQuery,
                source = depotSuggestions,
                onValueChange = { depotAutocompleteViewModel.onQueryChange(it) },
                onClearClick = { depotAutocompleteViewModel.resetQuery() }
            )
        }

        InputTextField(
            value = state.workerId ?: "",
            onValueChange = {
                onStateChange(state.copy(workerId = it))
            },
            label = stringResource(R.string.doc_id),
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
            isError = state.isValidationStarted && state.workerIdError != null,
            errorText = if (state.isValidationStarted) state.workerIdError else null,
            isEnabled = true,
            readOnly = false
        )

        InputTextField(
            value = state.workerName,
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
            value = state.workerPosition,
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
                if (state.workerPosition.isNotEmpty()) {
                    ClearIcon {
                        onStateChange(
                            state.copy(
                                workerPosition = ""
                            )
                        )
                    }
                }
            },
            label = stringResource(R.string.worker_position),
            isError = state.isValidationStarted && state.workerPositionError != null,
            enabled = true,
            error = if (state.isValidationStarted) state.workerPositionError else null
        )
        RoundedUnborderedButton(
            text = stringResource(R.string.clean_string),
            onClick = {
                onStateChange(
                    state.copy(
                        number = "",
                        selectedType = "",
                        selectedDepot = null,
                        selectedCompany = null,
                        workerId = null,
                        workerName = "",
                        workerPosition = "",
                        numberError = null,
                        selectedTypeError = null,
                        selectedDepotError = null,
                        selectedCompanyError = null,
                        workerIdError = null,
                        workerNameError = null,
                        workerPositionError = null,
                        isRentalCoach = false,
                        isValidationStarted = false
                    )
                )
            }
        )
    }
}

@Composable
private fun VariativeAutocompleteField(
    state: NewDinnerCoachState,
    onStateChange: (String) -> Unit,
    value: String,
    source: List<String>,
    onValueChange: (String) -> Unit,
    onClearClick: () -> Unit
) {
    AutocompleteField(
        value = value,
        source = source,
        onValueChange = {
            onValueChange(it)
        },
        onValueSelected = {
            onStateChange(it)
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                ClearIcon {
                    onClearClick()
                }
            }
        },
        label = stringResource(R.string.depot_string),
        isError = state.isValidationStarted && state.selectedDepotError != null,
        enabled = true,
        error = if (state.isValidationStarted) state.selectedDepotError else null
    )
}