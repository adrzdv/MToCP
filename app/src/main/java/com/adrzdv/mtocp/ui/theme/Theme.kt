package com.adrzdv.mtocp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = ApplicationColors.MAIN_COLOR,
    onPrimary = ApplicationColors.SURFACE_COLOR,
    secondary = ApplicationColors.MAIN_COLOR,
    onSecondary = ApplicationColors.SURFACE_COLOR,
    background = ApplicationColors.BACKGROUND_COLOR,
    onBackground = ApplicationColors.MAIN_COLOR,
    surface = ApplicationColors.SURFACE_COLOR,
    onSurface = ApplicationColors.MAIN_COLOR,
    error = ApplicationColors.ERROR_COLOR,
    onError = ApplicationColors.SURFACE_COLOR,
    surfaceContainerHigh = ApplicationColors.SURFACE_COLOR
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