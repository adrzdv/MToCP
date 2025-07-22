package com.adrzdv.mtocp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CoachViewModelFactory(
    private val coachNumber: String,
    private val orderViewModel: OrderViewModel,
    private val depotViewModel: DepotViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return CoachViewModel(coachNumber, orderViewModel, depotViewModel) as T
    }
}