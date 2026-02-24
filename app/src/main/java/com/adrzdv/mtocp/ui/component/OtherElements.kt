package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import com.adrzdv.mtocp.ui.component.buttons.CustomOutlinedButton
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.model.AdditionalParamViewModel
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
    var showDialog by remember { mutableStateOf(false) }
    val params = paramsViewModel.tempParams
    var editingParam by remember { mutableStateOf<StaticsParam?>(null) }

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

                    IconButton(onClick = {
                        editingParam = param
                        showDialog = true
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit note")
                    }

                    Checkbox(
                        checked = completed,
                        onCheckedChange = { checked ->
                            completed = checked
                            paramsViewModel.updateCompleted(param.id, checked)
                        },
                        modifier = Modifier.weight(1f),
                        colors = CheckboxDefaults.colors(
                            checkedColor = AppColors.MAIN_GREEN.color,
                            uncheckedColor = AppColors.LIGHT_GRAY.color,
                            checkmarkColor = AppColors.OFF_WHITE.color
                        )
                    )
                }
            }

            if (showDialog) {
                var note by remember { mutableStateOf(editingParam?.note ?: "") }
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    containerColor = AppColors.LIGHT_GRAY.color,
                    title = { Text(stringResource(R.string.add_tag)) },
                    text = {
                        TextField(
                            value = note,
                            onValueChange = { newNote ->
                                note = newNote
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            paramsViewModel.updateNote(editingParam!!.id, note)
                            showDialog = false
                        }) {
                            Text(stringResource(R.string.save_string))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            editingParam = null
                            showDialog = false
                        }) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
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

