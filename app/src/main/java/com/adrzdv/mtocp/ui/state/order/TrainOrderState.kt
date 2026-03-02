package com.adrzdv.mtocp.ui.state.order

import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.ui.model.statedtoui.CoachUi
import com.adrzdv.mtocp.ui.model.statedtoui.TrainUI
import com.adrzdv.mtocp.ui.model.statedtoui.WorkerUI
import java.time.LocalDateTime

data class TrainOrderState(
    override val orderNumber: String = "",
    override val dateStart: LocalDateTime = LocalDateTime.now(),
    override val dateEnd: LocalDateTime = LocalDateTime.now(),
    override val route: String = "",
    val orderConditions: RevisionType? = null,
    val crewList: Map<String, WorkerUI> = emptyMap(),
    val coachList: Map<String, CoachUi> = emptyMap(),
    val train: TrainUI = TrainUI("", "", ""),
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val pickerVisibleFor: PickerField? = null,
    val numberError: String? = null,
    val dateStartError: String? = null,
    val dateEndError: String? = null,
    val routeError: String? = null,
    val emptyCrewError: String? = null,
    val emptyCoachError: String? = null,
    val emptyTrainError: String? = null,
    val conditionsError: String? = null,
    val isOrderReadyForSave: Boolean? = null
) : OrderDraftState

enum class PickerField { START_DATE, END_DATE }