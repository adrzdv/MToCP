package com.adrzdv.mtocp.ui.component.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import com.adrzdv.mtocp.ui.component.dialogs.sys.AppIconTitleDialog
import com.adrzdv.mtocp.ui.component.newelements.RoundedUnborderedButton
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun CoachSelectionDialog(
    onAddClick: (CoachTypes) -> Unit,
    onDismiss: () -> Unit
) {

    val options = CoachTypes.entries
    var selectedOption by remember { mutableStateOf(options[0]) }

    AppIconTitleDialog(
        icon = painterResource(R.drawable.ic_wagon),
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddClick(selectedOption)
                },
                colors = ButtonDefaults
                    .buttonColors(containerColor = AppColors.MAIN_COLOR.color),
                border = null,
                enabled = true
            ) {
                Text(
                    stringResource(R.string.add_string),
                    style = AppTypography.bodyMedium
                )
            }
        },
        dismissButton = {
            RoundedUnborderedButton(
                onClick = {
                    onDismiss()
                },
                stringResource(R.string.cancel)
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                options.forEach { it ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOption = it
                            },
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (it == selectedOption),
                            onClick = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = it.description)
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    CoachSelectionDialog(
        onAddClick = {},
        onDismiss = {}
    )
}