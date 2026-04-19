package com.adrzdv.mtocp.ui.model.dto

import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import java.time.LocalDateTime
import java.util.UUID

data class CoachUi(
    override val id: UUID = UUID.randomUUID(),
    override val globalType: CoachTypes = CoachTypes.PASSENGER_CAR,
    override val number: String = "",
    override val type: String = "",
    override val depot: String = "",
    override val revisionStart: LocalDateTime? = null,
    override val revisionEnd: LocalDateTime? = null,
    override val violationMap: Map<Int, ViolationUi> = emptyMap(),
    override val statParams: Map<String, StaticsParamUi> = emptyMap(),
    override val workerId: String? = "",
    override val workerName: String? = "",
    override val workerPosition: String? = "",
    val workerDepot: String? = "",
    val route: String? = "",
    val isTrailing: Boolean = false,
) : CoachUIBase