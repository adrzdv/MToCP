package com.adrzdv.mtocp.ui.screen

import androidx.compose.runtime.Composable
import com.adrzdv.mtocp.ui.viewmodel.model.DepotViewModel

@Composable
fun DinnerDepotCatalogScreen(
    viewModel: DepotViewModel
) {
    DepotCatalogScreen(viewModel = viewModel)
}
