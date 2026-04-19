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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.ui.component.newelements.AutocompleteField
import com.adrzdv.mtocp.ui.component.newelements.ClearIcon
import com.adrzdv.mtocp.ui.component.newelements.DropdownField
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.SquaredMediumButton
import com.adrzdv.mtocp.ui.model.dto.WorkerUI
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.EditCoachViewModel

@Composable
fun EditCoachWorkerSheetContent(
    editCoachViewModel: EditCoachViewModel,
    depotAutoCompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    onClose: (WorkerUI) -> Unit
) {

    val state by editCoachViewModel.state.collectAsState()
    var workerId by remember { mutableStateOf("") }
    var workerName by remember { mutableStateOf("") }
    var workerPosition by remember { mutableStateOf("") }
    val depotQuery by depotAutoCompleteViewModel.query.collectAsState()
    val depotSuggestions by depotAutoCompleteViewModel.suggestions.collectAsState()

    LaunchedEffect(state.worker) {
        state.worker?.let {
            depotAutoCompleteViewModel.onQueryChange(it.depot)
            workerId = it.id
            workerName = it.name
            workerPosition = it.position
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
            value = workerId,
            onValueChange = {
                workerId = it
            },
            isError = state.isIllegalState() && workerId.isNotEmpty(),
            errorText = state.workerError,
            label = stringResource(R.string.worker_id),
            trailingIcon = {
                if (workerId.isNotEmpty()) {
                    ClearIcon {
                        workerId = ""
                    }
                }
            }

        )

        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            value = workerName,
            onValueChange = { workerName = it },
            isError = state.isIllegalState() && workerName.isNotEmpty(),
            errorText = state.workerError,
            label = stringResource(R.string.worker_name),
            trailingIcon = {
                if (workerName.isNotEmpty()) {
                    ClearIcon {
                        workerName = ""
                    }
                }
            },
            secretInput = false,
            readOnly = false,
        )
        DropdownField(
            source = WorkerTypes.entries.map { it.description },
            selected = workerPosition,
            isError = state.isIllegalState() && workerPosition.isNotEmpty(),
            errorMessage = state.workerError,
            label = stringResource(R.string.worker_position),
            onOptionSelected = { selected ->
                workerPosition = selected
            }
        )

        if (state.globalType != CoachTypes.DINNER_CAR) {
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
                isError = state.isIllegalState() && depotQuery.isNotEmpty(),
                enabled = true,
                error = state.workerError
            )
        }


        Row(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SquaredMediumButton(
                onClick = {
                    onClose(
                        editCoachViewModel.createWorkerUi(
                            workerId, workerName, workerPosition, depotQuery
                        )
                    )
                }, //TODO Need to validate new data (now we are getting object without synchronization)
                text = stringResource(R.string.save_string),
            )
        }
    }
}