package com.adrzdv.mtocp.ui.component.newelements

import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isEnabled: Boolean = true,
    isError: Boolean,
    errorText: String,
    label: String,
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
                    else AppColors.MAIN_COLOR.color,
                    contentDescription = null
                )
            }
        },
        isError = isError,
        supportingText = {
            if (isError && errorText.isNotBlank()) {
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
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) AppColors.ERROR_COLOR.color
            else AppColors.MAIN_COLOR.color,
            unfocusedBorderColor = if (isError) AppColors.ERROR_COLOR.color
            else AppColors.MAIN_COLOR.color,
            focusedContainerColor = AppColors.SURFACE_COLOR.color,
            unfocusedContainerColor = AppColors.SURFACE_COLOR.color,
            focusedLabelColor = AppColors.MAIN_COLOR.color,
            unfocusedLabelColor = AppColors.MAIN_COLOR.color,
            focusedTextColor = AppColors.MAIN_COLOR.color,
            unfocusedTextColor = AppColors.MAIN_COLOR.color,
            errorTextColor = AppColors.ERROR_COLOR.color
        ),
        visualTransformation = when (secretInput) {
            true -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        }
    )
}