package com.adrzdv.mtocp.ui.state

data class OnRevisionPassengerCoachState(
    val number: String = "",
    val selectedType: String = "",
    val isCopyDepot: Boolean = true,
    val isTrailing: Boolean = false,
    val selectedDepot: String? = null,
    val route: String? = null,
    val workerId: String? = null,
    val workerName: String? = null,
    val workerPosition: String? = null,
    val workerDepot: String? = null,
    val violationMap: Map<Int, String> = emptyMap(),

    val numberError: String? = null,
    val selectedTypeError: String? = null,
    val selectedDepotError: String? = null,
    val routeError: String? = null,
    val workerIdError: String? = null,
    val workerNameError: String? = null,
    val workerDepotError: String? = null,
    val workerPositionError: String? = null,

    val isValidationStarted: Boolean = false
) {
}