package com.adrzdv.mtocp.ui.state.coach

import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import com.adrzdv.mtocp.ui.model.dto.StaticsParamUi
import com.adrzdv.mtocp.ui.model.dto.ViolationUi
import com.adrzdv.mtocp.ui.model.dto.WorkerUI
import java.util.UUID

data class CoachDraftState(
    val id: UUID,
    val globalType: CoachTypes,
    val number: String? = "",
    val depot: String? = "",
    val company: String? = "",
    val type: String? = "",
    val worker: WorkerUI? = null,
    val violationMap: Map<Int, ViolationUi>? = emptyMap(),
    val statParams: Map<String, StaticsParamUi>? = emptyMap(),
    val isTrailing: Boolean? = false,
    val route: String? = "",

    val numberError: String? = null,
    val workerError: String? = null,
    val depotError: String? = null,
    val companyError: String? = null,
    val typeError: String? = null,
    val statParamsEmpty: String? = null,
    val routeError: String? = null,

    val isSaveEnabled: Boolean = false
) {
    private val isErrorsEmpty = listOf(
        numberError,
        workerError,
        depotError,
        companyError,
        typeError,
        statParamsEmpty,
        routeError
    )

    fun isIllegalState(): Boolean {
        return isErrorsEmpty.any { it != null }
    }
}