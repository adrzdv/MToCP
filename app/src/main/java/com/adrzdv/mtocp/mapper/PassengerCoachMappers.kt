package com.adrzdv.mtocp.mapper

import com.adrzdv.mtocp.domain.model.departments.CompanyDomain
import com.adrzdv.mtocp.domain.model.departments.DepotDomain
import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import com.adrzdv.mtocp.domain.model.enums.DinnerCarsType
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.ui.model.dto.CoachUIBase
import com.adrzdv.mtocp.ui.model.dto.CoachUi
import com.adrzdv.mtocp.ui.model.dto.DinnerCarUI
import com.adrzdv.mtocp.ui.model.dto.WorkerUI
import com.adrzdv.mtocp.ui.state.coach.CoachDraftState
import com.adrzdv.mtocp.ui.state.coach.NewDinnerCoachState
import com.adrzdv.mtocp.ui.state.coach.NewPassengerCoachState
import java.time.LocalDateTime
import java.util.UUID

fun CoachUi.toDomain(depot: DepotDomain) = PassengerCar(number, this.globalType).apply {
    this.uuid = id
    this.depotDomain = depot
    this.coachType = PassengerCoachType.fromString(type)
    this.coachRoute = route
    this.revisionDateStart = revisionStart ?: LocalDateTime.now()
    this.revisionDateEnd = revisionEnd ?: LocalDateTime.now()
    this.violationMap = this@toDomain.violationMap.mapValues { it.value.toDomain() }
    this.additionalParams = this@toDomain.statParams.mapValues { it.value.toDomain() }
}

fun NewPassengerCoachState.toDomain(depot: DepotDomain) =
    PassengerCar(number, CoachTypes.PASSENGER_CAR).apply {
        this.uuid = UUID.randomUUID()
        this.depotDomain = depot
        this.coachType = PassengerCoachType.fromString(selectedType)
        this.coachRoute = route
    }

fun NewPassengerCoachState.toUI() = CoachUi().copy(
    globalType = globalCoachType,
    number = number,
    route = route,
    type = selectedType,
    depot = selectedDepot ?: "",
    isTrailing = isTrailing,
    workerId = workerId,
    workerName = workerName,
    workerPosition = workerPosition,
    workerDepot = workerDepot
)

fun NewDinnerCoachState.toUI() = DinnerCarUI().copy(
    number = number,
    type = selectedType,
    depot = selectedDepot ?: "",
    company = selectedCompany ?: "",
    workerId = workerId,
    workerName = workerName,
    workerPosition = workerPosition
)

fun DinnerCarUI.toDomain(depot: DepotDomain? = null, company: CompanyDomain? = null) =
    DinnerCar(number, CoachTypes.DINNER_CAR).apply {
        this.uuid = id
        this.depot?.let {
            this.depot = depot
        }

        this.violationMap = violationMap
        this.additionalParams = additionalParams

        this.companyDomain?.let {
            this.companyDomain = company
        }

        this.dinnerType = DinnerCarsType.fromString(type)
        this.revisionDateStart = revisionStart ?: LocalDateTime.now()
        this.revisionDateEnd = revisionEnd ?: LocalDateTime.now()
    }

fun CoachUIBase.toDraftState(): CoachDraftState {
    return when (this) {
        is CoachUi -> this.toDraftState()
        is DinnerCarUI -> this.toDraftState()
    }
}

fun CoachUi.toDraftState(): CoachDraftState = CoachDraftState(
    id = id,
    globalType = globalType,
    number = number,
    depot = depot,
    type = type,
    worker = WorkerUI(
        workerId ?: "",
        workerName ?: "",
        workerPosition ?: "",
        workerDepot ?: ""
    ),
    violationMap = violationMap,
    isTrailing = isTrailing,
    route = route,
)

fun DinnerCarUI.toDraftState(): CoachDraftState = CoachDraftState(
    id = id,
    globalType = globalType,
    number = number,
    depot = depot,
    type = type,
    worker = WorkerUI(
        workerId ?: "",
        workerName ?: "",
        workerPosition ?: "",
        depot.ifEmpty { company }
    ),
    violationMap = violationMap,
)
