package com.adrzdv.mtocp.ui.component.dialogs.newcoachcontent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.dialogs.sys.AppIconTitleDialog
import com.adrzdv.mtocp.ui.component.newelements.AutocompleteField
import com.adrzdv.mtocp.ui.component.newelements.DropdownField
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.RoundedButton
import com.adrzdv.mtocp.ui.component.newelements.RoundedUnborderedButton
import com.adrzdv.mtocp.ui.state.coach.CoachDraftState

@Composable
fun NewCoachShortDataDialog(
    draftState: CoachDraftState,
    onConfirm: (CoachDraftState) -> Unit,
    onDismiss: () -> Unit,
) {

    val number by remember { mutableStateOf(draftState.number) }
    val selectedType by remember { mutableStateOf(draftState.type) }
    val selectedDepot by remember { mutableStateOf(draftState.depot) }

    AppIconTitleDialog(
        icon = painterResource(R.drawable.ic_wagon),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            RoundedButton(
                onClick = {
                    onConfirm(
                        draftState.copy(
                            number = number,
                            type = selectedType,
                            depot = selectedDepot
                        )
                    )
                    onDismiss()
                },
                text = stringResource(R.string.add_string)
            )
        },
        dismissButton = {
            RoundedUnborderedButton(
                onClick = { onDismiss() },
                text = stringResource(R.string.cancel)
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            InputTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                isEnabled = true,
                isError = false,
                errorText = null,
                label = "Number",
                trailingIcon = {},
                secretInput = false,
                readOnly = false,
            )
            DropdownField(
                source = emptyList(),
                selected = "",
                isError = false,
                errorMessage = null,
                label = "Simple label",
                onOptionSelected = {}
            )
            AutocompleteField(
                value = "",
                source = emptyList(),
                onValueChange = {},
                onValueSelected = {},
                trailingIcon = { },
                label = "Simple label",
                isError = false,
                enabled = true,
                error = null
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    NewCoachShortDataDialog(
//        onDismiss = {},
//        onConfirm = {}
//    )
//}