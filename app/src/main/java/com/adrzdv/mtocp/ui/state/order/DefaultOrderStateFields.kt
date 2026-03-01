package com.adrzdv.mtocp.ui.state.order

import java.time.LocalDateTime

open class DefaultOrderStateFields(
    override val orderNumber: String = "",
    override val dateStart: LocalDateTime = LocalDateTime.now(),
    override val dateEnd: LocalDateTime = LocalDateTime.now(),
    override val route: String = "",
    val numberError: String? = null,
    val dateStartError: String? = null,
    val dateEndError: String? = null,
    val routeError: String? = null,
    val isOrderReadyForSave: Boolean? = null
) : OrderDraftState
