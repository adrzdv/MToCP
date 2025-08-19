package com.adrzdv.mtocp.ui.screen.wrapper

import androidx.compose.ui.platform.ComposeView
import com.adrzdv.mtocp.ui.screen.RequestWebScreen

fun ComposeView.showRequestWebScreen(
    onBackClick: () -> Unit
) {
    setContent {
        RequestWebScreen(
            onBackClick = onBackClick
        )
    }
}