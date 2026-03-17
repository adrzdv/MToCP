package com.adrzdv.mtocp.ui.model.statedtoui

import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import java.time.LocalDateTime

interface CoachUIBase {
    val globalType: CoachTypes?
    val number: String
    val type: String
    val depot: String
    val revisionStart: LocalDateTime?
    val revisionEnd: LocalDateTime?
    val violationMap: Map<Int, ViolationUi>
    val workerId: String?
    val workerName: String?
    val workerPosition: String?
}

data class CoachUi(
    override val globalType: CoachTypes = CoachTypes.PASSENGER_CAR,
    override val number: String = "",
    override val type: String = "",
    override val depot: String = "",
    override val revisionStart: LocalDateTime? = null,
    override val revisionEnd: LocalDateTime? = null,
    override val violationMap: Map<Int, ViolationUi> = emptyMap(),
    override val workerId: String? = "",
    override val workerName: String? = "",
    override val workerPosition: String? = "",
    val workerDepot: String? = "",
    val route: String? = "",
    val isTrailing: Boolean = false
) : CoachUIBase

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

data class ViolationUi(
    val code: Int,
    val description: String,
    val shortDescription: String,
    val value: Int,
    val isResolved: Boolean,
    val attributes: List<String> = emptyList(),
    val mediaPaths: List<String> = emptyList()
)

data class StaticsParamUi(
    val id: Int,
    val name: String,
    val isCompleted: Boolean,
    val note: String
)
