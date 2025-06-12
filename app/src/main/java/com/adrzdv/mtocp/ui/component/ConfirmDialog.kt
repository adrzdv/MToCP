package com.adrzdv.mtocp.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmText: String = stringResource(R.string.yes_string),
    dismissText: String = stringResource(R.string.cancel),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val buttonConfirmationStyle = AppTypography.labelLarge
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppColors.LIGHT_GRAY.color,
        title = {
            Text(
                text = title,
                style = AppTypography.titleMedium
            )
        },
        text = {
            Text(
                text = message,
                style = AppTypography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmText,
                    style = buttonConfirmationStyle,
                    color = AppColors.MAIN_GREEN.color
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = dismissText,
                    style = buttonConfirmationStyle,
                    color = AppColors.MAIN_GREEN.color
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewDialog() {
    ConfirmDialog(
        "Example title",
        "Some message",
        onConfirm = {},
        onDismiss = {}
    )
}