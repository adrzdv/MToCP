package com.adrzdv.mtocp.ui.screen

import androidx.compose.ui.platform.ComposeView

fun ComposeView.showStartMenuScreen(
    onStartRevisionClick: () -> Unit,
    onOpenViolationCatalogClick: () -> Unit,
    onServiceMenuClick: () -> Unit,
    onExitClick: () -> Unit,
    onHelpClick: () -> Unit,
    appVersion: String
) {
    setContent {
        StartMenuScreen(
            onStartRevisionClick = onStartRevisionClick,
            onOpenViolationCatalogClick = onOpenViolationCatalogClick,
            onServiceMenuClick = onServiceMenuClick,
            onExitClick = onExitClick,
            onHelpClick = onHelpClick,
            appVersion = appVersion
        )
    }
}