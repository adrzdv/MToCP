package com.adrzdv.mtocp.ui.state

import com.adrzdv.mtocp.domain.model.violation.ViolationDomain

data class PassengerCoachState(
    val idWorker: String? = "",
    val nameWorker: String? = "",
    val typeWorker: String? = "",
    val depotWorker: String? = "",
    val violations: Map<Int, ViolationDomain>? = emptyMap(),

    val idError: String? = null,
    val nameError: String? = null,
    val typeError: String? = null,
    val depotError: String? = null,

    val isSaveEnabled: Boolean = false
)