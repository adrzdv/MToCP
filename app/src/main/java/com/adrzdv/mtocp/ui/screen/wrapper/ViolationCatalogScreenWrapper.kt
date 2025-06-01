package com.adrzdv.mtocp.ui.screen.wrapper

import ViolationCatalogScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.asFlow
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel

fun ComposeView.showViolationCatalogScreen(
    onBackClick: () -> Unit,
    viewModel: ViolationViewModel,
    revisionTypes: List<String>
) {
    setContent {
        ViolationCatalogScreen(
            onBackClick = onBackClick,
            viewModel = viewModel,
            revisionTypes = revisionTypes
        )
    }
}