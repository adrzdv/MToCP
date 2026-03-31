package com.adrzdv.mtocp.ui.model

import androidx.compose.ui.graphics.painter.Painter

data class MenuElementItem(
    val route: String,
    val title: String,
    val icon: Painter
)