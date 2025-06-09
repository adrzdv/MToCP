package com.adrzdv.mtocp.ui.component

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun CustomOutlinedButton(
    onClick: () -> Unit,
    text: String
) {
    OutlinedButton(
        onClick = onClick,
        border = null,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = AppColors.MAIN_GREEN.color
        )
    ) {
        Text(
            text = text,
            style = AppTypography.bodyMedium
        )
    }
}

@Preview
@Composable
fun ShowCustomOutline() {
    CustomOutlinedButton(
        onClick = {},
        text = "Some text"
    )
}