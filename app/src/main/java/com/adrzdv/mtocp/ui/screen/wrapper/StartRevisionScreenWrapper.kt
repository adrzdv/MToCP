package com.adrzdv.mtocp.ui.screen.wrapper

import androidx.compose.ui.platform.ComposeView
import com.adrzdv.mtocp.ui.screen.StartRevisionScreen
import com.adrzdv.mtocp.ui.viewmodel.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel

fun ComposeView.showStartRevisionScreen(
    orderViewModel: OrderViewModel,
    autocompleteViewModel: AutocompleteViewModel,
    ordersTypes: List<String>,
    onBackClick: () -> Unit
) {
    setContent {
        StartRevisionScreen(
            orderViewModel = orderViewModel,
            autocompleteViewModel = autocompleteViewModel,
            orderTypes = ordersTypes,
            onBackClick = onBackClick
        )
    }
}