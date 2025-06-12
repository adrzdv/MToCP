package com.adrzdv.mtocp.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color

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
            if(isError && errorText.isNotBlank()) {
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