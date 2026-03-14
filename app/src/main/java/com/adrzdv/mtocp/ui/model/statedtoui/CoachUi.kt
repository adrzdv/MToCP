package com.adrzdv.mtocp.ui.model.statedtoui

import java.time.LocalDateTime

data class CoachUi(
    val number: String = "",
    val route: String? = "",
    val type: String = "",
    val depot: String = "",
    val isTrailing: Boolean = false,
    val revisionStart: LocalDateTime? = null,
    val revisionEnd: LocalDateTime? = null,
    val violationMap: Map<Int, ViolationUi> = emptyMap(),
    val workerId: String? = "",
    val workerName: String? = "",
    val workerPosition: String? = "",
    val workerDepot: String? = ""
)

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
