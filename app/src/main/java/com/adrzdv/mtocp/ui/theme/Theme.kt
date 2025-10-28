package com.adrzdv.mtocp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = AppColors.MAIN_GREEN.color,
    onPrimary = AppColors.TEXT_ON_PRIMARY.color,
    background = AppColors.OFF_WHITE.color,
    surface = AppColors.OFF_WHITE.color,
    error = AppColors.ERROR_COLOR.color,
    onError = AppColors.TEXT_ON_PRIMARY.color,
    onBackground = AppColors.TEXT_PRIMARY.color,
    onSurface = AppColors.TEXT_PRIMARY.color,
    outline = AppColors.OUTLINE_GREEN.color
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = AppTypography,
        content = content,
        shapes = MaterialTheme.shapes,
        colorScheme = LightColors
    )
}