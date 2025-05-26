package com.adrzdv.mtocp.ui.screen.wrapper

import androidx.compose.ui.platform.ComposeView
import com.adrzdv.mtocp.ui.screen.ServiceScreen

fun ComposeView.showServiceScreen(
    onCleanRepositoryClick: () -> Unit,
    onLoadCatalogClick: () -> Unit,
    onBackClick: () -> Unit
) {
    setContent {
        ServiceScreen(
            onCleanRepositoryClick = onCleanRepositoryClick,
            onLoadCatalog = onLoadCatalogClick,
            onBackClick = onBackClick
        )
    }

}