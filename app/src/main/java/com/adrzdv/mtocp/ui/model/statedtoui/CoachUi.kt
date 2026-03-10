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
    val violationMap: Map<String, String> = emptyMap(),
    val workerId: String? = "",
    val workerName: String? = "",
    val workerPosition: String? = "",
    val workerDepot: String? = ""
)
