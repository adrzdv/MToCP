package com.adrzdv.mtocp.ui.screen

import ViolationCatalogScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.asFlow
import com.adrzdv.mtocp.ui.model.ViolationDto
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel

fun ComposeView.showViolationCatalogScreen(
    onBackClick: () -> Unit,
    viewModel: ViolationViewModel,
    revisionTypes: List<String>
) {
    setContent {
        val violations by viewModel.getFilteredViolations()
            .asFlow()
            .collectAsState(initial = emptyList())

        ViolationCatalogScreen(
            onBackClick = onBackClick,
            viewModel = viewModel,
            revisionTypes = revisionTypes
        )
    }
}