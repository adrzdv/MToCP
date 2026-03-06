package com.adrzdv.mtocp.ui.component.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.ui.component.newelements.AutocompleteField
import com.adrzdv.mtocp.ui.component.newelements.DropdownField
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.RoundedUnborderedButton
import com.adrzdv.mtocp.ui.model.statedtoui.WorkerUI
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.validation.Validator
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel

@Composable
fun AddWorkerDialog(
    onWorkerAdd: (WorkerUI) -> Unit,
    onDismiss: () -> Unit,
    depotAutocompleteViewModel: AutocompleteViewModel
) {
    var tabNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var selectedPosition by remember { mutableStateOf<WorkerTypes?>(null) }
    var selectedDepot by remember { mutableStateOf("") }
    val queryDepot by depotAutocompleteViewModel.query.collectAsState()
    val suggestionsDepot by depotAutocompleteViewModel.filteredItems.collectAsState()
    var isSubmitClicked by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppColors.SURFACE_COLOR.color,
        confirmButton = {
            Button(
                onClick = {
                    isError = listOf(tabNumber, name, selectedDepot).any { it.isEmpty() }
                            || selectedPosition == null

                    isSubmitClicked = true
                    if (!isError) {
                        onWorkerAdd(
                            WorkerUI(
                                tabNumber.toInt(),
                                name,
                                selectedPosition!!.description,
                                selectedDepot
                            )
                        )
                        onDismiss()
                    }
                },
                colors = ButtonDefaults
                    .buttonColors(containerColor = AppColors.MAIN_COLOR.color),
                border = null,
                enabled = !isError
            ) {
                Text(
                    stringResource(R.string.add_string),
                    style = AppTypography.bodyMedium
                )
            }
        },
        dismissButton = {
            RoundedUnborderedButton(
                onClick = onDismiss,
                stringResource(R.string.cancel)
            )
        },
        title = {
            Text(
                text = stringResource(R.string.new_worker),
                style = AppTypography.titleMedium
            )
        },
        text = {
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
                        isError = false
                    },
                    isError = isSubmitClicked && tabNumber.isBlank(),
                    errorText = stringResource(R.string.empty_string),
                    label = stringResource(R.string.worker_id),
                    trailingIcon = {
                        if (tabNumber.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    tabNumber = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(R.string.clear_text)
                                )
                            }
                        }
                    }
                )

                InputTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        isError = false
                    },
                    isError = isSubmitClicked
                            && name.isNotBlank()
                            && !Validator.validateWorkerName(name),
                    errorText = stringResource(R.string.empty_string),
                    label = stringResource(R.string.worker_name),
                    trailingIcon = {
                        if (name.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    name = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(R.string.clear_text)
                                )
                            }
                        }
                    }
                )

                DropdownField(
                    source = WorkerTypes.entries.map { it.description },
                    selected = selectedPosition?.description ?: "",
                    isError = isSubmitClicked && (selectedPosition == null),
                    errorMessage = stringResource(R.string.empty_string),
                    label = stringResource(R.string.worker_type),
                    onOptionSelected = { selected ->
                        selectedPosition = WorkerTypes.entries.firstOrNull {
                            it.description == selected
                        }
                        isError = false
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
                        isError = false
                    },
                    trailingIcon = {
                        if (selectedDepot.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    depotAutocompleteViewModel.onQueryChange("")
                                    selectedDepot = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(R.string.clear_text)
                                )
                            }
                        }
                    },
                    label = stringResource(R.string.worker_depot),
                    isError = isSubmitClicked && selectedDepot.isEmpty(),
                    error = stringResource(R.string.empty_string)
                )
            }
        }
    )
}