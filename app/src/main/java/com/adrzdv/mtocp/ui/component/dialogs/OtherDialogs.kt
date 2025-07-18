package com.adrzdv.mtocp.ui.component.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.departments.DepotDomain
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain
import com.adrzdv.mtocp.ui.component.AppFullscreenDialog
import com.adrzdv.mtocp.ui.component.CustomOutlinedButton
import com.adrzdv.mtocp.ui.component.CustomOutlinedTextField
import com.adrzdv.mtocp.ui.component.DropdownMenuField
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.RequestWebViewModel
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider

@Composable
fun CustomAlertDialog(
    viewModel: RequestWebViewModel,
    title: String
) {
    val workerName = viewModel.workerName

    AlertDialog(
        onDismissRequest = { viewModel.dismissDialogs() },
        title = {
            Text(
                text = title,
                style = AppTypography.titleMedium
            )
        },
        containerColor = AppColors.LIGHT_GRAY.color,
        confirmButton = {
            Button(
                onClick = {
                    viewModel.getNumber()
                },
                colors = ButtonDefaults
                    .buttonColors(containerColor = AppColors.MAIN_GREEN.color),
                border = null
            ) {
                Text(
                    stringResource(R.string.add_string),
                    style = AppTypography.bodyMedium
                )
            }
        },
        dismissButton = {
            CustomOutlinedButton(
                onClick = { viewModel.dismissDialogs() },
                stringResource(R.string.cancel)
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
                    value = workerName,
                    onValueChange = {
                        viewModel.onWorkerNameChanged(it)
                    },
                    isError = false,
                    errorText = "",
                    label = stringResource(R.string.worker_name)
                )
            }
        }
    )
}

@Composable
fun AddTempTrainDialog(
    orderVM: OrderViewModel,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    var isEmptyNumber by remember { mutableStateOf(false) }
    var isEmptyRoute by remember { mutableStateOf(false) }
    var isEmptyDepot by remember { mutableStateOf(false) }

    var trainNumber by remember { mutableStateOf("") }
    var trainRoute by remember { mutableStateOf("") }
    var trainDepot by remember { mutableStateOf("") }

    val depotVM: DepotViewModel = viewModel(
        factory = ViewModelFactoryProvider.provideFactory()
    )

    fun addTempTrain() {
        val depot: DepotDomain = depotVM.getDepotDomain(trainDepot)
        val tempTrain: TrainDomain =
            TrainDomain(trainNumber, trainRoute, depot, false, false, false)
        orderVM.setObjectNumber(trainNumber)
    }

    AppFullscreenDialog(
        title = "",
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomOutlinedTextField(
                    label = stringResource(R.string.object_number),
                    value = trainNumber,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onValueChange = {
                        trainNumber = it
                    },
                    isError = isEmptyNumber,
                    errorText = stringResource(R.string.empty_string)
                )

                CustomOutlinedTextField(
                    label = stringResource(R.string.route),
                    value = trainRoute,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f),
                    onValueChange = {
                        trainRoute = it
                    },
                    isError = isEmptyRoute,
                    errorText = stringResource(R.string.empty_string)
                )
            }

            DropdownMenuField(
                label = stringResource(R.string.worker_depot),
                modifier = Modifier.fillMaxWidth(),
                options = listOf(),
                selectedOption = "",
                onOptionSelected = {},
                isError = isEmptyDepot,
                errorMessage = stringResource(R.string.empty_string)
            )

            TextButton(
                onClick = {
                    trainNumber = ""
                    trainRoute = ""
                    trainDepot = ""
                    isEmptyNumber = false
                    isEmptyRoute = false
                    isEmptyRoute = false
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear_list),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.clean_string),
                    style = AppTypography.labelLarge,
                    color = Color.Black
                )
            }
        }
    )
}
