package com.adrzdv.mtocp.ui.screen.wrapper

import androidx.compose.ui.platform.ComposeView
import com.adrzdv.mtocp.ui.screen.RevisionScreen
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.OrderViewModel

fun ComposeView.showRevisionScreen(
    orderViewModel: OrderViewModel,
    depotViewModel: DepotViewModel,
    autocompleteViewModel: AutocompleteViewModel,
    ordersTypes: List<String>,
    onBackClick: () -> Unit
) {
    setContent {
        RevisionScreen(
            orderViewModel = orderViewModel,
            depotViewModel = depotViewModel,
            autocompleteViewModel = autocompleteViewModel,
            orderTypes = ordersTypes,
            onBackClick = onBackClick
        )
    }
}