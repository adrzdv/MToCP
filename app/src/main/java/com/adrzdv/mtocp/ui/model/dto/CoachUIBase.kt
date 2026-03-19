package com.adrzdv.mtocp.ui.model.dto

import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import java.time.LocalDateTime
import java.util.UUID

sealed interface CoachUIBase {
    val id: UUID
    val globalType: CoachTypes
    val number: String
    val type: String
    val depot: String
    val revisionStart: LocalDateTime?
    val revisionEnd: LocalDateTime?
    val violationMap: Map<Int, ViolationUi>
    val statParams: Map<String, StaticsParamUi>
    val workerId: String?
    val workerName: String?
    val workerPosition: String?
}