package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier
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
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        text = errorMessage,
                        style = AppTypography.labelMedium,
                        color = AppColors.ERROR.color
                    )
                }
            },
            onValueChange = {},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) AppColors.ERROR.color else AppColors.OUTLINE_GREEN.color,
                unfocusedBorderColor = if (isError) AppColors.ERROR.color else Color.Gray,
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
                if (isError) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        tint = AppColors.ERROR.color,
                        contentDescription = ""
                    )
                } else ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownMenuParameterizedField(
    label: String,
    options: List<T>,
    selectedOption: T?,
    labelProvider: (T) -> String,
    onOptionSelected: (T) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedOption?.let(labelProvider) ?: "",
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        text = errorMessage,
                        style = AppTypography.labelMedium,
                        color = AppColors.ERROR.color
                    )
                }
            },
            onValueChange = {},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) AppColors.ERROR.color else AppColors.OUTLINE_GREEN.color,
                unfocusedBorderColor = if (isError) AppColors.ERROR.color else Color.Gray,
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
                if (isError) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        tint = AppColors.ERROR.color,
                        contentDescription = ""
                    )
                } else ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = labelProvider(option),
                            style = AppTypography.bodyMedium
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isEnabled: Boolean = true,
    isError: Boolean,
    errorText: String,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        enabled = isEnabled,
        textStyle = AppTypography.bodyLarge,
        onValueChange = onValueChange,
        trailingIcon = {
            if (isError && errorText.isNotBlank()) {
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = AppColors.ERROR.color,
                    contentDescription = ""
                )
            }
        },
        isError = isError,
        supportingText = {
            if (isError && errorText.isNotBlank()) {
                Text(
                    text = errorText,
                    color = AppColors.ERROR.color,
                    style = AppTypography.labelSmall
                )
            }
        },
        label = {
            Text(
                text = label,
                style = AppTypography.labelMedium
            )
        },
        singleLine = true,
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) AppColors.ERROR.color
            else AppColors.OUTLINE_GREEN.color,
            unfocusedBorderColor = if (isError) AppColors.ERROR.color
            else Color(0xFFCCCCCC),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    )
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isEnabled: Boolean = true,
    isError: Boolean,
    errorText: String,
    label: String,
    modifier: Modifier = Modifier,
    trailingIcon: Painter?,
    secretInput: Boolean?
) {
    OutlinedTextField(
        value = value,
        enabled = isEnabled,
        textStyle = AppTypography.bodyLarge,
        onValueChange = onValueChange,
        trailingIcon = {
            trailingIcon?.let {
                Icon(
                    painter = trailingIcon,
                    tint = if (isError && errorText.isNotBlank()) AppColors.ERROR_COLOR.color
                    else AppColors.SECONDARY_COLOR.color,
                    contentDescription = null
                )
            }
        },
        isError = isError,
        supportingText = {
            if (isError && errorText.isNotBlank()) {
                Text(
                    text = errorText,
                    color = AppColors.ERROR.color,
                    style = AppTypography.labelSmall
                )
            }
        },
        label = {
            Text(
                text = label,
                style = AppTypography.labelMedium
            )
        },
        singleLine = true,
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) AppColors.ERROR.color
            else AppColors.OUTLINE_GREEN.color,
            unfocusedBorderColor = if (isError) AppColors.ERROR.color
            else Color(0xFFCCCCCC),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        visualTransformation = when (secretInput) {
            true -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevisionTypeDropdown(
    revisionTypes: List<String>,
    selectedRevision: String,
    isError: Boolean,
    errorMessage: String,
    onRevisionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        OutlinedTextField(
            value = selectedRevision,
            onValueChange = {},
            isError = isError,
            readOnly = true,
            supportingText = {
                if (isError) {
                    Text(
                        text = errorMessage,
                        color = AppColors.ERROR.color,
                        style = AppTypography.labelSmall
                    )
                }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) AppColors.ERROR.color else AppColors.OUTLINE_GREEN.color,
                unfocusedBorderColor = if (isError) AppColors.ERROR.color else Color(0xFFCCCCCC),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            revisionTypes.forEach { type ->
                DropdownMenuItem(
                    text = {
                        Text(
                            type,
                            style = AppTypography.bodyLarge
                        )
                    },
                    onClick = {
                        onRevisionSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutocompleteTextField(
    query: String,
    suggestions: List<String>,
    onQueryChanged: (String) -> Unit,
    onSuggestionSelected: (String) -> Unit,
    enabled: Boolean,
    isError: Boolean,
    errorMessage: String
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
            isError = isError,
            supportingText = {
                if (isError && errorMessage.isNotBlank()) {
                    Text(
                        text = errorMessage,
                        color = AppColors.ERROR.color,
                        style = AppTypography.labelSmall
                    )
                }
            },
            trailingIcon = {
                if (isError && errorMessage.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        tint = AppColors.ERROR.color,
                        contentDescription = ""
                    )
                }
            },
            label = {
                Text(
                    stringResource(R.string.object_number),
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
                focusedBorderColor = if (isError) AppColors.ERROR.color else AppColors.OUTLINE_GREEN.color,
                unfocusedBorderColor = if (isError) AppColors.ERROR.color else Color(0xFFCCCCCC),
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