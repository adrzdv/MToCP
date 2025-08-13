package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.violation.StaticsParam
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.AdditionalParamViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParameterSelectionBottomSheet(
    paramsViewModel: AdditionalParamViewModel,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
    callback: (Map<String, StaticsParam>) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val params = paramsViewModel.tempParams

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppColors.OFF_WHITE.color,
        modifier = Modifier.imePadding()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                stringResource(R.string.addition_params_string),
                style = AppTypography.titleLarge
            )

            Spacer(Modifier.height(16.dp))

            params.forEach { param ->
                var note by remember { mutableStateOf(param.note ?: "") }
                var completed by remember { mutableStateOf(param.completed ?: true) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        param.name,
                        modifier = Modifier.weight(4f),
                        style = AppTypography.bodyLarge
                    )

                    Checkbox(
                        checked = completed,
                        onCheckedChange = { checked ->
                            completed = checked
                            paramsViewModel.updateCompleted(param.id, checked)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                CustomOutlinedTextField(
                    value = note,
                    onValueChange = { newNote ->
                        note = newNote
                        paramsViewModel.updateNote(param.id, newNote)
                    },
                    label = "note",
                    isError = false,
                    errorText = "",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(24.dp))

            CustomOutlinedButton(
                onClick = {
                    scope.launch {
                        paramsViewModel.getMapOfParams()
                        val mapParams = paramsViewModel.mapParams
                        callback(mapParams)
                        sheetState.hide()
                        onSave()
                    }
                },
                text = stringResource(R.string.save_string),

                )
        }
    }
}

