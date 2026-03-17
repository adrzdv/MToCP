package com.adrzdv.mtocp.ui.model.dto

import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import java.time.LocalDateTime

data class DinnerCarUI(
    override val globalType: CoachTypes = CoachTypes.DINNER_CAR,
    override val number: String = "",
    override val type: String = "",
    override val depot: String = "",
    override val revisionStart: LocalDateTime? = null,
    override val revisionEnd: LocalDateTime? = null,
    override val violationMap: Map<Int, ViolationUi> = emptyMap(),
    override val workerId: String? = "",
    override val workerName: String? = "",
    override val workerPosition: String? = "",
    val company: String = ""
) : CoachUIBase