package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.InnerWorkerViewModel

@Composable
fun AddWorkerDialog(
    orderViewModel: OrderViewModel,
    depotViewModel: DepotViewModel,
    onDismiss: () -> Unit,
    innerWorkerViewModel: InnerWorkerViewModel = viewModel(),
) {
    var tabNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var selectedWorkerType by remember { mutableStateOf<WorkerTypes?>(null) }
    var selectedDepot by remember { mutableStateOf("") }

    fun addWorker(
        number: String,
        name: String,
        selectedDepot: String,
        workerType: WorkerTypes
    ) {
        val depotDomain = depotViewModel.getDepotDomain(selectedDepot)
        val worker = InnerWorkerDomain(number.toInt(), name, depotDomain, workerType)
        innerWorkerViewModel.addWorker(worker)
        orderViewModel.addWorker(worker)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppColors.OFF_WHITE.color,
        confirmButton = {
            Button(
                onClick = {
                    selectedWorkerType?.let {
                        selectedWorkerType?.let { workerType ->
                            addWorker(tabNumber, name, selectedDepot, workerType)
                            onDismiss()
                        }
                    }
                },
                colors = ButtonDefaults
                    .buttonColors(containerColor = AppColors.MAIN_GREEN.color),
                border = null,
                enabled = tabNumber.isNotBlank()
                        && name.isNotBlank()
                        && selectedWorkerType != null
                        && selectedDepot.isNotBlank()
            ) {
                Text(
                    stringResource(R.string.add_string),
                    style = AppTypography.bodyMedium
                )
            }
        },
        dismissButton = {
            CustomOutlinedButton(
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
                OutlinedTextField(
                    value = tabNumber,
                    onValueChange = { tabNumber = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.OUTLINE_GREEN.color
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.worker_id),
                            style = AppTypography.labelMedium
                        )
                    },
                    singleLine = true,
                    textStyle = AppTypography.bodyMedium
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.OUTLINE_GREEN.color
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.worker_name),
                            style = AppTypography.labelMedium
                        )
                    },
                    singleLine = true,
                    textStyle = AppTypography.bodyMedium
                )

                DropdownMenuField(
                    label = stringResource(R.string.worker_type),
                    options = WorkerTypes.values().map { it.description },
                    selectedOption = selectedWorkerType?.description ?: "",
                    onOptionSelected = { desc ->
                        selectedWorkerType = WorkerTypes.values().firstOrNull {
                            it.description == desc
                        }
                    }
                )

                DropdownMenuField(
                    label = stringResource(R.string.worker_depot),
                    options = depotViewModel.filteredDepots.value?.map { it.name } ?: emptyList(),
                    selectedOption = selectedDepot,
                    onOptionSelected = { selectedDepot = it }
                )
            }
        }
    )
}