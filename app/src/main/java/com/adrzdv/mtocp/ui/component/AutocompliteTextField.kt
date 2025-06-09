package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutocompleteTextField(
    query: String,
    suggestions: List<String>,
    onQueryChanged: (String) -> Unit,
    onSuggestionSelected: (String) -> Unit,
    enabled: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        expanded = expanded && enabled && suggestions.isNotEmpty(),
        onExpandedChange = {
            if (enabled) expanded = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = query,
            onValueChange = {
                if (enabled) {
                    onQueryChanged(it)
                    expanded = true
                }
            },
            label = {
                Text(
                    stringResource(R.string.input_text_hint),
                    style = AppTypography.labelLarge
                )
            },
            textStyle = AppTypography.bodyLarge,
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            enabled = enabled,
            readOnly = false,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.OUTLINE_GREEN.color,
                unfocusedBorderColor = Color(0xFFCCCCCC),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Gray,
                disabledLabelColor = Color.Gray,
                disabledBorderColor = Color.LightGray,
                disabledContainerColor = Color(0xFFEFEFEF)
            )
        )

        ExposedDropdownMenu(
            expanded = expanded && enabled && suggestions.isNotEmpty(),
            onDismissRequest = { expanded = false }
        ) {
            suggestions.forEach { suggestion ->
                DropdownMenuItem(
                    text = {
                        Text(
                            suggestion,
                            style = AppTypography.bodyLarge
                        )
                    },
                    onClick = {
                        onQueryChanged(suggestion)
                        onSuggestionSelected(suggestion)
                        expanded = false
                        focusManager.clearFocus()
                    }
                )
            }
        }
    }
}