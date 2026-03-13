package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

//TODO: need to add scrollBehavior in next version
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String = "",
    actions: @Composable (RowScope.() -> Unit) = {},
    navigationIcon: @Composable (() -> Unit) = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = AppTypography.titleLarge,
                color = AppColors.SURFACE_COLOR.color
            )
        },
        actions = actions,
        navigationIcon = navigationIcon,
        colors = TopAppBarColors(
            containerColor = AppColors.MAIN_COLOR.color,
            scrolledContainerColor = AppColors.MAIN_COLOR.color,
            navigationIconContentColor = AppColors.SURFACE_COLOR.color,
            titleContentColor = AppColors.SURFACE_COLOR.color,
            actionIconContentColor = AppColors.SURFACE_COLOR.color,
            subtitleContentColor = AppColors.BACKGROUND_COLOR.color
        )
    )
}