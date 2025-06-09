package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel

@Composable
fun AddCoachDialog(
    orderViewModel: OrderViewModel,
    depotViewModel: DepotViewModel,
    onDismiss: () -> Unit
) {
    var coachNumber by remember { mutableStateOf("") }
    var selectedDepot by remember { mutableStateOf("") }
    var checkedTrailingCar by remember { mutableStateOf(false) }
    var trailingRoute by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppColors.OFF_WHITE.color,
        confirmButton = {
            Button(
                onClick = {

                },
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = AppColors.MAIN_GREEN.color
                    ),
                border = null,
                enabled = coachNumber.isNotBlank()
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
                text = stringResource(R.string.new_coach),
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
                    value = coachNumber,
                    onValueChange = { coachNumber = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.OUTLINE_GREEN.color,
                        focusedContainerColor = AppColors.OFF_WHITE.color
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.coach_number),
                            style = AppTypography.labelMedium
                        )
                    },
                    singleLine = true,
                    textStyle = AppTypography.bodyMedium
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = checkedTrailingCar,
                        onCheckedChange = { checkedTrailingCar = it }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.quality_passport),
                        style = AppTypography.labelLarge
                    )
                }

                DropdownMenuField(
                    label = stringResource(R.string.worker_depot),
                    options = depotViewModel.filteredDepots.value?.map { it.name } ?: emptyList(),
                    selectedOption = selectedDepot,
                    onOptionSelected = { selectedDepot = it }
                )

                OutlinedTextField(
                    value = trailingRoute,
                    enabled = checkedTrailingCar,
                    onValueChange = { trailingRoute = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.OUTLINE_GREEN.color,
                        focusedContainerColor = AppColors.OFF_WHITE.color
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.trailing_route),
                            style = AppTypography.labelMedium
                        )
                    },
                    singleLine = true,
                    textStyle = AppTypography.bodyMedium
                )
            }
        }
    )
}