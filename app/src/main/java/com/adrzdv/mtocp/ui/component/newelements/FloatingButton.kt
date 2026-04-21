package com.adrzdv.mtocp.ui.component.newelements

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun FloatingExtendedButton(
    icon: @Composable () -> Unit,
    text: String = "",
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        contentColor = Color.White,
        containerColor = AppColors.MAIN_COLOR.color,
        icon = {
            icon()
        },
        text = {
            Text(
                text = text,
                style = AppTypography.labelLarge,
                color = AppColors.MAIN_COLOR.color
            )
        }
    )
}

@Composable
fun FloatingButton(
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = AppColors.MAIN_COLOR.color,
        contentColor = Color.White
    ) {
        icon()
    }
}