package com.adrzdv.mtocp.ui.screen.wrapper

import androidx.compose.ui.platform.ComposeView
import com.adrzdv.mtocp.ui.screen.InfoCatalogScreen
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel

fun ComposeView.showInfoCatalogScreen(
    onBackClick: () -> Unit,
    violationViewModel: ViolationViewModel,
    revisionTypes: List<String>,
    depotViewModel: DepotViewModel

) {
    setContent {
        InfoCatalogScreen(
            onBackClick = onBackClick,
            violationViewModel = violationViewModel,
            revisionTypes = revisionTypes,
            depotViewModel = depotViewModel
        )
    }
}