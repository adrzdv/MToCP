package com.adrzdv.mtocp.ui.component.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.ui.component.dialogs.sys.AppIconTitleDialog
import com.adrzdv.mtocp.ui.component.newelements.AutocompleteField
import com.adrzdv.mtocp.ui.component.newelements.ClearIcon
import com.adrzdv.mtocp.ui.component.newelements.DropdownField
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.RoundedUnborderedButton
import com.adrzdv.mtocp.ui.model.dto.WorkerUI
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.validation.Validator
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel


@Composable
fun AddWorkerDialog(
    onWorkerAdd: (WorkerUI) -> Unit,
    onDismiss: () -> Unit,
    depotAutocompleteViewModel: AutocompleteViewModel<DepotWithBranch>
) {
    var tabNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var selectedPosition by remember { mutableStateOf<WorkerTypes?>(null) }
    var selectedDepot by remember { mutableStateOf("") }
    val queryDepot by depotAutocompleteViewModel.query.collectAsState()
    val suggestionsDepot by depotAutocompleteViewModel.suggestions.collectAsState()
    var isSubmitClicked by remember { mutableStateOf(false) }
    val isFormValid = tabNumber.isNotBlank() &&
            (name.isNotBlank() && Validator.validateWorkerName(name)) &&
            selectedPosition != null &&
            selectedDepot.isNotBlank()

    AppIconTitleDialog(
        icon = painterResource(R.drawable.ic_add_person),
        onDismissRequest = onDismiss,
        dismissButton = {
            RoundedUnborderedButton(
                onClick = onDismiss,
                stringResource(R.string.cancel)
            )
        },
        confirmButton =
            {
                Button(
                    onClick = {
                        selectedPosition?.let { position ->
                            onWorkerAdd(
                                WorkerUI(
                                    tabNumber.toInt(),
                                    name,
                                    position.description,
                                    selectedDepot
                                )
                            )
                        }
                        onDismiss()
                    },
                    colors = ButtonDefaults
                        .buttonColors(containerColor = AppColors.MAIN_COLOR.color),
                    border = null,
                    enabled = isFormValid
                ) {
                    Text(
                        stringResource(R.string.add_string),
                        style = AppTypography.bodyMedium
                    )
                }
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InputTextField(
                value = tabNumber,
                onValueChange = {
                    tabNumber = it
                },
                isError = isSubmitClicked && tabNumber.isBlank(),
                errorText = stringResource(R.string.empty_string),
                label = stringResource(R.string.worker_id),
                trailingIcon = {
                    if (tabNumber.isNotEmpty()) {
                        ClearIcon(onClick = { tabNumber = "" })
                    }
                }
            )

            InputTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                isError = isSubmitClicked
                        && name.isBlank()
                        && !Validator.validateWorkerName(name),
                errorText = stringResource(R.string.empty_string),
                label = stringResource(R.string.worker_name),
                trailingIcon = {
                    if (name.isNotEmpty()) {
                        ClearIcon { name = "" }
                    }
                }
            )

            DropdownField(
                source = WorkerTypes.entries.map { it.description },
                selected = selectedPosition?.description ?: "",
                isError = isSubmitClicked && (selectedPosition == null),
                errorMessage = stringResource(R.string.empty_string),
                label = stringResource(R.string.worker_position),
                onOptionSelected = { selected ->
                    selectedPosition = WorkerTypes.entries.firstOrNull {
                        it.description == selected
                    }
                }
            )

            AutocompleteField(
                value = queryDepot,
                source = suggestionsDepot,
                onValueChange = { input ->
                    depotAutocompleteViewModel.onQueryChange(input)
                },
                onValueSelected = { selected ->
                    selectedDepot = selected
                },
                trailingIcon = {
                    if (queryDepot.isNotEmpty()) {
                        ClearIcon {
                            depotAutocompleteViewModel.onQueryChange("")
                            selectedDepot = ""
                        }
                    }
                },
                label = stringResource(R.string.worker_depot),
                isError = isSubmitClicked && selectedDepot.isEmpty(),
                error = stringResource(R.string.empty_string)
            )
        }
    }
}