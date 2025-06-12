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
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.departments.DepotDomain
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.RevisionObjectViewModel

@Composable
fun AddCoachDialog(
    orderViewModel: OrderViewModel,
    depotViewModel: DepotViewModel,
    onDismiss: () -> Unit,
    coachViewModel: RevisionObjectViewModel<PassengerCar>
) {
    var coachNumber by remember { mutableStateOf("") }
    var selectedDepot by remember { mutableStateOf("") }
    var checkedTrailingCar by remember { mutableStateOf(false) }
    var trailingRoute by remember { mutableStateOf("") }
    var typeCoachSelected by remember { mutableStateOf<PassengerCoachType?>(null) }
    var isCoachPatterError by remember { mutableStateOf(false) }
    var isDuplicateCoachError by remember { mutableStateOf(false) }
    var isRouteError by remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun addCoach() {
        val revObject = PassengerCar(coachNumber)
        val depot: DepotDomain = depotViewModel.getDepotDomain(selectedDepot)
        revObject.depotDomain = depot
        revObject.trailing = checkedTrailingCar
        revObject.coachType = typeCoachSelected
        revObject.coachRoute = trailingRoute
        if (checkedTrailingCar && trailingRoute.isBlank()) {
            isRouteError = true
            return
        }
        try {
            orderViewModel.addRevisionObject(revObject)
            coachViewModel.addRevObject(revObject)
            orderViewModel.updateTrainScheme()
            onDismiss()
        } catch (e: IllegalArgumentException) {
            isCoachPatterError = true
        } catch (e: IllegalStateException) {
            isDuplicateCoachError = true
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppColors.LIGHT_GRAY.color,
        confirmButton = {
            Button(
                onClick = {
                    addCoach()
                },
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = AppColors.MAIN_GREEN.color
                    ),
                border = null,
                enabled = coachNumber.isNotBlank()
                        && selectedDepot.isNotBlank()
                        && typeCoachSelected != null
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
                CustomOutlinedTextField(
                    value = coachNumber,
                    onValueChange = {
                        coachNumber = it
                        isCoachPatterError = false
                        isDuplicateCoachError = false
                    },
                    isError = isCoachPatterError || isDuplicateCoachError,
                    errorText = when {
                        isCoachPatterError -> MessageCodes.PATTERN_MATCHES_ERROR.errorTitle
                        isDuplicateCoachError -> MessageCodes.DUPLICATE_ERROR.errorTitle
                        else -> ""
                    },
                    label = stringResource(R.string.coach_number)
                )

                DropdownMenuField(
                    label = stringResource(R.string.coach_type),
                    options = PassengerCoachType.values().map { it.passengerCoachTitle },
                    selectedOption = typeCoachSelected?.passengerCoachTitle ?: "",
                    onOptionSelected = { selected ->
                        typeCoachSelected = PassengerCoachType.values().firstOrNull() {
                            it.passengerCoachTitle == selected
                        }
                    }
                )

                DropdownMenuField(
                    label = stringResource(R.string.worker_depot),
                    options = depotViewModel.filteredDepots.value?.map { it.name } ?: emptyList(),
                    selectedOption = selectedDepot,
                    onOptionSelected = { selectedDepot = it }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = checkedTrailingCar,
                        colors = CheckboxDefaults.colors(
                            checkedColor = AppColors.MAIN_GREEN.color,
                            checkmarkColor = AppColors.OFF_WHITE.color
                        ),
                        onCheckedChange = { checkedTrailingCar = it }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.trailing_string),
                        style = AppTypography.labelLarge
                    )
                }

                CustomOutlinedTextField(
                    value = trailingRoute,
                    isEnabled = checkedTrailingCar,
                    onValueChange = {
                        trailingRoute = it
                        isRouteError = false
                    },
                    isError = isRouteError,
                    errorText = stringResource(R.string.empty_string),
                    label = stringResource(R.string.trailing_route)
                )

//                OutlinedTextField(
//                    value = trailingRoute,
//                    enabled = checkedTrailingCar,
//                    onValueChange = {
//                        trailingRoute = it
//                        isRouteError = false
//                    },
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = AppColors.OUTLINE_GREEN.color
//                    ),
//                    label = {
//                        Text(
//                            text = stringResource(R.string.trailing_route),
//                            style = AppTypography.labelMedium
//                        )
//                    },
//                    singleLine = true,
//                    textStyle = AppTypography.bodyMedium
//                )
            }
        }
    )
}