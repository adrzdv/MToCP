package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.OUTLINE_GREEN.color,
                focusedContainerColor = AppColors.LIGHT_GRAY.color
            ),
            readOnly = true,
            label = {
                Text(
                    text = label,
                    style = AppTypography.labelMedium
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { selectedOption ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = selectedOption,
                            style = AppTypography.bodyMedium
                        )
                    },
                    onClick = {
                        onOptionSelected(selectedOption)
                        expanded = false
                    }
                )
            }
        }
    }
}