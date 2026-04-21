package com.adrzdv.mtocp.ui.screen.monitoring.train.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import com.adrzdv.mtocp.domain.model.enums.DinnerCarsType
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType
import com.adrzdv.mtocp.ui.component.newelements.AppCheckBox
import com.adrzdv.mtocp.ui.component.newelements.AutocompleteField
import com.adrzdv.mtocp.ui.component.newelements.ClearIcon
import com.adrzdv.mtocp.ui.component.newelements.DropdownField
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.SquaredMediumButton
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.EditCoachViewModel

@Composable
fun EditCoachDataSheetContent(
    editCoachViewModel: EditCoachViewModel,
    depotAutoCompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    companyAutoCompleteViewModel: AutocompleteViewModel<CompanyWithBranch>,
    onClose: () -> Unit
) {

    val state by editCoachViewModel.state.collectAsState()
    val depotQuery by depotAutoCompleteViewModel.query.collectAsState()
    val depotSuggestions by depotAutoCompleteViewModel.suggestions.collectAsState()
    val companyQuery by companyAutoCompleteViewModel.query.collectAsState()
    val companySuggestions by companyAutoCompleteViewModel.suggestions.collectAsState()

    LaunchedEffect(state.depot) {
        if (!state.depot.isNullOrEmpty()) {
            depotAutoCompleteViewModel.onQueryChange(state.depot!!)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.SURFACE_COLOR.color),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.number ?: "",
            onValueChange = editCoachViewModel::onNumberChange,
            isEnabled = true,
            isError = state.isIllegalState() && state.numberError != null,
            errorText = state.numberError,
            label = stringResource(R.string.coach_number),
            trailingIcon = {
                if (state.number?.isNotEmpty() ?: false) {
                    ClearIcon {
                        editCoachViewModel.onNumberChange("")
                    }
                }
            },
            secretInput = false,
            readOnly = false,
        )
        DropdownField(
            source = if (state.globalType == CoachTypes.DINNER_CAR) DinnerCarsType.entries.map { it.description }
            else PassengerCoachType.entries.map { it.passengerCoachTitle },
            selected = state.type ?: "",
            isError = state.isIllegalState() && state.typeError != null,
            errorMessage = state.typeError,
            label = stringResource(R.string.coach_type),
            onOptionSelected = editCoachViewModel::onTypeSelected
        )
        AutocompleteField(
            value = depotQuery,
            source = depotSuggestions,
            onValueChange = depotAutoCompleteViewModel::onQueryChange,
            onValueSelected = editCoachViewModel::onDepotSelected,
            trailingIcon = {
                if (depotQuery.isNotEmpty()) {
                    ClearIcon {
                        depotAutoCompleteViewModel.onQueryChange("")
                    }
                }
            },
            label = stringResource(R.string.depot_string),
            isError = state.isIllegalState() && state.depotError != null,
            enabled = companyQuery.isEmpty(),
            error = state.depotError
        )

        if (state.globalType == CoachTypes.DINNER_CAR) {
            AutocompleteField(
                value = companyQuery,
                source = companySuggestions,
                onValueChange = companyAutoCompleteViewModel::onQueryChange,
                onValueSelected = editCoachViewModel::onCompanySelected,
                trailingIcon = {
                    if (companyQuery.isNotEmpty()) {
                        ClearIcon {
                            companyAutoCompleteViewModel.onQueryChange("")
                        }
                    }
                },
                label = stringResource(R.string.dinner_company),
                isError = state.isIllegalState() && state.companyError != null,
                enabled = depotQuery.isEmpty(),
                error = state.companyError
            )
        }

        Row(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.globalType == CoachTypes.PASSENGER_CAR) {
                AppCheckBox(
                    checked = state.isTrailing == true,
                    label = stringResource(R.string.trailing_string)
                ) {
                    editCoachViewModel.onTrailingSelected()
                }
            }

            SquaredMediumButton(
                onClick = { onClose() }, //TODO Need to validate new data
                text = stringResource(R.string.save_string),
            )
        }
    }
}