package com.adrzdv.mtocp.ui.screen.wrapper

import androidx.compose.ui.platform.ComposeView
import com.adrzdv.mtocp.ui.screen.StartMenuScreen

fun ComposeView.showStartMenuScreen(
    onStartRevisionClick: () -> Unit,
    onOpenViolationCatalogClick: () -> Unit,
    onServiceMenuClick: () -> Unit,
    onExitClick: () -> Unit,
    onHelpClick: () -> Unit,
    onRequestWebClick: () -> Unit,
    appVersion: String
) {
    setContent {
        StartMenuScreen(
            onStartRevisionClick = onStartRevisionClick,
            onOpenViolationCatalogClick = onOpenViolationCatalogClick,
            onServiceMenuClick = onServiceMenuClick,
            onExitClick = onExitClick,
            onHelpClick = onHelpClick,
            onRequestWebClick = onRequestWebClick,
            appVersion = appVersion
        )
    }
}