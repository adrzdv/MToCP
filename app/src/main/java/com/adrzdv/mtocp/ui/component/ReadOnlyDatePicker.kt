package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import androidx.compose.ui.Modifier as Modifier

@Composable
fun ReadOnlyDatePickerField(
    value: String,
    labelText: String,
    isBlankError: Boolean = false,
    isFormatError: Boolean = false,
    errorBlankMessage: String = "",
    errorTimeMessage: String = "",
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isError = isBlankError || isFormatError

    OutlinedTextField(
        value = value,
        onValueChange = {},
        trailingIcon = {
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = AppColors.ERROR.color,
                    contentDescription = ""
                )
            }
        },
        readOnly = true,
        enabled = false,
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        label = {
            Text(labelText, style = AppTypography.labelMedium)
        },
        isError = isError,
        supportingText = {
            if (isBlankError) {
                Text(
                    text = errorBlankMessage,
                    color = AppColors.ERROR.color,
                    style = AppTypography.labelSmall
                )
            } else if (isFormatError) {
                Text(
                    text = errorTimeMessage,
                    color = AppColors.ERROR.color,
                    style = AppTypography.labelSmall
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) AppColors.ERROR.color else Color(0xFFCCCCCC),
            unfocusedBorderColor = if (isError) AppColors.ERROR.color else Color(0xFFCCCCCC),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        textStyle = AppTypography.bodyLarge,
        singleLine = true
    )
}