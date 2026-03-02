package com.adrzdv.mtocp.ui.component.newelements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isEnabled: Boolean? = true,
    isError: Boolean,
    errorText: String?,
    label: String,
    trailingIcon: (@Composable () -> Unit)? = null,
    secretInput: Boolean? = false,
    readOnly: Boolean? = false
) {
    OutlinedTextField(
        value = value,
        enabled = isEnabled ?: true,
        textStyle = AppTypography.bodyLarge,
        onValueChange = onValueChange,
        readOnly = readOnly ?: false,
        trailingIcon = {
            val iconColor =
                if (isError && errorText?.isNotBlank() == true)
                    AppColors.ERROR_COLOR.color
                else
                    AppColors.MAIN_COLOR.color
            CompositionLocalProvider(
                LocalContentColor provides iconColor
            ) {
                trailingIcon?.invoke()
            }
        },
        isError = isError,
        supportingText = {
            if (isError && errorText?.isNotBlank() == true) {
                Text(
                    text = errorText,
                    color = AppColors.ERROR_COLOR.color,
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
        maxLines = 1,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) AppColors.ERROR_COLOR.color
            else AppColors.MAIN_COLOR.color,
            unfocusedBorderColor = if (isError) AppColors.ERROR_COLOR.color
            else AppColors.MAIN_COLOR.color,
            focusedContainerColor = AppColors.SURFACE_COLOR.color,
            unfocusedContainerColor = AppColors.SURFACE_COLOR.color,
            focusedLabelColor = if (isError) AppColors.ERROR_COLOR.color
            else AppColors.MAIN_COLOR.color,
            unfocusedLabelColor = if (isError) AppColors.ERROR_COLOR.color
            else AppColors.MAIN_COLOR.color,
            focusedTextColor = AppColors.MAIN_COLOR.color,
            unfocusedTextColor = AppColors.MAIN_COLOR.color,
            errorTextColor = AppColors.ERROR_COLOR.color,
            disabledBorderColor = if (isError) AppColors.ERROR_COLOR.color
            else AppColors.MAIN_COLOR.color,
            disabledTextColor = if (isError) AppColors.ERROR_COLOR.color
            else AppColors.MAIN_COLOR.color,
            disabledLabelColor = if (isError) AppColors.ERROR_COLOR.color
            else AppColors.MAIN_COLOR.color,
            disabledContainerColor = AppColors.SURFACE_COLOR.color,
        ),
        visualTransformation = when (secretInput) {
            true -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    source: List<String>,
    selected: String,
    isError: Boolean,
    errorMessage: String?,
    label: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .fillMaxWidth()
    ) {
        InputTextField(
            value = selected,
            onValueChange = {},
            isError = isError,
            errorText = errorMessage,
            label = label ?: "",
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = AppColors.SURFACE_COLOR.color
        ) {
            source.forEach { type ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = type,
                            style = AppTypography.bodyLarge
                        )
                    }, onClick = {
                        onOptionSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DatePickerReadOnlyField(
    value: String,
    label: String,
    isError: Boolean,
    error: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    InputTextField(
        value = value,
        onValueChange = {},
        isEnabled = false,
        isError = isError,
        errorText = error,
        readOnly = true,
        label = label,
        trailingIcon = {
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = ""
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = ""
                )
            }
        },
        modifier = modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick() }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutocompleteField(
    value: String,
    source: List<String>,
    onValueChange: (String) -> Unit,
    onValueSelected: (String) -> Unit,
    label: String,
    isError: Boolean,
    enabled: Boolean,
    error: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        expanded = expanded && enabled && source.isNotEmpty(),
        onExpandedChange = {
            if (enabled) expanded = it
        },
        modifier = modifier
            .fillMaxWidth()
    ) {
        InputTextField(
            value = value,
            onValueChange = {
                if (enabled) {
                    onValueChange(it)
                    expanded = true
                }
            },
            isEnabled = enabled,
            isError = isError,
            errorText = error,
            label = label,
            secretInput = null
        )
        ExposedDropdownMenu(
            expanded = expanded && enabled && source.isNotEmpty(),
            onDismissRequest = { expanded = false }
        ) {
            source.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            item,
                            style = AppTypography.bodyLarge
                        )
                    },
                    onClick = {
                        onValueChange(item)
                        onValueSelected(item)
                        expanded = false
                        focusManager.clearFocus()
                    }
                )
            }
        }
    }
}