package com.adrzdv.mtocp.ui.state

import com.adrzdv.mtocp.domain.model.violation.StaticsParam
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain

data class DinnerCoachState(
    val idWorker: String? = "",
    val nameWorker: String? = "",
    val typeWorker: String? = "",
    val violations: Map<Int, ViolationDomain>? = emptyMap(),
    val statParams: Map<String, StaticsParam>? = emptyMap(),

    val idError: String? = null,
    val nameError: String? = null,
    val typeError: String? = null,
    val statParamsError: String? = null,

    val isSaveEnabled: Boolean = false
)